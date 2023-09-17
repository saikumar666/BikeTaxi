package com.ride.booking.service;

import com.ride.booking.model.User;

public interface UserService {
   // User createUser(String name, String email, String phoneNumber, Location location);
    User getUserById(Long id);
    // Add more methods as needed
}

