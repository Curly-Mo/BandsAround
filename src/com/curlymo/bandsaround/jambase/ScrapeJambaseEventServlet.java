package com.curlymo.bandsaround.jambase;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ScrapeJambaseEventServlet extends HttpServlet {
    String soundCloudApiKey="84a2392830bf4d00a8fb7557613a36e6";
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        String latLng = req.getHeader("X-AppEngine-CityLatLong");
        String zip = req.getParameter("zip");
        String retry = req.getParameter("retry");
        String radius = req.getParameter("radius");
        
        
        resp.setContentType("application/json");
        out = resp.getWriter();
        out.print("");
        out.flush();
    }

}
