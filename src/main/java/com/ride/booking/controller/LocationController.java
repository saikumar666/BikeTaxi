package com.ride.booking.controller;

import com.ride.booking.model.Location;
import com.ride.booking.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/locations") // Base path for all location-related endpoints
public class LocationController {

  
	 private final LocationService locationService;

	    @Autowired
	    public LocationController(LocationService locationService) {
	        this.locationService = locationService;
	    }

	    @PostMapping("/create")
	    public Location createLocation(@RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude) {
	        // Create a new location
	        return locationService.createLocation(latitude, longitude);
	    }

	    @GetMapping("/check-in-service-area")
	    public ResponseEntity<?> checkLocationInServiceArea(@RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude) {
	        // Check if the provided location is within a service area
	        Location location = new Location(latitude, longitude);
	        boolean isInServiceArea = locationService.isLocationInServiceArea(location);

	        if (isInServiceArea) {
	            return ResponseEntity.ok("Location is within a service area.");
	        } else {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Location is not within a service area.");
	        }
	    }

	    // Add more endpoints as needed
}
