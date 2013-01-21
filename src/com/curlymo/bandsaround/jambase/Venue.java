package com.curlymo.bandsaround.jambase;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

@SuppressWarnings("serial")
public class Venue implements Serializable{
	String venue_id;
	String venue_name;
	String venue_city;
	String venue_state;
	String venue_zip;
	
	@XmlElement
    public String getVenue_id() {
        return venue_id;
    }
    
    public void setVenue_id(String venue_id) {
    	this.venue_id = venue_id;
    }   
	
	@XmlElement
    public String getVenue_name() {
        return venue_name;
    }
    
    public void setVenue_name(String venue_name) {
    	this.venue_name = venue_name;
    }   
	
	@XmlElement
    public String getVenue_city() {
        return venue_city;
    }
    
    public void setVenue_city(String venue_city) {
    	this.venue_city = venue_city;
    }   
	
	@XmlElement
    public String getVenue_state() {
        return venue_state;
    }
    
    public void setVenue_state(String venue_state) {
    	this.venue_state = venue_state;
    }   
	
	@XmlElement
    public String getVenue_zip() {
        return venue_zip;
    }
    
    public void setVenue_zip(String venue_zip) {
    	this.venue_zip = venue_zip;
    }   
}

