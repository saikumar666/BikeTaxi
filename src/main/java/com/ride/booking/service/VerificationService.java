package com.ride.booking.service;

public interface VerificationService {
	void approveVerification(Long driverId);

	void rejectVerification(Long driverId);
}
