package com.curlymo.bandsaround.jambase;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement(name="Response")
public class Events implements Serializable{

    List<Event> Events;
    
    @XmlElement(name="Events")
    public List<Event> getEvents() {
        return Events;
    }
    
    public void setEvents(List<Event> Events) {
        this.Events = Events;
    }

}
