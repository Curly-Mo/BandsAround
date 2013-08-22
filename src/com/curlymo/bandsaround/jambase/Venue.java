package com.curlymo.bandsaround.jambase;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

@SuppressWarnings("serial")
public class Venue implements Serializable{
	long Id;
	String Name;
	String Address;
	String City;
	String State;
	String StateCode;
	String Country;
	String CountryCode;
	String ZipCode;
	String Url;
	long Latitude;
	long Longitude;
	
	@XmlElement
    public long getVenue_id() {
        return Id;
    }
    
    public void setVenue_id(long Id) {
    	this.Id = Id;
    }   
	
	@XmlElement
    public String getVenue_name() {
        return Name;
    }
    
    public void setVenue_name(String Name) {
    	this.Name = Name;
    }   

    @XmlElement
    public String getVenue_address() {
        return Address;
    }
    
    public void setVenue_address(String Address) {
        this.Address = Address;
    }  
    
	@XmlElement
    public String getVenue_city() {
        return City;
    }
    
    public void setVenue_city(String City) {
    	this.City = City;
    }   
	
	@XmlElement
    public String getVenue_state() {
        return State;
    }
    
    public void setVenue_state(String State) {
    	this.State = State;
    }  
    
    @XmlElement
    public String getVenue_stateCode() {
        return StateCode;
    }
    
    public void setVenue_stateCode(String StateCode) {
        this.StateCode = StateCode;
    }   
    
    @XmlElement
    public String getVenue_Country() {
        return Country;
    }
    
    public void setVenue_country(String Country) {
        this.Country = Country;
    }   
    
    @XmlElement
    public String getVenue_countryCode() {
        return CountryCode;
    }
    
    public void setVenue_countryCode(String CountryCode) {
        this.CountryCode = CountryCode;
    }   
	
	@XmlElement
    public String getVenue_zip() {
        return ZipCode;
    }
    
    public void setVenue_zip(String ZipCode) {
    	this.ZipCode = ZipCode;
    }   
    
    @XmlElement
    public String getVenue_url() {
        return Url;
    }
    
    public void setVenue_url(String Url) {
        this.Url = Url;
    }   
    
    @XmlElement
    public long getVenue_latitude() {
        return Latitude;
    }
    
    public void setVenue_latitude(long Latitude) {
        this.Latitude = Latitude;
    }  
    
    @XmlElement
    public long getVenue_longitude() {
        return Longitude;
    }
    
    public void setVenue_longitude(long Longitude) {
        this.Longitude = Longitude;
    }  
}

