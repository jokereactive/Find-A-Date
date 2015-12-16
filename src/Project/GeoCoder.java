package Project;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class GeoCoder {

    	/*Geocode request URL. Here see we are passing "json" it means we will get the output in JSON format.
    	* You can also pass "xml" instead of "json" for XML output.
    	* For XML output URL will be "http://maps.googleapis.com/maps/api/geocode/xml"; 
    	*/

    	private static final String URL = "http://maps.googleapis.com/maps/api/geocode/json"; 

    	/*
    	* Here the fullAddress String is in format like "address,city,state,zipcode". Here address means "street number + route" .
    	*
    	*/
    	public String getJSONByGoogle(String fullAddress) throws IOException {

    	/*
    	* Create an java.net.URL object by passing the request URL in constructor. 
    	* Here you can see I am converting the fullAddress String in UTF-8 format. 
    	* You will get Exception if you don't convert your address in UTF-8 format. Perhaps google loves UTF-8 format. :)
    	* In parameter we also need to pass "sensor" parameter.
    	* sensor (required parameter) — Indicates whether or not the geocoding request comes from a device with a location sensor. This value must be either true or false.
    	*/
    		System.out.println(fullAddress);
    	URL url = new URL(URL + "?address=" + URLEncoder.encode(fullAddress, "UTF-8")+ "&sensor=false");

    	// Open the Connection 
    	URLConnection conn = url.openConnection();


    	BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line+"\n");
        }
        br.close();

    	return sb.toString(); // This returned String is JSON string from which you can retrieve all key value pair and can save it in POJO.
    	}
}
