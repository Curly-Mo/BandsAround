package com.curlymo.bandsaround.jambase;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.curlymo.bandsaround.geocoder.Geocoder;
import com.curlymo.bandsaround.jambasev2.Artist;
import com.curlymo.bandsaround.jambasev2.Event;
import com.curlymo.bandsaround.jambasev2.Events;
import com.curlymo.bandsaround.jambasev2.Jambase;
import com.curlymo.bandsaround.soundcloud.SoundCloud;
import com.curlymo.bandsaround.soundcloud.Track;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

@SuppressWarnings("serial")
public class JambaseAsyncTracksRequestServlet extends HttpServlet {
    String soundCloudApiKey="84a2392830bf4d00a8fb7557613a36e6";
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long start = System.currentTimeMillis();
        PrintWriter out = resp.getWriter();
        String latLng = req.getHeader("X-AppEngine-CityLatLong");
        String zip = req.getParameter("zip");
        String retry = req.getParameter("retry");
        String radius = req.getParameter("radius");
        int dayStart = Integer.parseInt(req.getParameter("dayStart"));
        int dayEnd = Integer.parseInt(req.getParameter("dayEnd"));
        int tracksPerArtist = Integer.parseInt(req.getParameter("tracksPerArtist"));
        if (zip==null||zip==""){
            try {
                zip = Geocoder.getZipFromLatLng(latLng);
                } catch (JSONException e1) {
                } catch (IndexOutOfBoundsException e1) {
                }
            }
        URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();
        ArrayList<Future<HTTPResponse>> asyncResponses = new ArrayList<Future<HTTPResponse>>();
        ArrayList<Artist> trackArtists = new ArrayList<Artist>();
        ArrayList<Event> trackEvents = new ArrayList<Event>();
        Events theEvents;
        if(retry==null){
            theEvents = Jambase.getEvents(zip, radius, dayStart, dayEnd);
        }else{
            theEvents = Jambase.getEventsAlternate(zip, radius, dayStart, dayEnd);
        }
        List<Event> events = null;
        if (theEvents!=null){ 
            events = theEvents.getEvents();
            }
        //out.println(events);
        out.println("gotEvents: " + (System.currentTimeMillis() - start));
        if (events!=null && !events.isEmpty()){
            Collections.shuffle(events);
            if(events.size()>200){
                events = events.subList(0, 200);
                }
            for(Event event : events){
                for(Artist artist : event.getArtists()){
                    URL url = SoundCloud.getTracksURLByTrackSearch(artist.getArtist_name(), tracksPerArtist);
                    Future<HTTPResponse> responseFuture = fetcher.fetchAsync(url);
                    asyncResponses.add(responseFuture);
                    trackArtists.add(artist);
                    trackEvents.add(event);
                    }
                }
        }
        out.println("parsedTracks: " + (System.currentTimeMillis() - start));
        int index=0;
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
                    if(track.getStreamable().equals("true")){
                        JSONObject json = new JSONObject();
                        json.put("title", track.getTitle());
                        json.put("artwork", track.getArtwork_url());
                        json.put("artist", trackArtists.get(index).getArtist_name());
                        json.put("artist_id", trackArtists.get(index).getArtist_id());
                        json.put("venue", trackEvents.get(index).getVenue().getVenue_name());
                        //json.put("address", trackEvents.get(index).getVenue().getVenue_address());
                        json.put("city", trackEvents.get(index).getVenue().getVenue_city());
                        //json.put("state", trackEvents.get(index).getVenue().getVenue_stateCode());
                        json.put("zip", trackEvents.get(index).getVenue().getVenue_zip());
                        json.put("date", trackEvents.get(index).getEvent_date());
                        json.put("ticketUrl", trackEvents.get(index).getTicket_url());
                        json.put("streamURL", track.getStream_url()+"?client_id="+soundCloudApiKey);
                        jsonArray.put(json);
                        }
                    }
                } catch (InterruptedException e) {
                    // Guess you would do something here
                } catch (ExecutionException e) {
                    // Guess you would do something here
                } catch (JsonSyntaxException e) {
                    // Soundcloud service unavailable
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                e.printStackTrace();
                }
            index++;
            }
            
        JSONObject json = new JSONObject();
        try {
            json.put("tracks",shuffleJsonArray(jsonArray));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        out.println("parsedJSON: " + (System.currentTimeMillis() - start));    
        out.println("size: " + jsonArray.length());
        resp.setContentType("application/json");
        out = resp.getWriter();
        out.print(json.toString());
        out.flush();
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
