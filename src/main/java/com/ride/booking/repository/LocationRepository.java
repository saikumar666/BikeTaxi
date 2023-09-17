package com.ride.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.ride.booking.model.Location;

@EnableJpaRepositories
public interface LocationRepository extends JpaRepository<Location, Long> {

}
