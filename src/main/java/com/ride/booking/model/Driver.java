package com.ride.booking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Driver {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String phoneNumber;

	private double balance;

	private String vehicleNumber;
	
	
	public void setLocation(Location location) {
        // Implementation for setting the driver's location
    }

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	private VerificationStatus verificationStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public VerificationStatus getVerificationStatus() {
		return verificationStatus;
	}

	public void setVerificationStatus(VerificationStatus verificationStatus) {
		this.verificationStatus = verificationStatus;
	}

	public boolean getAvailability() {
		// TODO Auto-generated method stub
		return getAvailability();
	}

	public String getFcmToken() {
		// TODO Auto-generated method stub
		return getFcmToken();
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public boolean isAvailable() {
		// Implement logic to check driver's availability, e.g., based on their current
		// status
		return true; // Replace with actual logic
	}

	public Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	// Other getters and setters
}