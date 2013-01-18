package com.curlymo.bandsaround;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

@SuppressWarnings("serial")
public class TestServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	    ChannelService channelService = ChannelServiceFactory.getChannelService();

	    // The 'Game' object exposes a method which creates a unique string based on the game's key
	    // and the user's id.
	    String channelKey = req.getParameter("channelKey");

	    // Index is the contents of our index.html resource, details omitted for brevity.

	    while(true){
	    	channelService.sendMessage(new ChannelMessage(channelKey, "Hello World!"));
	    	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
        
	}
	
}
