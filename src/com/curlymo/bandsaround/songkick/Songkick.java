package com.curlymo.bandsaround.songkick;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.google.gson.Gson;

public class Songkick {
    static String songkickApiKey="Gt09daRzGgjAdFX3";
    //static String jambaseApiKeyAlternate="vwt5vdwjsjbd967gx8znv3hv";

    public static List<Event> getEvents(String latitude, String longitude, int dayStart, int dayEnd){
        return getEvents(latitude,longitude, dayStart, dayEnd, songkickApiKey);
    }
    
        //public static List<Event> getEventsAlternate(String latitude, String longitude, int dayStart, int dayEnd){
        //    return getEvents(latitude,longitude, dayStart, dayEnd, jambaseApiKeyAlternate);
        //}
        
        public static List<Event> getEvents(String latitude, String longitude, int days) {
            return getEvents(latitude,longitude,0,days);
        }
        
        public static List<Event> getEvents(String latitude, String longitude) {
                return getEvents(latitude,longitude,0,7);
        }
    
    public static List<Event> getEvents(String latitude, String longitude, int dayStart, int dayEnd, String apiKey) {
        List<Event> events = null;
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 4);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
        StringBuilder uriBuilder= new StringBuilder();
        uriBuilder.append("http://api.songkick.com/api/3.0/events.json");
        uriBuilder.append("?location=geo:" + latitude + "," + longitude);
        cal.add(Calendar.DATE, dayStart);
        uriBuilder.append("&min_date=" + dateFormat.format(cal.getTime()));
        cal.add(Calendar.DATE, dayEnd+1);
        uriBuilder.append("&max_date=" + dateFormat.format(cal.getTime()));
        uriBuilder.append("&apikey=" + apiKey);
        String uri = uriBuilder.toString();

        try {
                URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/xml");
            connection.setReadTimeout(10000);
            
            String json = getJSON(uri, 10000);
                        Gson gson = new Gson();
                        System.err.println(uri);
                        System.err.println(json);
                        SongkickResult result = gson.fromJson(json, SongkickResult.class);
                        
                        events = result.getResultsPage().getResults().getEvents();
                
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.err.println(uri);
        //System.err.println(events.getEvents());
        return events;
    }
    
    
    
        public static String getJSON(String url, int timeout) {
            try {
                URL u = new URL(url);
                HttpURLConnection c = (HttpURLConnection) u.openConnection();
                c.setRequestMethod("GET");
                c.setRequestProperty("Content-length", "0");
                c.setUseCaches(false);
                c.setAllowUserInteraction(false);
                c.setConnectTimeout(timeout);
                c.setReadTimeout(timeout);
                c.connect();
                int status = c.getResponseCode();
                
                switch (status) {
                case 200:
                    case 201:
                        BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line+"\n");
                        }
                        br.close();
                        return sb.toString();
                    }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


}
