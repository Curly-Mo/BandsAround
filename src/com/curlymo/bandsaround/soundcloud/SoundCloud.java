package com.curlymo.bandsaround.soundcloud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.curlymo.bandsaround.jambase.Events;
import com.google.gson.Gson;

public class SoundCloud {

	public static String getTracks(String artist) {
		Events events = null;
		User[] users = null;
		

		StringBuilder uriBuilder= new StringBuilder();
		uriBuilder.append("https://api.soundcloud.com/users.json");
		uriBuilder.append("?client_id=" + "84a2392830bf4d00a8fb7557613a36e6");
		uriBuilder.append("&q=" + artist);
		uriBuilder.append("&order=" + "hotness");
		uriBuilder.append("&limit=" + "1");
		String uri = uriBuilder.toString();

		URL url;
		try {
			url = new URL(uri);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/xml");
			connection.setReadTimeout(10000);
			
			String json = getJSON(uri, 10000);
			Gson gson = new Gson();
			users = gson.fromJson(json, User[].class);
			//Type collectionType = new TypeToken<Collection<channelSearchEnum>>(){}.getType();
			//Collection<channelSearchEnum> enums = gson.fromJson(json, collectionType);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return users[0].getUsername();
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
