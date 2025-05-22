package com.example.hospitalreservation.controller;

import com.example.hospitalreservation.model.Reservation;
import com.example.hospitalreservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public String getReservations(Model model) {
        List<Reservation> reservations = reservationService.getAllReservations();
        model.addAttribute("reservations", reservations);
        return "index.html";
    }

    @GetMapping("/new")
    public String showReservationForm() {
        return "reservation_form";
    }

    @PostMapping
    public String createReservation(@RequestParam Long doctorId,
                                    @RequestParam Long patientId,
                                    @RequestParam String reservationTime,
                                    @RequestParam String reason,
                                    Model model) {
        try {
            LocalTime time = LocalTime.parse(reservationTime);
            reservationService.createReservation(doctorId, patientId, reason, time);
            return "redirect:/reservations";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "reservation_form";
        }
    }

    @PostMapping("/delete/{id}")
    public String cancelReservationFromForm(@PathVariable Long id,
                                            @RequestParam String cancelReason,
                                            Model model) {
        try {
            reservationService.cancelReservation(id, cancelReason);
        } catch (NoSuchElementException e) {
            model.addAttribute("errorMessage", "존재하지 않는 예약입니다.");
        }
        return "redirect:/reservations";
    }
}