package com.ride.booking.service;

import com.ride.booking.model.Location;
import com.ride.booking.model.Ride;
import com.ride.booking.model.User;

public interface RideService {
    Ride createRide(User user, Location startLocation, Location endLocation, double amount);

    Ride getRideById(Long id);

   double requestRide(User user, Location startLocation, Location endLocation);

    void sendRideRequestNotification(Ride ride);
}
