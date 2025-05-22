package com.example.hospitalreservation.fee;
import org.springframework.stereotype.Component;

@Component("감기 증상")
public class ColdSymptomsFeeCalculator implements FeeCalculator {
    public int calculateFee() {
        return 15000;
    }
}