package com.ride.booking.serviceImpl;

import com.ride.booking.model.Location;
import com.ride.booking.service.RouteDirectionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleMapsRouteDirectionServiceImpl implements RouteDirectionService {

	
	  private final RestTemplate restTemplate;
	    
	    @Value("${google.maps.api.key}")
	    private String apiKey; // Inject your Google Maps API key here

	    public GoogleMapsRouteDirectionServiceImpl() {
	        this.restTemplate = new RestTemplate();
	    }

	    @Override
	    public String getRouteDirections(Location startLocation, Location endLocation) {
	        String apiUrl = "https://maps.googleapis.com/maps/api/directions/json" +
	                "?origin=" + startLocation.getLatitude() + "," + startLocation.getLongitude() +
	                "&destination=" + endLocation.getLatitude() + "," + endLocation.getLongitude() +
	                "&key=" + apiKey;

	        // Make the API call to Google Maps Directions API
	        String response = restTemplate.getForObject(apiUrl, String.class);

	        // Process the response as needed (e.g., parse JSON response)
	        return response;
	    }
	    
	    

   
    
}
