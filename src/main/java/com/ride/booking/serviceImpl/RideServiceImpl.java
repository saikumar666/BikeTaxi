package com.ride.booking.serviceImpl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.ride.booking.model.Location;
import com.ride.booking.model.Ride;
import com.ride.booking.model.User;
import com.ride.booking.repository.RideRepository;
import com.ride.booking.service.DistanceCalculationService;
import com.ride.booking.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;

@Service
public class RideServiceImpl implements RideService {

	private final DistanceCalculationService distanceService;
    private final RideRepository rideRepository;

    @Autowired
    public RideServiceImpl(DistanceCalculationService distanceService, RideRepository rideRepository) {
        this.distanceService = distanceService;
        this.rideRepository = rideRepository;

        // Initialize the Firebase Admin SDK (you can put this initialization in a separate method or configuration)
        try {
            FileInputStream serviceAccount = new FileInputStream("YOUR_JSON_KEY_FILE.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Ride createRide(User user, Location startLocation, Location endLocation, double amount) {
        Ride ride = new Ride();
        ride.setUser(user);
        ride.setStartLocation(startLocation);
        ride.setEndLocation(endLocation);
        ride.setAmount(amount);
        // Set other ride properties as needed
        return rideRepository.save(ride);
    }

    @Override
    public Ride getRideById(Long id) {
        return rideRepository.findById(id).orElse(null);
    }

    @Override
    public double requestRide(User user, Location startLocation, Location endLocation) {
        double distanceInKilometers = distanceService.calculateDistance(
                startLocation.getLatitude(),
                startLocation.getLongitude(),
                endLocation.getLatitude(),
                endLocation.getLongitude());

        if (distanceInKilometers < 0) {
            return -1.0; // Distance calculation failed
        }

        double totalCharge;

        if (distanceInKilometers <= 1.0) {
            totalCharge = 10.0; // Base fare of 10 rupees for distances up to 1 kilometer
        } else {
            totalCharge = 10.0 + ((distanceInKilometers - 1.0) * 10.0); // Charge 10 rupees per kilometer beyond the first kilometer
        }

        return totalCharge;
    }

    @Override
    public void sendRideRequestNotification(Ride ride) {
        try {
            // Construct the FCM message
            Message message = Message.builder()
                    .putData("ride_id", String.valueOf(ride.getId()))
                    .putData("user_id", String.valueOf(ride.getUser().getId()))
                    .putData("start_location", ride.getStartLocation().getAddress())
                    .putData("end_location", ride.getEndLocation().getAddress())
                    .setTopic("drivers") // Send to a topic that drivers are subscribed to
                    .build();

            // Send the FCM message
            String response = FirebaseMessaging.getInstance().send(message);

            System.out.println("Successfully sent message: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
