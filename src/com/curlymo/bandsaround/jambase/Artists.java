package com.curlymo.bandsaround.jambase;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

@SuppressWarnings("serial")
public class Artists implements Serializable{

    List<Artist> Artists;
    
    @XmlElement(name="Artist")
    public List<Artist> getArtists() {
        return Artists;
    }
    
    public void setArtists(List<Artist> Artists) {
        this.Artists = Artists;
    } 

}
