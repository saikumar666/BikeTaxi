package com.ride.booking.controller;

import com.ride.booking.model.Location;
import com.ride.booking.model.RideRequest;
import com.ride.booking.model.User;
import com.ride.booking.service.LocationService;
import com.ride.booking.service.RideRequestService;
import com.ride.booking.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ride-requests")
public class RideRequestController {
	private final RideRequestService rideRequestService;
	private final UserService userService;
	private final LocationService locationService;

	@Autowired
	public RideRequestController(RideRequestService rideRequestService, UserService userService,
			LocationService locationService) {
		this.rideRequestService = rideRequestService;
		this.userService = userService;
		this.locationService = locationService;
	}

	@PostMapping("/request-ride")
	public ResponseEntity<String> requestRide(@RequestParam("userId") Long userId,
			@RequestParam("pickupLatitude") double pickupLatitude,
			@RequestParam("pickupLongitude") double pickupLongitude,
			@RequestParam("dropoffLatitude") double dropoffLatitude,
			@RequestParam("dropoffLongitude") double dropoffLongitude) {

		// Validate request parameters
		if (!isValidCoordinates(pickupLatitude, pickupLongitude)
				|| !isValidCoordinates(dropoffLatitude, dropoffLongitude)) {
			return ResponseEntity.badRequest().body("Invalid coordinates");
		}

		Location pickupLocation = new Location(pickupLatitude, pickupLongitude);

		if (!locationService.isLocationInServiceArea(pickupLocation)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sorry, we are not available in your area");
		}

		User user = userService.getUserById(userId);

		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}

		RideRequest rideRequest = rideRequestService.createRideRequest(user, pickupLocation,
				new Location(dropoffLatitude, dropoffLongitude));

		if (rideRequest != null) {
			rideRequestService.sendRideRequestNotification(rideRequest);
			return ResponseEntity.status(HttpStatus.OK).body("Ride requested successfully");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to request a ride");
		}
	}

	// Add more endpoints as needed

	private boolean isValidCoordinates(double latitude, double longitude) {
		// Implement validation logic for latitude and longitude values
		// You can check if they are within valid ranges or meet specific criteria
		return true; // Replace with your validation logic
	}
	
}
