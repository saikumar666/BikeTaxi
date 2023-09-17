package com.ride.booking.serviceImpl;

import com.ride.booking.model.Location;
import com.ride.booking.repository.LocationRepository;
import com.ride.booking.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService {

	// Define your service areas as latitude and longitude boundaries
		private static final double[][] serviceAreas = 
			{ { 16.95, 17.03, 82.22, 82.27 }, // Service Area for Kakinada
				{ 16.98, 17.03, 81.75, 81.82 }, // Service Area for Rajahmundry
				{ 16.92, 16.96, 82.22, 82.26 } ,// Service Area for Anaparthi
				{17.65, 17.70, 82.59, 82.63}//service Area for Narsipatnam
				// Add more service areas as needed
		};

		@Autowired
		private LocationRepository locationRepository;

		@Override
		public Location createLocation(double latitude, double longitude) {
			Location location = new Location(longitude, longitude);
			location.setLatitude(latitude);
			location.setLongitude(longitude);
			return locationRepository.save(location);
		}

		@Override
		public Location getLocationById(Long id) {
			return locationRepository.findById(id).orElse(null);
		}

		@Override
		public boolean isLocationInServiceArea(Location location) {
		    // Check if the provided location (latitude, longitude) is within any service area
		    double latitude = location.getLatitude();
		    double longitude = location.getLongitude();

		    

		    for (double[] area : serviceAreas) {
		        double minLat = area[0];
		        double maxLat = area[1];
		        double minLng = area[2];
		        double maxLng = area[3];

		        if (latitude >= minLat && latitude <= maxLat && longitude >= minLng && longitude <= maxLng) {
		            // Your code logic for valid latitude and longitude range
		            return true; // Location is within a service area
		        }
		    }

		    return false; // Location is not within any service area
		}
		

	
}
