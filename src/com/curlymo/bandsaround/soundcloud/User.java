package com.curlymo.bandsaround.soundcloud;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable{

	String username;
	
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
    	this.username = username;
    } 

}
