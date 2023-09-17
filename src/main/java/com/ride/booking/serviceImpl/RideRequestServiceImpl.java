package com.ride.booking.serviceImpl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.ride.booking.exception.ApplicationException;
import com.ride.booking.model.Driver;
import com.ride.booking.model.Location;
import com.ride.booking.model.RequestStatus;
import com.ride.booking.model.RideRequest;
import com.ride.booking.model.User;
import com.ride.booking.repository.RideRequestRepository;
import com.ride.booking.service.DistanceCalculationService;
import com.ride.booking.service.DriverService;
import com.ride.booking.service.LocationService;
import com.ride.booking.service.RideRequestService;
import com.ride.booking.service.RouteDirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
public class RideRequestServiceImpl implements RideRequestService {

	 private final RideRequestRepository rideRequestRepository;
	    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

	    private final RouteDirectionService routeDirectionService;
	    private final DistanceCalculationService distanceService;
	    private final LocationService locationService;
	    
	    DriverService driverServiceImpl;
	   
	    
	    @Autowired
	    public RideRequestServiceImpl(RideRequestRepository rideRequestRepository,
	                                  RouteDirectionService routeDirectionService, DistanceCalculationService distanceService,
	                                  LocationService locationService,DriverService driverServiceImpl) {
	        this.rideRequestRepository = rideRequestRepository;
	        this.routeDirectionService = routeDirectionService;
	        this.distanceService = distanceService;
	        this.locationService = locationService;
	        this.driverServiceImpl=driverServiceImpl;
	    }

	    @Override
	    public RideRequest createRideRequest(User user, Location startLocation, Location endLocation) {
	        // Check if the pickup location is within a service area
	        boolean isInServiceArea = locationService.isLocationInServiceArea(startLocation);

	        if (!isInServiceArea) {
	            // Handle the case where the pickup location is not in the service area
	            throw new ApplicationException("Sorry, we are not available in your pickup location.");
	        }

	        RideRequest rideRequest = new RideRequest();
	        rideRequest.setUser(user);
	        rideRequest.setStartLocation(startLocation);
	        rideRequest.setEndLocation(endLocation);

	        // Fetch route directions and set them on the ride request
	        String routeDirections = routeDirectionService.getRouteDirections(startLocation, endLocation);
	        rideRequest.setRouteDirections(routeDirections);

	        // Calculate and set the distance
	        double distanceInKilometers = distanceService.calculateDistance(startLocation.getLatitude(),
	                startLocation.getLongitude(), endLocation.getLatitude(), endLocation.getLongitude());

	        if (distanceInKilometers >= 0) {
	            rideRequest.setDistance(distanceInKilometers); // Set distance in kilometers
	        } else {
	            // Handle distance calculation failure
	        }

	        // Set the request status to PENDING initially
	        rideRequest.setRequestStatus(RequestStatus.PENDING);

	        // Set other ride request properties as needed
	        return rideRequestRepository.save(rideRequest);
	    }

	    @Override
	    public RideRequest getRideRequestById(Long id) {
	        return rideRequestRepository.findById(id).orElse(null);
	    }

	    @Override
	    public void sendRideRequestNotification(RideRequest rideRequest) {
	        // Fetch a list of nearby drivers based on rideRequest.getStartLocation() or your logic
	        List<Driver> nearbyDrivers = findNearbyDrivers(rideRequest.getStartLocation());

	        for (Driver driver : nearbyDrivers) {
	            Message message = Message.builder()
	                    .setNotification(Notification.builder()
	                            .setTitle("New Ride Request")
	                            .setBody("A new ride request is available.")
	                            .build())
	                    .putData("requestId", String.valueOf(rideRequest.getId()))
	                    .putData("requestStatus", rideRequest.getRequestStatus().toString())
	                    .setToken(driver.getFcmToken()) // Use the driver's FCM token
	                    .build();

	            try {
	                // Send the message to this driver
	                String response = FirebaseMessaging.getInstance().send(message);
	                System.out.println("Successfully sent message: " + response);
	            } catch (FirebaseMessagingException e) {
	                System.err.println("Error sending FCM message: " + e.getMessage());
	            }
	        }
	    }

	  


	    @Override
	    public void assignDriverToRideRequest(RideRequest rideRequest, Driver driver) {
	        // Check if the ride request is still pending (not assigned to any driver)
	        if (rideRequest.getRequestStatus() == RequestStatus.PENDING) {
	            // Assign the driver to the ride request
	            rideRequest.setAssignedDriver(driver);
	            rideRequest.setRequestStatus(RequestStatus.ASSIGNED);
	            rideRequestRepository.save(rideRequest);

	            // You can also send a notification to the user or perform any other necessary
	            // actions here
	        } else {
	            // Handle the case where the ride request is no longer pending (already assigned
	            // or completed)
	        }
	    }

		@Override
		public List<Driver> findNearbyDrivers(Location location) {
			// TODO Auto-generated method stub
			return driverServiceImpl.findNearbyDrivers(location);
		}
   
}
