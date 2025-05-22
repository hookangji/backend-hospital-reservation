package com.example.hospitalreservation.fee;
import org.springframework.stereotype.Component;

@Component("일반 검진")
public class GeneralCheckupFeeCalculator implements FeeCalculator {
    public int calculateFee() {
        return 10000;
    }
}