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

	public static Events getEvents(String zip, String radius) {
		Events events = null;
		
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		
		StringBuilder uriBuilder= new StringBuilder();
		uriBuilder.append("http://api.jambase.com/search");
		uriBuilder.append("?zip=" + zip);
		uriBuilder.append("&radius=" + radius);
		uriBuilder.append("&startDate=" + dateFormat.format(cal.getTime()));
		cal.roll(Calendar.DATE, 7);
		uriBuilder.append("&endDate=" + dateFormat.format(cal.getTime()));
		uriBuilder.append("&apikey=" + "hwxvvh2mtphmygtwce4vtmfm");
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
		
		return events;
	}
	

}
