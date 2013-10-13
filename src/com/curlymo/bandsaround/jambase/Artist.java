package com.curlymo.bandsaround.jambase;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

@SuppressWarnings("serial")
public class Artist implements Serializable{

    Long Id;
    String Name;
    
    @XmlElement
    public Long getArtist_id() {
        return Id;
    }
    
    public void setArtist_id(Long Id) {
        this.Id = Id;
    } 
    
    @XmlElement
    public String getArtist_name() {
        return Name;
    }
    
    public void setArtist_name(String Name) {
        this.Name = Name;
    } 

}
