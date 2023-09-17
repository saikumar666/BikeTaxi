package com.ride.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.ride.booking.model.Driver;

@EnableJpaRepositories
public interface DriverRepository extends JpaRepository<Driver, Long> {
    // You can add custom queries here if needed
}

