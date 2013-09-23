package com.curlymo.bandsaround.geocoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.curlymo.bandsaround.json.JSON;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.gson.Gson;

public class Geocoder {

    public static String getZipFromLatLng(String latlng) throws JSONException {
        if(latlng==null || latlng.equals("")){
            return "92865";
        }
        String url = "http://maps.googleapis.com/maps/api/geocode/json?latlng=";
        url+= latlng;
        url+= "&sensor=false";
        url = url.replace(' ', '+');
        String geoJSON = JSON.getJSON(url, 10000);
        //GeocoderResponseType geoLocationResult = new Gson().fromJson(geoJSON, GeocoderResponseType.class);
        ArrayList<InfoPoint> locations = parsePoints(geoJSON);
        locations.get(0);
        String zip = "00000";
        if(!locations.isEmpty()){
            for(HashMap<String, Object> addressComponent : locations.get(0).getAddressFields()){
                if((addressComponent.get("types")).equals("[\"postal_code\"]")){
                    zip = (String) addressComponent.get("long_name");
                }
            }
        }
        return zip;
    }
    
    public static LatLng getLatLngFromAddress(String address) throws JSONException {
        if(address==null || address.equals("")){
            return new LatLng();
        }
        String url = "http://maps.googleapis.com/maps/api/geocode/json?address=";
        url+= address;
        url+= "&sensor=false";
        url = url.replace(' ', '+');
        String geoJSON = JSON.getJSON(url, 10000);
        GeocoderResponseType geoLocationResult = new Gson().fromJson(geoJSON, GeocoderResponseType.class);
        LatLng latlng = geoLocationResult.getResults().get(0).getGeometry().getLocation();
        
        return latlng;
    }
    
    private static ArrayList<InfoPoint> parsePoints(String strResponse) {
        // TODO Auto-generated method stub
        ArrayList<InfoPoint> result=new ArrayList<InfoPoint>();
        try {
            JSONObject obj=new JSONObject(strResponse);
            JSONArray array=obj.getJSONArray("results");
            for(int i=0;i<array.length()||i<1;i++)
            {
                InfoPoint point=new InfoPoint();

                JSONObject item=array.getJSONObject(i);
                ArrayList<HashMap<String, Object>> tblPoints=new ArrayList<HashMap<String,Object>>();
                JSONArray jsonTblPoints=item.getJSONArray("address_components");
                for(int j=0;j<jsonTblPoints.length();j++)
                {
                    JSONObject jsonTblPoint=jsonTblPoints.getJSONObject(j);
                    HashMap<String, Object> tblPoint=new HashMap<String, Object>();
                    @SuppressWarnings("unchecked")
                    Iterator<String> keys=jsonTblPoint.keys();
                    while(keys.hasNext())
                    {
                        String key=(String) keys.next();
                        if(tblPoint.get(key) instanceof JSONArray)
                        {
                            tblPoint.put(key, jsonTblPoint.getJSONArray(key));
                        }
                        tblPoint.put(key, jsonTblPoint.getString(key));
                    }
                    tblPoints.add(tblPoint);
                }
                point.setAddressFields(tblPoints);
                point.setStrFormattedAddress(item.getString("formatted_address"));
                JSONObject geoJson=item.getJSONObject("geometry");
                JSONObject locJson=geoJson.getJSONObject("location");
                point.setDblLatitude(Double.parseDouble(locJson.getString("lat")));
                point.setDblLongitude(Double.parseDouble(locJson.getString("lng")));

                result.add(point);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }
}
