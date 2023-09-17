package com.ride.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.ride.booking.model.Payment;

@EnableJpaRepositories

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	// You can add custom queries here if needed
}
