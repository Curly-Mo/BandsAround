package com.curlymo.bandsaround.songkick;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("serial")
public class Event implements Serializable {

    String type;
    
    String status;
    
    @SerializedName("performance")
    List<Performance> performances;
    
    Venue venue;
    
    Start start;
    
    Location location;
    
    String displayName;
    
    String uri;
    
    double popularity;
    
    long id;
    
    
    
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public List<Performance> getPerformances() {
        return performances;
    }
    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }
    public Venue getVenue() {
        return venue;
    }
    public void setVenue(Venue venue) {
        this.venue = venue;
    }
    public Start getStart() {
        return start;
    }
    public void setStart(Start start) {
        this.start = start;
    }
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
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
    public double getPopularity() {
        return popularity;
    }
    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    

    
}