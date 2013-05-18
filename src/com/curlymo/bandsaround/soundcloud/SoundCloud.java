package com.curlymo.bandsaround.soundcloud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Collections;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SoundCloud {
        static String clientID = "84a2392830bf4d00a8fb7557613a36e6";
	public static User getUser(String artist) throws UnsupportedEncodingException {
		//Events events = null;
		Collection<User> users = null;
		

		StringBuilder uriBuilder= new StringBuilder();
		uriBuilder.append("https://api.soundcloud.com/users.json");
		uriBuilder.append("?client_id=" + clientID);
		uriBuilder.append("&q=" + URLEncoder.encode(artist,"UTF-8"));
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
			//users = gson.fromJson(json, User[].class);
			Type collectionType = new TypeToken<Collection<User>>(){}.getType();
			users = gson.fromJson(json, collectionType);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(users != null && !users.isEmpty()){
			return users.iterator().next();
		}
		return null;
	}
	
	public static Collection<Track> getTracks(String artist) throws UnsupportedEncodingException {
		User user = getUser(artist);
		if(user==null){
			return Collections.emptyList();
		}
		Collection<Track> tracks = null;
		
		StringBuilder uriBuilder= new StringBuilder();
		uriBuilder.append("https://api.soundcloud.com");
		uriBuilder.append("/users/"+user.getId());
		uriBuilder.append("/tracks.json");
		uriBuilder.append("?client_id=" + clientID);
		uriBuilder.append("&order=" + "hotness");
		uriBuilder.append("&limit=" + "10");
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
			//users = gson.fromJson(json, User[].class);
			Type collectionType = new TypeToken<Collection<Track>>(){}.getType();
			tracks = gson.fromJson(json, collectionType);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return tracks;
	}
	
	public static Collection<Track> getTracksByTrackSearch(String artist, int limit) throws UnsupportedEncodingException {
		Collection<Track> tracks = null;
		
		StringBuilder uriBuilder= new StringBuilder();
		uriBuilder.append("https://api.soundcloud.com");
		uriBuilder.append("/tracks.json");
		uriBuilder.append("?client_id=" + clientID);
		uriBuilder.append("&q=" + URLEncoder.encode(artist,"UTF-8"));
		uriBuilder.append("&username=" + URLEncoder.encode(artist,"UTF-8"));
		uriBuilder.append("&order=" + "hotness");
		uriBuilder.append("&limit=" + limit);
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
			//users = gson.fromJson(json, User[].class);
			Type collectionType = new TypeToken<Collection<Track>>(){}.getType();
			tracks = gson.fromJson(json, collectionType);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (tracks==null){
			return Collections.emptyList();
		}
		return tracks;
	}
	
	public static URL getTracksURLByTrackSearch(String artist, int limit) throws UnsupportedEncodingException, MalformedURLException {	
		StringBuilder uriBuilder= new StringBuilder();
		uriBuilder.append("https://api.soundcloud.com");
		uriBuilder.append("/tracks.json");
		uriBuilder.append("?client_id=" + clientID);
		uriBuilder.append("&q=" + URLEncoder.encode(artist,"UTF-8"));
		uriBuilder.append("&username=" + URLEncoder.encode(artist,"UTF-8"));
		uriBuilder.append("&order=" + "hotness");
		uriBuilder.append("&limit=" + limit);
		String uri = uriBuilder.toString();

		URL url = new URL(uri);
		return url;
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
