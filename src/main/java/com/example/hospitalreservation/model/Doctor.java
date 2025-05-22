package com.example.hospitalreservation.model;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String specialization;

    private LocalTime availableStartTime = LocalTime.of(9, 0);
    private LocalTime availableEndTime = LocalTime.of(17, 0);

    public Doctor() {}

    public LocalTime getAvailableStartTime() {
        return availableStartTime;
    }

    public LocalTime getAvailableEndTime() {
        return availableEndTime;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }
}