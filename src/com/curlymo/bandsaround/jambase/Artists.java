package com.curlymo.bandsaround.jambase;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

@SuppressWarnings("serial")
public class Artists implements Serializable{

	List<Artist> artists;
	
	@XmlElement(name="artist")
    public List<Artist> getArtists() {
        return artists;
    }
    
    public void setArtists(List<Artist> artists) {
    	this.artists = artists;
    } 

}
