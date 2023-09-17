package com.ride.booking.service;

import java.util.List;

import com.ride.booking.model.Driver;
import com.ride.booking.model.Location;
import com.ride.booking.model.RideRequest;
import com.ride.booking.model.User;

public interface RideRequestService {
	RideRequest createRideRequest(User user, Location startLocation, Location endLocation);

	RideRequest getRideRequestById(Long id);

	void sendRideRequestNotification(RideRequest rideRequest);

	void assignDriverToRideRequest(RideRequest rideRequest, Driver driver);

    List<Driver> findNearbyDrivers(Location location);


}