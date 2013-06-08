package com.curlymo.bandsaround.echonest;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class EchoNestServlet extends HttpServlet{
    static String apiKey = "8C0DI9VHHE8BZSPOP";
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        PrintWriter out = resp.getWriter();
        String artistID = req.getParameter("artistID");
        
        StringBuilder uriBuilder= new StringBuilder();
        uriBuilder.append("http://developer.echonest.com/api/v4/artist/profile");
        uriBuilder.append("?api_key=" + apiKey);
        uriBuilder.append("&id=jambase:artist:" + artistID);
        uriBuilder.append("&bucket=biographies");
        uriBuilder.append("&bucket=familiarity");
        uriBuilder.append("&bucket=hotttnesss");
        uriBuilder.append("&bucket=images");
        uriBuilder.append("&bucket=artist_location");
        uriBuilder.append("&bucket=news");
        uriBuilder.append("&bucket=reviews");
        uriBuilder.append("&bucket=urls");
        uriBuilder.append("&bucket=video");
        uriBuilder.append("&bucket=years_active");
        uriBuilder.append("&format=json");
        String uri = uriBuilder.toString();
        
        out.println(uri);

    }
    
    
}
