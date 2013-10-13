package com.curlymo.bandsaround;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
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
import com.google.appengine.labs.repackaged.org.json.JSONException;

@SuppressWarnings("serial")
public class BandsAroundServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        List<Track> tracks = new LinkedList<Track>();
        String latLng = req.getHeader("X-AppEngine-CityLatLong");
        String zip = "00000";
        String radius = "30";
        try {
            zip = Geocoder.getZipFromLatLng(latLng);
        } catch (JSONException e1) {
        }
        
        Events events = Jambase.getEvents(zip, radius);
        if (events!=null && events.getEvents()!=null && !events.getEvents().isEmpty()){
            for(Event event : events.getEvents()){
                for(Artist artist : event.getArtists()){
                    Collection<Track> artistTracks = SoundCloud.getTracksByTrackSearch(artist.getArtist_name(), 5);
                    tracks.addAll(artistTracks);
                }
                
            }
        }

        Collections.shuffle(tracks);
        
        req.setAttribute("tracks", tracks);
        try {
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        } catch (ServletException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
