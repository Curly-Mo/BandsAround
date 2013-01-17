package com.curlymo.bandsaround;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
