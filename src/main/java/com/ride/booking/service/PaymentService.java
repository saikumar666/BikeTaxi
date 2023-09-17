package com.ride.booking.service;

import com.ride.booking.model.Driver;
import com.ride.booking.model.Payment;

public interface PaymentService {
    Payment createPayment(Driver driver, double amount);
    Payment getPaymentById(Long id);
    // Add more methods as needed
}

