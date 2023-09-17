package com.ride.booking.model;

//import org.hibernate.validator.constraints.URL;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;

    //@URL(message = "Invalid URL format")
    private String profilePictureUrl;
    
    
    
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    // Constructors, getters, setters, etc.
}
