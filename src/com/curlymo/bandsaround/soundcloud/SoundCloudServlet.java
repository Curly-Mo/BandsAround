package com.curlymo.bandsaround.soundcloud;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class SoundCloudServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		
		//Events events = Jambase.getEvents("08054", "50");
		String user = SoundCloud.getTracks("metronomy");
		resp.getWriter().println(user);
	}
	
	
}
