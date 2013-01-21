package com.curlymo.bandsaround.jambase;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement(name="JamBase_Data")
public class Events implements Serializable{

	List<Event> events;
	
	@XmlElement(name="event")
    public List<Event> getEvents() {
        return events;
    }
    
    public void setEvents(List<Event> events) {
    	this.events = events;
    }

}
