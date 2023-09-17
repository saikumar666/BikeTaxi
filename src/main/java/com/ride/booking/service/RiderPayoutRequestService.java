package com.ride.booking.service;

import com.ride.booking.model.Driver;
import com.ride.booking.model.RiderPayoutRequest;

public interface RiderPayoutRequestService {
    RiderPayoutRequest createRiderPayoutRequest(Driver driver, double amount);
    RiderPayoutRequest getRiderPayoutRequestById(Long id);
    // Add more methods as needed
}

