/**
 * $Revision$
 * $Date$
 * $Author$
 * 
 * $Log$
 *
 *
 * (c) 2008 Future Platforms
 * 
 */ 
package com.curlymo.bandsaround.songkick.api.helper;
import java.util.List;

import com.curlymo.bandsaround.songkick.api.objects.Event;

public class EventResults implements Results {
    private List<Event> event;

    public List<Event> getEvent() {
        return event;
    }

    public void setEvent(List<Event> event) {
        this.event = event;
    }

    public List<Event> getList() {
        return event;
    }
}
