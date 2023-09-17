package com.ride.booking.controller;

import com.ride.booking.model.Location;
import com.ride.booking.service.RouteDirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/route-direction")
public class GoogleMapsRouteDirectionController {

	private final RouteDirectionService routeDirectionService;

	@Autowired
	public GoogleMapsRouteDirectionController(RouteDirectionService routeDirectionService) {
		this.routeDirectionService = routeDirectionService;
	}

	@GetMapping("/calculate")
	public ResponseEntity<String> calculateRouteDirection(@RequestParam double startLatitude,
			@RequestParam double startLongitude, @RequestParam double endLatitude, @RequestParam double endLongitude) {

		// Create Location objects for the start and end points
		Location startLocation = new Location(startLatitude, startLongitude);
		Location endLocation = new Location(endLatitude, endLongitude);

		// Call the route direction service to get directions
		String directions = routeDirectionService.getRouteDirections(startLocation, endLocation);

		// You can further process the directions or return them as needed
		return ResponseEntity.ok(directions);
	}
}
