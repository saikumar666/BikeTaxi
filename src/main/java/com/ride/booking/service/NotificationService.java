package com.ride.booking.service;

import com.ride.booking.model.Driver;
import com.ride.booking.model.RideRequest;
import com.ride.booking.model.User;

public interface NotificationService {
    void sendRideRequestNotificationToDriver(Driver driver, RideRequest rideRequest);

    void sendRideAssignedNotificationToUser(User user, RideRequest rideRequest);

    void sendRideAssignedNotificationToDriver(User driver, String riderName);
}
