package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Calculator {
    private BigDecimal number1;
    private BigDecimal number2;
    private String operation;
    private BigDecimal result;

    public BigDecimal getResult(){
        if (operation == null) {
            return null;
        }

        switch (operation) {
            case "+" -> {
                result = number1.add(number2);
            }
            case "-" -> {
                result = number1.subtract(number2);
            }
            default -> {
            }
        }
        return result;
    }
}
