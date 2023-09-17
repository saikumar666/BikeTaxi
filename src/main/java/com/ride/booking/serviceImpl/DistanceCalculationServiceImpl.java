package com.ride.booking.serviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.ride.booking.service.DistanceCalculationService;

@Service
public class DistanceCalculationServiceImpl implements DistanceCalculationService {

	@Autowired
	private final GeoApiContext geoApiContext;

    
    public DistanceCalculationServiceImpl(@Value("${google.maps.api.key}") String apiKey) {
        this.geoApiContext = new GeoApiContext.Builder().apiKey(apiKey).build();
    }

    @Override
    public double calculateDistance(double startLatitude, double startLongitude, double endLatitude, double endLongitude) {
        try {
            String originStr = startLatitude + "," + startLongitude;
            String destinationStr = endLatitude + "," + endLongitude;

            DistanceMatrix result = DistanceMatrixApi.newRequest(geoApiContext)
                    .origins(originStr)
                    .destinations(destinationStr)
                    .await();

            if (result.rows.length > 0 && result.rows[0].elements.length > 0) {
                DistanceMatrixElement element = result.rows[0].elements[0];
                return element.distance.inMeters / 1000.0; // Convert meters to kilometers
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1.0; // Distance calculation failed
    }

}
