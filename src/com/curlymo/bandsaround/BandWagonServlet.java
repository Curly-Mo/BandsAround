package com.curlymo.bandsaround;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

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
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class BandWagonServlet extends HttpServlet {
	String soundCloudApiKey="84a2392830bf4d00a8fb7557613a36e6";
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String latLng = req.getHeader("X-AppEngine-CityLatLong");
		String zip = "00000";
		String radius = "20";
		int days = Integer.parseInt(req.getParameter("days"));
		int limit = Integer.parseInt(req.getParameter("limit"));

		try {
			zip = Geocoder.getZipFromLatLng(latLng);
		} catch (JSONException e1) {
		}
		
		JSONArray jsonArray = new JSONArray();
		Events events = Jambase.getEvents(zip, radius, days);
		if (events!=null && events.getEvents()!=null && !events.getEvents().isEmpty()){
			for(Event event : events.getEvents()){
				for(Artist artist : event.getArtists()){
					Collection<Track> artistTracks = SoundCloud.getTracksByTrackSearch(artist.getArtist_name(), limit);
					for(Track track : artistTracks){
						jsonArray.put(trackToJson(track, artist));
					}
				}
				
			}
		}
		
		JSONObject json = new JSONObject();
		try {
			json.put("tracks",jsonArray);
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
	
}
