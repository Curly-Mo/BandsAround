package com.curlymo.bandsaround.songkick;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Performance implements Serializable{

    Artist artist;
    int billingIndex;
    String billing;
    String displayName;
    long id;
    
    
    public Artist getArtist() {
        return artist;
    }
    public void setArtist(Artist artist) {
        this.artist = artist;
    }
    public int getBillingIndex() {
        return billingIndex;
    }
    public void setBillingIndex(int billingIndex) {
        this.billingIndex = billingIndex;
    }
    public String getBilling() {
        return billing;
    }
    public void setBilling(String billing) {
        this.billing = billing;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    
}
