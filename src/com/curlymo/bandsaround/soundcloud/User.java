package com.curlymo.bandsaround.soundcloud;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable{

	String id;
	String username;
	String permalink;
	String uri;
	String permalink_url;
	String avatar_url;
	String country;
	String full_name;
	String city;
	String description;
	String discogs_name;
	String myspace_name;
	String website;
	String website_title;
	Boolean online;
	String track_count;
	String playlist_count;
	String followers_count;
	String public_favorites_count;

    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
    	this.username = username;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPermalink() {
		return permalink;
	}

	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getPermalink_url() {
		return permalink_url;
	}

	public void setPermalink_url(String permalink_url) {
		this.permalink_url = permalink_url;
	}

	public String getAvatar_url() {
		return avatar_url;
	}

	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDiscogs_name() {
		return discogs_name;
	}

	public void setDiscogs_name(String discogs_name) {
		this.discogs_name = discogs_name;
	}

	public String getMyspace_name() {
		return myspace_name;
	}

	public void setMyspace_name(String myspace_name) {
		this.myspace_name = myspace_name;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getWebsite_title() {
		return website_title;
	}

	public void setWebsite_title(String website_title) {
		this.website_title = website_title;
	}

	public Boolean getOnline() {
		return online;
	}

	public void setOnline(Boolean online) {
		this.online = online;
	}

	public String getTrack_count() {
		return track_count;
	}

	public void setTrack_count(String track_count) {
		this.track_count = track_count;
	}

	public String getPlaylist_count() {
		return playlist_count;
	}

	public void setPlaylist_count(String playlist_count) {
		this.playlist_count = playlist_count;
	}

	public String getFollowers_count() {
		return followers_count;
	}

	public void setFollowers_count(String followers_count) {
		this.followers_count = followers_count;
	}

	public String getPublic_favorites_count() {
		return public_favorites_count;
	}

	public void setPublic_favorites_count(String public_favorites_count) {
		this.public_favorites_count = public_favorites_count;
	} 

}
