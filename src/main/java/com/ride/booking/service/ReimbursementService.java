package com.ride.booking.service;

import com.ride.booking.model.Driver;
import com.ride.booking.model.Reimbursement;

public interface ReimbursementService {
	Reimbursement createReimbursement(Driver driver, double amount);

	Reimbursement getReimbursementById(Long id);
	// Add more methods as needed
}
