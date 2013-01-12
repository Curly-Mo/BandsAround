package com.curlymo.bandsaround.jambase;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

@SuppressWarnings("serial")
public class Artist implements Serializable{

	Long artist_id;
	String artist_name;
	
	@XmlElement
    public Long getArtist_id() {
        return artist_id;
    }
    
    public void setArtist_id(Long artist_id) {
    	this.artist_id = artist_id;
    } 
    
	@XmlElement
    public String getArtist_name() {
        return artist_name;
    }
    
    public void setArtist_name(String artist_name) {
    	this.artist_name = artist_name;
    } 

}
