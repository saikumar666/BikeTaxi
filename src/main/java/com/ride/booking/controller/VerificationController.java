package com.ride.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ride.booking.service.VerificationService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/verification")
public class VerificationController {
	private final VerificationService verificationService;

	@Autowired
	public VerificationController(VerificationService verificationService) {
		this.verificationService = verificationService;
	}

	@PostMapping("/{driverId}/approve")
	public ResponseEntity<String> approveVerification(@PathVariable Long driverId) {
		try {
			verificationService.approveVerification(driverId);
			return ResponseEntity.ok("Verification approved.");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/{driverId}/reject")
	public ResponseEntity<String> rejectVerification(@PathVariable Long driverId) {
		try {
			verificationService.rejectVerification(driverId);
			return ResponseEntity.ok("Verification rejected.");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
