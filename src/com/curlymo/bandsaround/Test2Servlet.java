package com.curlymo.bandsaround;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.curlymo.bandsaround.geocoder.Geocoder;
import com.curlymo.bandsaround.geocoder.LatLng;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;

@SuppressWarnings("serial")
public class Test2Servlet extends HttpServlet {
    String soundCloudApiKey="84a2392830bf4d00a8fb7557613a36e6";
    String lastFMApiKey = "812ddaf7105675342e456ebf4eab4e92";
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        String address = req.getParameter("address");
        LatLng latlng = null;
        try {
            latlng = Geocoder.getLatLngFromAddress(address);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        out.print(latlng.getLat());
        out.print(", ");
        out.print(latlng.getLng());
        out.flush();
        }

    public static JSONArray shuffleJsonArray(JSONArray array) throws JSONException {
        // Implementing Fisher–Yates shuffle
        Random rnd = new Random();
        for (int i = array.length() - 1; i >= 0; i--)
            {
            int j = rnd.nextInt(i + 1);
            // Simple swap
            Object object = array.get(j);
            array.put(j, array.get(i));
            array.put(i, object);
            }
        return array;
        }
}
