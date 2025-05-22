package com.example.hospitalreservation.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private LocalDateTime reservationStartTime;
    private LocalDateTime reservationEndTime;
    private int fee;

    public Reservation() {}

    public Long getId() {
        return id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public LocalDateTime getReservationStartTime() {
        return reservationStartTime;
    }

    public LocalDateTime getReservationEndTime() {
        return reservationEndTime;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }
    public static Reservation of(Doctor doctor, Patient patient, LocalDateTime startTime, LocalDateTime endTime) {
        Reservation r = new Reservation();
        r.doctor = doctor;
        r.patient = patient;
        r.reservationStartTime = startTime;
        r.reservationEndTime = endTime;
        return r;
    }
}