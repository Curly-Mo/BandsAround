package com.curlymo.bandsaround.songkick;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Venue implements Serializable{
    
    double lat;
    double lng;
    MetroArea metroArea;
    String displayName;
    String uri;
    long id;
    
    
    public double getLat() {
        return lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    public double getLng() {
        return lng;
    }
    public void setLng(double lng) {
        this.lng = lng;
    }
    public MetroArea getMetroArea() {
        return metroArea;
    }
    public void setMetroArea(MetroArea metroArea) {
        this.metroArea = metroArea;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    
    
}

