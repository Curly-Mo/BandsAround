package com.curlymo.bandsaround.jambase;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;


@SuppressWarnings("serial")
public class Event implements Serializable {

	long Id;
	String Date;
	Venue Venue;
	Artists Artists;
        String TicketUrl;
	
	@XmlElement
    public long getEvent_id() {
        return Id;
    }
    
    public void setEvent_id(long Id) {
    	this.Id = Id;
    }
	
    @XmlElement(name="Artists")
    public Artists getArtistsObject() {
        return Artists;
    }
    
    public void setArtistsObject(Artists Artists) {
    	this.Artists = Artists;
    }    
	
	@XmlElement
    public String getEvent_ticketUrl() {
        return TicketUrl;
    }
    
    public void getEvent_ticketUrl(String TicketUrl) {
    	this.TicketUrl = TicketUrl;
    }  
    
	@XmlElement
    public String getEvent_date() {
        return Date;
    }
    
    public void setEvent_date(String Date) {
    	this.Date = Date;
    }  
    
	@XmlElement
    public Venue getVenue() {
        return Venue;
    }
    
    public void setVenue(Venue Venue) {
    	this.Venue = Venue;
    }  
    
    public List<Artist> getArtists() {
        return Artists.getArtists();
    }
    
}