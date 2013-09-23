package com.curlymo.bandsaround.songkick.api.objects;

import java.io.Serializable;

public class ClientLocation implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -2572506643764652294L;
    private String ip;
    private double lat;
    private double lng;
    private long metroAreaId;
    
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
    public long getMetroAreaId() {
        return metroAreaId;
    }
    public void setMetroAreaId(long metroAreaId) {
        this.metroAreaId = metroAreaId;
    }
    
}