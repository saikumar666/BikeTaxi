package com.ride.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.ride.booking.model.RiderPayoutRequest;

@EnableJpaRepositories
public interface RiderPayoutRequestRepository extends JpaRepository<RiderPayoutRequest, Long> {
	// You can add custom queries here if needed
}
