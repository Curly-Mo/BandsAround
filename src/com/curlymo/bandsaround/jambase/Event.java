package com.curlymo.bandsaround.jambase;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;


@SuppressWarnings("serial")
public class Event implements Serializable {

	long event_id;
	String event_url;
	String event_date;
	Venue venue;
	Artists artists;
	
	@XmlElement
    public long getEvent_id() {
        return event_id;
    }
    
    public void setEvent_id(long event_id) {
    	this.event_id = event_id;
    }
	
    @XmlElement(name="artists")
    public Artists getArtistsObject() {
        return artists;
    }
    
    public void setArtistsObject(Artists artists) {
    	this.artists = artists;
    }    
	
	@XmlElement
    public String getEvent_url() {
        return event_url;
    }
    
    public void setEvent_url(String event_url) {
    	this.event_url = event_url;
    }  
    
	@XmlElement
    public String getEvent_date() {
        return event_date;
    }
    
    public void setEvent_date(String event_date) {
    	this.event_date = event_date;
    }  
    
	@XmlElement
    public Venue getVenue() {
        return venue;
    }
    
    public void setVenue(Venue venue) {
    	this.venue = venue;
    }  
    
    public List<Artist> getArtists() {
        return artists.getArtists();
    }
    
}