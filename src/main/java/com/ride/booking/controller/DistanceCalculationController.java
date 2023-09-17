package com.ride.booking.controller;

import com.ride.booking.service.DistanceCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/distance")
public class DistanceCalculationController {
	@Autowired
	private final DistanceCalculationService distanceCalculationService;

	
	public DistanceCalculationController(DistanceCalculationService distanceCalculationService) {
		this.distanceCalculationService = distanceCalculationService;
	}

	@GetMapping("/calculate")
	public ResponseEntity<String> calculateDistance(@RequestParam double startLatitude,
			@RequestParam double startLongitude, @RequestParam double endLatitude, @RequestParam double endLongitude) {

		try {
			double distance = distanceCalculationService.calculateDistance(startLatitude, startLongitude, endLatitude,
					endLongitude);
			String responseMessage = "Distance: " + distance + " kilometers";
			return ResponseEntity.ok(responseMessage);
		} catch (Exception e) {
			String errorMessage = "Distance calculation failed";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}
}
