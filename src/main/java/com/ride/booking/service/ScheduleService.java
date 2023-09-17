package com.ride.booking.service;

import java.time.LocalDateTime;

import com.ride.booking.model.Driver;
import com.ride.booking.model.Schedule;

public interface ScheduleService {
	Schedule createSchedule(Driver driver, LocalDateTime startTime, LocalDateTime endTime);

	Schedule getScheduleById(Long id);
	// Add more methods as needed
}
