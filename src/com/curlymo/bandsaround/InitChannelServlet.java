package com.curlymo.bandsaround;
import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class InitChannelServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	    ChannelService channelService = ChannelServiceFactory.getChannelService();
	    //String channelKey = "xyz";
	    String channelKey = UUID.randomUUID().toString();
	    String token = channelService.createChannel(channelKey);
        
        resp.setContentType("application/json;charset=utf-8");

        JSONObject json = new JSONObject();

        try {
			json.put("token", token);
			json.put("channelKey", channelKey);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        resp.getWriter().print(json.toString());
        resp.getWriter().close();

        Queue queue = QueueFactory.getDefaultQueue();
        queue.add(withUrl("/eventstream").param("channelKey", channelKey).method(Method.GET));
	}
	
}
