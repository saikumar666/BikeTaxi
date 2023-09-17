package com.ride.booking.service;

import com.ride.booking.model.Location;

public interface RouteDirectionService {

	String getRouteDirections(Location startLocation, Location endLocation);
	//String getRouteDirections(Location startLocation, Location endLocation);
}
