package com.example.hospitalreservation.service;

import com.example.hospitalreservation.model.Reservation;
import com.example.hospitalreservation.repository.ReservationRepository;
import com.example.hospitalreservation.repository.DoctorRepository;
import com.example.hospitalreservation.repository.PatientRepository;
import com.example.hospitalreservation.model.Doctor;
import com.example.hospitalreservation.model.Patient;
import org.springframework.stereotype.Service;
import com.example.hospitalreservation.fee.FeeCalculatorFactory;
import com.example.hospitalreservation.fee.FeeCalculator;

import java.time.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final FeeCalculatorFactory feeCalculatorFactory;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(Long doctorId, Long patientId, String reason, LocalTime reservationTime) {
        LocalDateTime fullDateTime = LocalDateTime.of(LocalDate.now(), reservationTime);

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("해당 의사가 존재하지 않습니다."));

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("해당 환자가 존재하지 않습니다."));

        if (reservationTime.isBefore(doctor.getAvailableStartTime()) ||
                reservationTime.isAfter(doctor.getAvailableEndTime().minusHours(1))) {
            throw new IllegalArgumentException("의사의 진료 가능 시간(" +
                    doctor.getAvailableStartTime() + "~" + doctor.getAvailableEndTime() +
                    ") 내에서만 예약할 수 있습니다.");
        }

        if (reservationTime.getMinute() != 0 || reservationTime.getSecond() != 0) {
            throw new IllegalArgumentException("예약은 정각(1시간 단위)으로만 가능합니다.");
        }

        if (reservationRepository.findByReservationStartTime(fullDateTime).isPresent()) {
            throw new IllegalArgumentException("해당 시간에는 이미 예약이 있습니다. 다른 시간을 선택해주세요.");
        }

        FeeCalculator calculator = feeCalculatorFactory.getCalculator(reason);
        int fee = calculator.calculateFee();

        Reservation reservation = Reservation.of(doctor, patient, fullDateTime, fullDateTime.plusHours(1));
        reservation.setFee(fee);
        return reservationRepository.save(reservation);
    }

    public void cancelReservation(Long id, String reason) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if(reservation.isEmpty()) {
            System.out.println("예약 ID: " + id + " 취소 사유: " + reason);
            throw new NoSuchElementException("존재하지 않는 예약입니다.");
        }
        reservationRepository.deleteById(id);
    }
    public ReservationService(
            ReservationRepository reservationRepository,
            DoctorRepository doctorRepository,
            PatientRepository patientRepository,
            FeeCalculatorFactory feeCalculatorFactory
    ) {
        this.reservationRepository = reservationRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.feeCalculatorFactory = feeCalculatorFactory;
    }
}