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

import com.curlymo.bandsaround.lastfm.Event;
import com.curlymo.bandsaround.lastfm.Geo;
import com.curlymo.bandsaround.lastfm.PaginatedResult;
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
public class AsyncTracksRequestServlet extends HttpServlet {
    String soundCloudApiKey="84a2392830bf4d00a8fb7557613a36e6";
    String lastFMApiKey = "812ddaf7105675342e456ebf4eab4e92";
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        //long start = System.currentTimeMillis();
        //String retry = req.getParameter("retry");
        //int dayStart = Integer.parseInt(req.getParameter("dayStart"));
        //int dayEnd = Integer.parseInt(req.getParameter("dayEnd"));
        String page = req.getParameter("page");
        String maxEvents = req.getParameter("maxEvents");
        String radius = req.getParameter("radius");
        String tracksPerArtist = req.getParameter("tracksPerArtist");
        String lat = req.getParameter("latitude");
        String lng = req.getParameter("longitude");
        

        if(radius==null||radius==""){radius="30";}
        if(tracksPerArtist==null||tracksPerArtist==""){tracksPerArtist = "5";}
        if(page==null||page==""){page = "1";}
        if(maxEvents==null||maxEvents==""){maxEvents = "50";}
        double latitude = 0;
        double longitude = 0;
        if(lat == "" || lng == ""){
            String latLng = req.getHeader("X-AppEngine-CityLatLong");
            lat = latLng.split(",")[0];
            lng = latLng.split(",")[1];
        }
        latitude = Double.parseDouble(lat);
        longitude = Double.parseDouble(lng);
        

        URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();
        ArrayList<Future<HTTPResponse>> asyncResponses = new ArrayList<Future<HTTPResponse>>();
        ArrayList<String> trackArtists = new ArrayList<String>();
        ArrayList<Event> trackEvents = new ArrayList<Event>();
        /*if(retry==null){
            theEvents = Jambase.getEvents(zip, radius, dayStart, dayEnd);
        }else{
            theEvents = Jambase.getEventsAlternate(zip, radius, dayStart, dayEnd);
        }*/
        //out.println("Initial: " + (System.currentTimeMillis() - start));
        PaginatedResult<Event> events = Geo.getEvents(latitude, longitude, radius, Integer.parseInt(page), Integer.parseInt(maxEvents), lastFMApiKey);
        //out.println("gotEvents: " + (System.currentTimeMillis() - start));
        for(Event event : events.getPageResults()){
            for(String artist : event.getArtists()){
                URL url = SoundCloud.getTracksURLByTrackSearch(artist, Integer.parseInt(tracksPerArtist));
                Future<HTTPResponse> responseFuture = fetcher.fetchAsync(url);
                asyncResponses.add(responseFuture);
                trackArtists.add(artist);
                trackEvents.add(event);
            }
        }
        //out.println("parsedTracks: " + (System.currentTimeMillis() - start));
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
                        JSONObject venue = new JSONObject();
                        venue.put("name", trackEvents.get(index).getVenue().getName());
                        venue.put("latitude", trackEvents.get(index).getVenue().getLatitude());
                        venue.put("longitude", trackEvents.get(index).getVenue().getLongitude());
                        venue.put("image", trackEvents.get(index).getVenue().getImageURL(null));
                        venue.put("phone", trackEvents.get(index).getVenue().getPhonenumber());

                        JSONObject json = new JSONObject();
                        json.put("venue", venue);
                        json.put("title", track.getTitle());
                        json.put("artwork", track.getArtwork_url());
                        json.put("artist", trackArtists.get(index));
                        json.put("eventTitle", trackEvents.get(index).getTitle());
                        json.put("date", trackEvents.get(index).getStartDate());
                        json.put("ticketUrl", trackEvents.get(index).getWebsite());
                        json.put("streamURL", track.getStream_url()+"?client_id="+soundCloudApiKey);
                        json.put("lineup", new JSONArray(trackEvents.get(index).getArtists()));
                        json.put("headliner", trackEvents.get(index).getHeadliner());
                        jsonArray.put(json);
                        //json.put("venue", trackEvents.get(index).getVenue().getName());
                        //json.put("address", trackEvents.get(index).getVenue().getVenue_address());
                        //json.put("city", trackEvents.get(index).getVenue().getCity());
                        //json.put("state", trackEvents.get(index).getVenue().getVenue_stateCode());
                        //json.put("zip", trackEvents.get(index).getVenue().getPostal());
                        }
                    }
                } catch (InterruptedException e) {
                    // Guess you would do something here
                } catch (ExecutionException e) {
                    // Guess you would do something here
                } catch (JsonSyntaxException e) {
                    // Soundcloud service unavailable
                } catch (JSONException e) {
                e.printStackTrace();
                }
            index++;
            }
                
        JSONObject json = new JSONObject();
        try {
            json.put("tracks",shuffleJsonArray(jsonArray));
        } catch (JSONException e) {
            e.printStackTrace();
        }
                
        //out.println("parsedJson: " + (System.currentTimeMillis() - start));
        //out.println("size: " + jsonArray.length());
        resp.setContentType("application/json");
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
