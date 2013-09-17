package com.curlymo.bandsaround.songkick;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Country implements Serializable{
    
    String displayName;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    
}
