package com.ride.booking.service;

public interface DistanceCalculationService {
	double calculateDistance(double startLatitude, double startLongitude, double endLatitude, double endLongitude);
}
