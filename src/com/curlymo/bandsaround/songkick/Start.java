package com.curlymo.bandsaround.songkick;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Start implements Serializable{
    String time;
    String date;
    String dateTime;
    
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getDateTime() {
        return dateTime;
    }
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
