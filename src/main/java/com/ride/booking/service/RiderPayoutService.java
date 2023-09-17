package com.ride.booking.service;

import com.ride.booking.model.Driver;
import com.ride.booking.model.RiderPayout;

public interface RiderPayoutService {
	RiderPayout createRiderPayout(Driver driver, double amount);

	RiderPayout getRiderPayoutById(Long id);
	// Add more methods as needed
}
