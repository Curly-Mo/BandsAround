package com.curlymo.bandsaround.songkick;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("serial")
public class Results implements Serializable{
    
    @SerializedName("event")
    List<Event> events;

    public List<Event> getEvents() {
        return events;
    }
    
    public void setEvents(List<Event> events) {
        this.events = events;
    }
   
   
}
