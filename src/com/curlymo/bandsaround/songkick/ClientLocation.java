package com.curlymo.bandsaround.songkick;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ClientLocation implements Serializable{

    String ip;
    double lat;
    double lng;
    MetroArea metroAreaId;
    
    
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
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
    public MetroArea getMetroAreaId() {
        return metroAreaId;
    }
    public void setMetroAreaId(MetroArea metroAreaId) {
        this.metroAreaId = metroAreaId;
    }
    
}
