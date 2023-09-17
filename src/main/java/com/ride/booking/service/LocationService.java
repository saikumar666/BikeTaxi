package com.ride.booking.service;

import com.ride.booking.model.Location;

public interface LocationService {
    Location getLocationById(Long id);
    boolean isLocationInServiceArea(Location location); // Add this method
	Location createLocation(double latitude, double longitude);
}
