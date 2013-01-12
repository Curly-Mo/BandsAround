package com.curlymo.bandsaround.jambase;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class JambaseServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");

		Events events = Jambase.getEvents("08054", "50");
		    
		if (events!=null){
		    for(Event event : events.getEvents()){
		    	resp.getWriter().println(event.getEvent_id());
		    	resp.getWriter().println(event.getEvent_url());
		    	resp.getWriter().println(event.getEvent_date());
		    	resp.getWriter().println(event.getVenue().getVenue_id());
		    	resp.getWriter().println(event.getVenue().getVenue_name());
		    	resp.getWriter().println(event.getVenue().getVenue_city());
		    	resp.getWriter().println(event.getVenue().getVenue_state());
		    	resp.getWriter().println(event.getVenue().getVenue_zip());
		    	for(Artist artist : event.getArtists()){
			    	resp.getWriter().println(artist.getArtist_name());			    	
			    	resp.getWriter().println(artist.getArtist_id());
		    	}
		    	resp.getWriter().println(" ");
	
		    	req.getSession().setAttribute("events", events);
		    }
		}else{
			resp.getWriter().println("No Events");
		}
	
	}
	
	
}
