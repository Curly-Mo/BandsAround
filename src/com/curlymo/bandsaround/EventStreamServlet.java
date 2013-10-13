package com.curlymo.bandsaround;
import java.io.IOException;
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
import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class EventStreamServlet extends HttpServlet {
    String soundCloudApiKey="84a2392830bf4d00a8fb7557613a36e6";
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ChannelService channelService = ChannelServiceFactory.getChannelService();
        String channelKey = req.getParameter("channelKey");
        String latLng = req.getHeader("X-AppEngine-CityLatLong");
        String zip = "00000";
        String radius = "30";
        try {
            zip = Geocoder.getZipFromLatLng(latLng);
        } catch (JSONException e1) {
        }
        
        Events events = Jambase.getEvents(zip, radius, 7);
        if (events!=null && events.getEvents()!=null && !events.getEvents().isEmpty()){
            for(Event event : events.getEvents()){
                for(Artist artist : event.getArtists()){
                    Collection<Track> artistTracks = SoundCloud.getTracksByTrackSearch(artist.getArtist_name(), 5);
                    for(Track track : artistTracks){
                        sendMessage(channelService, channelKey, track, artist);
                    }
                }
                
            }
        }
    }
    
    public void sendMessage(ChannelService channelService, String channelKey, Track track, Artist artist){
        JSONObject json = new JSONObject();

        try {
            json.put("title", track.getTitle());
            json.put("artist", artist.getArtist_name());
            json.put("streamURL", track.getStream_url()+"?client_id="+soundCloudApiKey);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        channelService.sendMessage(new ChannelMessage(channelKey, json.toString()));
    }
    
}
