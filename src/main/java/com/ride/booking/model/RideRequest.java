package com.ride.booking.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class RideRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String routeDirections;

	@Column(nullable = false)
	private LocalDateTime requestTime;
	
	private double distance;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Enumerated(EnumType.STRING)
	private RequestStatus requestStatus = RequestStatus.PENDING;

	@ManyToOne
	@JoinColumn(name = "driver_id")
	private Driver assignedDriver;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Driver getAssignedDriver() {
		return assignedDriver;
	}

	public void setAssignedDriver(Driver assignedDriver) {
		this.assignedDriver = assignedDriver;
	}
	// Constructors, getters, setters, and other fields as needed

	public RequestStatus getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(RequestStatus requestStatus) {
		this.requestStatus = requestStatus;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRouteDirections() {
		return routeDirections;
	}

	public void setRouteDirections(String routeDirections) {
		this.routeDirections = routeDirections;
	}
	
	 

	    // Constructor and other methods

	    public void setStartLocation(Location startLocation) {
	    }
	    public void setEndLocation(Location endLocation) {
	    }
	    
	    public void setDistance(double distance) {
	        this.distance = distance;
	    }

		public Location getStartLocation() {
			// TODO Auto-generated method stub
			return null;
		}

		public Location getEndLocation() {
			// TODO Auto-generated method stub
			return getEndLocation();
		}

		

}
