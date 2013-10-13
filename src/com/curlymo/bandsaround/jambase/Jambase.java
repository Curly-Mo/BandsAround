package com.curlymo.bandsaround.jambase;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Jambase {
    static String jambaseApiKey="eyt4zthpt9ysqjku2fnaqe98";
    static String jambaseApiKeyAlternate="vwt5vdwjsjbd967gx8znv3hv";

    public static Events getEvents(String zip, String radius, int dayStart, int dayEnd){
        return getEvents(zip, radius, dayStart, dayEnd, jambaseApiKey);
    }
        public static Events getEventsAlternate(String zip, String radius, int dayStart, int dayEnd){
            return getEvents(zip, radius, dayStart, dayEnd, jambaseApiKeyAlternate);
        }
        
        public static Events getEvents(String zip, String radius, int days) {
            return getEvents(zip,radius,0,days);
        }
        
        public static Events getEvents(String zip, String radius) {
                return getEvents(zip,radius,0,7);
        }
    
    public static Events getEvents(String zip, String radius, int dayStart, int dayEnd, String apiKey) {
        Events events = null;
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 4);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
        StringBuilder uriBuilder= new StringBuilder();
        uriBuilder.append("http://api.jambase.com/events");
        uriBuilder.append("?zipCode=" + zip);
        uriBuilder.append("&radius=" + radius);
        cal.add(Calendar.DATE, dayStart);
        uriBuilder.append("&startDate=" + dateFormat.format(cal.getTime()));
        cal.add(Calendar.DATE, dayEnd+1);
        uriBuilder.append("&endDate=" + dateFormat.format(cal.getTime()));
        uriBuilder.append("&api_key=" + apiKey);
        String uri = uriBuilder.toString();

        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/xml");
            connection.setReadTimeout(10000);
            
            InputStream xml = connection.getInputStream();
    
            JAXBContext context = JAXBContext.newInstance(Events.class);
            Unmarshaller unMarshaller = context.createUnmarshaller();
            events = (Events) unMarshaller.unmarshal(xml);
                
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.err.println(uri);
        //System.err.println(events.getEvents());
        return events;
    }


}
