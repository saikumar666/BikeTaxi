package com.ride.booking.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Reimbursement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "driver_id", nullable = false)
	private Driver driver;

	@Column(nullable = false)
	private double amount;

	@Column(nullable = false)
	private LocalDate requestDate;

	@Column(nullable = false)
	private boolean approved;

	// Constructors, getters, setters

}
