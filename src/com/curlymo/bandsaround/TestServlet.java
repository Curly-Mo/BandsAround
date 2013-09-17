package com.curlymo.bandsaround;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.curlymo.bandsaround.songkick.api.Songkick;
import com.curlymo.bandsaround.songkick.api.objects.Artist;
import com.curlymo.bandsaround.songkick.api.objects.Event;
import com.curlymo.bandsaround.songkick.api.objects.EventFilter;
import com.curlymo.bandsaround.songkick.api.objects.LocationFilter;
import com.curlymo.bandsaround.songkick.api.objects.Performance;
import com.curlymo.bandsaround.soundcloud.SoundCloud;
import com.curlymo.bandsaround.soundcloud.Track;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class TestServlet extends HttpServlet {
	String soundCloudApiKey="84a2392830bf4d00a8fb7557613a36e6";
	String songkickApi = "Gt09daRzGgjAdFX3";
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	        double latitude = 39;
                double longitude = -74;
                int days = 0;
            
                Calendar cal = Calendar.getInstance();
	        Songkick songkick = new Songkick(songkickApi);
	        LocationFilter lf = new LocationFilter();
	        lf.setLat(latitude);
	        lf.setLng(longitude);
	        EventFilter ef = new EventFilter();
	        ef.setLocation(lf);
	        ef.setMinDate(cal.getTime());
	        cal.add(Calendar.DATE,7);
	        ef.setMaxDate(cal.getTime());
		
		
		JSONArray jsonArray = new JSONArray();
		
		List<Event> events = songkick.getEvents(ef);
	        for(Event event : events){
	            for(Performance performance : event.getPerformance()){
	                Artist artist = performance.getArtist();
    	                Collection<Track> artistTracks = SoundCloud.getTracksByTrackSearch(artist.getDisplayName(), 1);
    	                for(Track track : artistTracks){
    	                    jsonArray.put(trackToJson(track, artist));
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
			json.put("artist", artist.getDisplayName());
			json.put("streamURL", track.getStream_url()+"?client_id="+soundCloudApiKey);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return json;
	}
	
}
