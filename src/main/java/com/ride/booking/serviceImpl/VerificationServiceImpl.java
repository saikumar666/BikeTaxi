package com.ride.booking.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ride.booking.model.Driver;
import com.ride.booking.model.VerificationStatus;
import com.ride.booking.repository.DriverRepository;
import com.ride.booking.service.VerificationService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class VerificationServiceImpl implements VerificationService {
    @Autowired
    private DriverRepository driverRepository;

    @Override
    public void approveVerification(Long driverId) {
        Driver driver = driverRepository.findById(driverId).orElseThrow(() -> new EntityNotFoundException("Driver not found"));
        driver.setVerificationStatus(VerificationStatus.APPROVED);
        driverRepository.save(driver);
    }

    @Override
    public void rejectVerification(Long driverId) {
        Driver driver = driverRepository.findById(driverId).orElseThrow(() -> new EntityNotFoundException("Driver not found"));
        driver.setVerificationStatus(VerificationStatus.REJECTED);
        driverRepository.save(driver);
    }
}
