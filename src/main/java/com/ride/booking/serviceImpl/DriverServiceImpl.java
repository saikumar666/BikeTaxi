package com.ride.booking.serviceImpl;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.ride.booking.model.Driver;
import com.ride.booking.model.Location;
import com.ride.booking.model.RequestStatus;
import com.ride.booking.model.RideRequest;
import com.ride.booking.model.User;
import com.ride.booking.repository.DriverRepository;
import com.ride.booking.service.DriverService;

@Service
public class DriverServiceImpl implements DriverService {

	 private final DriverRepository driverRepository;
	    private final DatabaseReference driverLocationRef;
	    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
	    
	    @Value("${google.api.key}") // Inject your Google Maps API key from configuration
	    private String apiKey;
	    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	    // Define a queue to manage pending ride requests
	    private final Queue<RideRequest> pendingRideRequests = new LinkedList<>();

	    
	    @Override
	    public List<Driver> findNearbyDrivers(Location riderLocation) {
	        List<Driver> nearbyDrivers = new ArrayList<>();

	        // Define the Google Places API URL
	        String apiUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
	                "?location=" + riderLocation.getLatitude() + "," + riderLocation.getLongitude() +
	                "&radius=3000" + // 3 km radius
	                "&type=driver" + // Replace with the actual type you use for drivers
	                "&key=" + apiKey;

	        // Make a request to the Google Places API
	        RestTemplate restTemplate = new RestTemplate();
	        Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);

	        // Parse the response and extract nearby drivers
	        if (response != null && response.containsKey("results")) {
	            List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");
	            for (Map<String, Object> result : results) {
	                // Extract driver information and create Driver objects
	                String driverName = (String) result.get("name");
	                String vehicleNumber = (String) result.get("vehicle_number"); // Replace with the actual field name for vehicle number
	                // Extract driver's location
	                Map<String, Double> locationMap = (Map<String, Double>) result.get("geometry");
	                Double driverLatitude = locationMap.get("latitude");
	                Double driverLongitude = locationMap.get("longitude");
	                Location driverLocation = new Location(driverLongitude, driverLongitude);

	                // Create a Driver object with the extracted information
	                Driver driver = new Driver();
	                driver.setName(driverName);
	                driver.setVehicleNumber(vehicleNumber);
	                driver.setLocation(driverLocation);

	                nearbyDrivers.add(driver);
	            }
	        }

	        return nearbyDrivers;
	    }


	    @Autowired
	    public DriverServiceImpl(DriverRepository driverRepository) {
	        this.driverRepository = driverRepository;

	        // Initialize the database reference
	        FirebaseDatabase database = FirebaseDatabase.getInstance();
	        driverLocationRef = database.getReference("driver_locations");
	    }

	    @Override
	    public Driver createDriver(String name, String email, String phoneNumber, Location location) {
	        // Create and save a new driver
	        Driver driver = new Driver();
	        driver.setName(name);
	        driver.setEmail(email);
	        driver.setPhoneNumber(phoneNumber);
	        driver.setLocation(location);
	        // You can set other driver properties as needed
	        return driverRepository.save(driver);
	    }

	    @Override
	    public Driver getDriverById(Long id) {
	        return driverRepository.findById(id).orElse(null);
	    }

	    @Override
	    public boolean acceptRideRequest(Driver driver, RideRequest rideRequest) {
	        // Check if the driver is available and the ride request is pending
	        if (driver.getAvailability() && rideRequest.getRequestStatus() == RequestStatus.PENDING) {
	            try {
	                // Create a JSON object for the driver's location
	                double driverLatitude = driver.getLocation().getLatitude();
	                double driverLongitude = driver.getLocation().getLongitude();

	                Map<String, Object> locationUpdate = new HashMap<>();
	                locationUpdate.put("latitude", driverLatitude);
	                locationUpdate.put("longitude", driverLongitude);

	                // Update the driver's location in Firebase Realtime Database
	                driverLocationRef.child(driver.getId().toString()).updateChildren(locationUpdate, new DatabaseReference.CompletionListener() {
	                    @Override
	                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
	                        if (databaseError != null) {
	                            // Handle the error, e.g., log it
	                            databaseError.toException().printStackTrace();
	                        }
	                    }
	                });

	                // Perform logic to assign the driver to the ride request
	                rideRequest.setAssignedDriver(driver);
	                rideRequest.setRequestStatus(RequestStatus.ASSIGNED);

	                // Notify the user and perform other necessary actions
	                notifyUser(rideRequest.getUser(), "Your ride request has been accepted by a driver.");

	                return true; // Ride request accepted successfully
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        } else {
	            // Handle the case where the ride request cannot be accepted
	            return false; // Ride request not accepted
	        }
	        return false;
	    }

	    // ... (other methods)

	    // Automatically cancel a ride request if not accepted within 20 seconds
	    public void autoCancelRideRequest(RideRequest rideRequest) {
	        if (rideRequest.getRequestStatus() == RequestStatus.PENDING) {
	            // Schedule the cancellation with a 20-second delay
	            executorService.schedule(() -> {
	                if (rideRequest.getRequestStatus() == RequestStatus.PENDING) {
	                    // Perform automatic cancellation
	                    rideRequest.setRequestStatus(RequestStatus.CANCELLED);
	                    notifyUser(rideRequest.getUser(), "Your ride request has been automatically canceled.");
	                }
	            }, 20, TimeUnit.SECONDS);
	        }
	    }

	    // ... (other methods)

	    // Notify the user with a message (You can implement the actual notification logic here)
	    private void notifyUser(User user, String message) {
	        // Implement notification logic using Firebase Cloud Messaging
	        try {
	            // Create an FCM message
	            Message fcmMessage = Message.builder()
	                    .setNotification(Notification.builder()
	                            .setTitle("Notification Title")
	                            .setBody(message)
	                            .build())
	                    .setToken(user.getFcmToken()) // Use the user's FCM token
	                    .build();

	            // Send the FCM message
	            String response = FirebaseMessaging.getInstance().send(fcmMessage);
	            System.out.println("Successfully sent FCM message: " + response);
	        } catch (FirebaseMessagingException e) {
	            System.err.println("Error sending FCM message: " + e.getMessage());
	        }
	    }

	    
	    @Override
	    public boolean cancelRideRequest(Driver driver, RideRequest rideRequest) {
	        // Check if the driver is assigned to the ride request and the ride request is pending
	        if (rideRequest.getAssignedDriver() == driver && rideRequest.getRequestStatus() == RequestStatus.PENDING) {
	            // Perform logic to cancel the ride request
	            rideRequest.setAssignedDriver(null); // Unassign the driver
	            rideRequest.setRequestStatus(RequestStatus.CANCELLED);
	            
	            // Notify the user and perform other necessary actions
	            notifyUser(rideRequest.getUser(), "Your ride request has been canceled by the driver.");
	            
	            return true; // Ride request canceled successfully
	        } else {
	            // Handle the case where the ride request cannot be canceled
	            // You can provide a reason for the cancellation failure
	            String reason = "Ride request cannot be canceled because it is not pending or the driver is not assigned.";
	            // Log the reason or handle it as needed
	            System.out.println(reason);
	            
	            return false; // Ride request not canceled
	        }
   
	    }}
    
    
    
    
   


