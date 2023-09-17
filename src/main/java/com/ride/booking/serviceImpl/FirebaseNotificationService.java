package com.ride.booking.serviceImpl;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.ride.booking.model.Driver;
import com.ride.booking.model.RideRequest;
import com.ride.booking.model.User;
import com.ride.booking.service.NotificationService;

@Service
public class FirebaseNotificationService implements NotificationService {

	 @Override
	    public void sendRideRequestNotificationToDriver(Driver driver, RideRequest rideRequest) {
	        // Create a Message to send to the driver
	        Message message = Message.builder()
	                .setNotification(Notification.builder()
	                        .setTitle("New Ride Request")
	                        .setBody("You have a new ride request.")
	                        .build())
	                .putData("ride_request_id", String.valueOf(rideRequest.getId()))
	                .putData("user_id", String.valueOf(rideRequest.getUser().getId()))
	                .putData("start_location", rideRequest.getStartLocation().getAddress())
	                .putData("end_location", rideRequest.getEndLocation().getAddress())
	                .setToken(driver.getFcmToken()) // Driver's FCM token
	                .build();

	        // Send the message
	        try {
	            String response = FirebaseMessaging.getInstance().send(message);
	            System.out.println("Successfully sent message to driver: " + response);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    @Override
	    public void sendRideAssignedNotificationToUser(User user, RideRequest rideRequest) {
	        // Create a Message to send to the user
	        Message message = Message.builder()
	                .setNotification(Notification.builder()
	                        .setTitle("Ride Assigned")
	                        .setBody("Your ride has been assigned to a driver.")
	                        .build())
	                .putData("ride_request_id", String.valueOf(rideRequest.getId()))
	                .putData("driver_id", String.valueOf(rideRequest.getAssignedDriver().getId()))
	                .putData("start_location", rideRequest.getStartLocation().getAddress())
	                .putData("end_location", rideRequest.getEndLocation().getAddress())
	                .setToken(user.getFcmToken()) // User's FCM token
	                .build();

	        // Send the message
	        try {
	            String response = FirebaseMessaging.getInstance().send(message);
	            System.out.println("Successfully sent message to user: " + response);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    @Override
	    public void sendRideAssignedNotificationToDriver(User driver, String riderName) {
	        // Create a Message to send to the driver
	        Message message = Message.builder()
	                .setNotification(Notification.builder()
	                        .setTitle("New Ride Assigned")
	                        .setBody("You have been assigned a new ride.")
	                        .build())
	                .putData("rider_name", riderName)
	                .setToken(driver.getFcmToken()) // Driver's FCM token
	                .build();

	        // Send the message
	        try {
	            String response = FirebaseMessaging.getInstance().send(message);
	            System.out.println("Successfully sent message to driver: " + response);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

   
}
