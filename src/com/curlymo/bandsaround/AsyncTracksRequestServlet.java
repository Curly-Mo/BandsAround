package com.curlymo.bandsaround;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.curlymo.bandsaround.geocoder.Geocoder;
import com.curlymo.bandsaround.jambase.Artist;
import com.curlymo.bandsaround.jambase.Event;
import com.curlymo.bandsaround.jambase.Events;
import com.curlymo.bandsaround.jambase.Jambase;
import com.curlymo.bandsaround.soundcloud.SoundCloud;
import com.curlymo.bandsaround.soundcloud.Track;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@SuppressWarnings("serial")
public class AsyncTracksRequestServlet extends HttpServlet {
	String soundCloudApiKey="84a2392830bf4d00a8fb7557613a36e6";
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String latLng = req.getHeader("X-AppEngine-CityLatLong");
		String zip = req.getParameter("zip");
		String radius = req.getParameter("radius");
		int dayStart = Integer.parseInt(req.getParameter("dayStart"));
		int dayEnd = Integer.parseInt(req.getParameter("dayEnd"));
		int tracksPerArtist = Integer.parseInt(req.getParameter("tracksPerArtist"));
		if (zip==null){
			try {
				zip = Geocoder.getZipFromLatLng(latLng);
			} catch (JSONException e1) {
			}
		}
		
		URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();
		ArrayList<Future<HTTPResponse>> asyncResponses = new ArrayList<Future<HTTPResponse>>();
		ArrayList<Artist> artists = new ArrayList<Artist>();
		Events events = Jambase.getEvents(zip, radius, dayStart, dayEnd);
		if (events!=null && events.getEvents()!=null && !events.getEvents().isEmpty()){
			for(Event event : events.getEvents()){
				for(Artist artist : event.getArtists()){
					URL url = SoundCloud.getTracksURLByTrackSearch(artist.getArtist_name(), tracksPerArtist);
					Future<HTTPResponse> responseFuture = fetcher.fetchAsync(url);
		            asyncResponses.add(responseFuture);
		            artists.add(artist);
				}
				
			}
		}
		
		JSONArray jsonArray = new JSONArray();
        for(Future<HTTPResponse> future : asyncResponses){
    		Collection<Track> tracks = null;
            HTTPResponse response;
            try {
                response = future.get();
                
    			Gson gson = new Gson();
    			Type collectionType = new TypeToken<Collection<Track>>(){}.getType();
    			tracks = gson.fromJson(new String(response.getContent(), "UTF-8"), collectionType);
    			
				for(Track track : tracks){
					JSONObject json = new JSONObject();
					json.put("title", track.getTitle());
					json.put("artist", track.getUser().getUsername());
					json.put("streamURL", track.getStream_url()+"?client_id="+soundCloudApiKey);
					jsonArray.put(json);
				}
            } catch (InterruptedException e) {
                // Guess you would do something here
            } catch (ExecutionException e) {
                // Guess you would do something here
            } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		
		JSONObject json = new JSONObject();
		try {
			json.put("tracks",shuffleJsonArray(jsonArray));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		out.print(json.toString());
		out.flush();
	}
	
	public JSONObject trackToJson(Track track, Artist artist){
        JSONObject json = new JSONObject();

        try {
			json.put("title", track.getTitle());
			json.put("artist", artist.getArtist_name());
			json.put("streamURL", track.getStream_url()+"?client_id="+soundCloudApiKey);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return json;
	}
	
	public static JSONArray shuffleJsonArray(JSONArray array) throws JSONException {
	    // Implementing Fisher–Yates shuffle
	        Random rnd = new Random();
	        for (int i = array.length() - 1; i >= 0; i--)
	        {
	          int j = rnd.nextInt(i + 1);
	          // Simple swap
	          Object object = array.get(j);
	          array.put(j, array.get(i));
	          array.put(i, object);
	        }
	    return array;
	}
}
