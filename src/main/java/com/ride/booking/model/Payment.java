package com.ride.booking.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long payment_id;
	private double totalAmount;
	private double platformFee;
	private double riderAmount;
	private double amount;
	private LocalDateTime paymentTime;
	private LocalDate paymentDate;

	@ManyToOne
	@JoinColumn(name = "driver_id")
	private Driver driver;


}
