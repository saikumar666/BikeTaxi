package com.ride.booking.service;

import java.util.List;

import com.ride.booking.model.Driver;
import com.ride.booking.model.Location;
import com.ride.booking.model.RideRequest;

public interface DriverService {
   Driver createDriver(String name, String email, String phoneNumber, Location location);

    Driver getDriverById(Long id);

    boolean acceptRideRequest(Driver driver, RideRequest rideRequest);

    boolean cancelRideRequest(Driver driver, RideRequest rideRequest);

    List<Driver> findNearbyDrivers(Location riderLocation);


    // Add more methods as needed
}
