package com.ride.booking.service;

import com.ride.booking.model.Commission;

public interface CommissionService {
	Commission createCommission(double percentage);

	Commission getCommissionById(Long id);
	// Add more methods as needed
}
