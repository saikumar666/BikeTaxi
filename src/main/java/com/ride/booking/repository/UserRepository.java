package com.ride.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.ride.booking.model.User;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Long> {
    // You can add custom queries here if needed
}

