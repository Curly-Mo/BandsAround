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

import com.curlymo.bandsaround.songkick.api.objects.Location;

public class LocationResults implements Results {
    private List<Location> location;

    public List<Location> getLocation() {
        return location;
    }

    public void setLocation(List<Location> location) {
        this.location = location;
    }

    public List<Location> getList() {
        return location;
    }
}
