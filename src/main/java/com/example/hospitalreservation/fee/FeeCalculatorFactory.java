package com.example.hospitalreservation.fee;
import org.springframework.stereotype.Component;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class FeeCalculatorFactory {

    private final Map<String, FeeCalculator> calculatorMap;

    @Autowired
    public FeeCalculatorFactory(Map<String, FeeCalculator> calculatorMap) {
        this.calculatorMap = calculatorMap;
    }

    public FeeCalculator getCalculator(String reason) {
        return calculatorMap.getOrDefault(reason, () -> 0); // 기본값 0원
    }
}