/*
 * Author - Sarthak Ahuja, 2012088
 */

package Project;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;

import javafx.fxml.FXML;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Button;


public class Controller implements Initializable, MapComponentInitializedListener {
    
    @FXML
    private Button button;
    
    @FXML
    private GoogleMapView mapView;
    
    private GoogleMap map;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.addMapInializedListener(this);
    }    

    String MY_ACCESS_TOKEN="X";
   



    
    @Override
    public void mapInitialized() {
    	FacebookClient facebookClient = new DefaultFacebookClient(MY_ACCESS_TOKEN);
    	User sarthak= facebookClient.fetchObject("me", User.class);
    	ArrayList<LatLong> plot = new ArrayList<LatLong>();

    	GeoCoder g=new GeoCoder();

    	System.out.println("Sarthak's Friend Plot: " + sarthak.getHometownName());
    	Connection<User> myFriends = facebookClient.fetchConnection("me/friends", User.class);
    	
    	System.out.println("Count of my friends: " + myFriends.getData().size());
    	
    	for (User user : myFriends.getData()){
    		User friend= facebookClient.fetchObject(user.getId(), User.class);

    	    String place= friend.getHometownName();
    	    if(place!=null){
    	    	try {
					String json = g.getJSONByGoogle(place);
					Map mapping = new Gson().fromJson(json, Map.class);
					List l = (List) mapping.get("results");
					mapping = (Map) l.get(0);
					mapping=(Map) mapping.get("geometry");
					mapping=(Map) mapping.get("location");
					System.out.println(mapping);
	    	    	plot.add(new LatLong(Double.parseDouble(String.valueOf(mapping.get("lat"))),Double.parseDouble(String.valueOf(mapping.get("lng")))));
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	    }
    		
    	}        
        
        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();
        
        mapOptions.center(new LatLong(28.6100, 77.2300))
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(12);
                   
        map = mapView.createMap(mapOptions);

        //Add markers to the map
        for(LatLong l:plot){
        	MarkerOptions m = new MarkerOptions();
        	m.position(l);
            Marker marker = new Marker(m);
            map.addMarker( marker );
        }
        
        
    }   
}