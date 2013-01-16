package com.curlymo.bandsaround.bandcamp;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class BandCampServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		
		//Events events = Jambase.getEvents("08054", "50");
		Collection<Track> tracks = BandCamp.getTracks("zedsdead");
		/*
		for (Track track : tracks) {
			resp.getWriter().println(track.getTitle());
			resp.getWriter().println(track.getGenre());		
			resp.getWriter().println(track.getStream_url());
			resp.getWriter().println("");
		}
		*/
		req.setAttribute("tracks", tracks);
		try {
			req.getRequestDispatcher("index.jsp").forward(req, resp);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
}
