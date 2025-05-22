package com.example.hospitalreservation.fee;
import org.springframework.stereotype.Component;

@Component("피로 회복 주사")
public class FatigueInjectionFeeCalculator implements FeeCalculator {
    public int calculateFee() {
        return 25000;
    }
}