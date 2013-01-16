package com.curlymo.bandsaround;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.curlymo.bandsaround.geocoder.Geocoder;
import com.curlymo.bandsaround.jambase.Artist;
import com.curlymo.bandsaround.jambase.Event;
import com.curlymo.bandsaround.jambase.Events;
import com.curlymo.bandsaround.jambase.Jambase;
import com.curlymo.bandsaround.soundcloud.SoundCloud;
import com.curlymo.bandsaround.soundcloud.Track;
import com.google.appengine.labs.repackaged.org.json.JSONException;

@SuppressWarnings("serial")
public class EventStreamServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/event-stream");  

        PrintWriter out = resp.getWriter();  
        
for(int i =0;i<5;i++){
				        String url="Some URL";
				        String title="Some Title";

				        out.print("data: {\n");
				        out.print("data: \"url\": \"" + url + "\",\n");
				        out.print("data: \"title\": \"" + title + "\"\n");
				        out.print("data: }\n\n");
				        
				        try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
}

        	
        out.flush();  
        out.close();
	}
}
