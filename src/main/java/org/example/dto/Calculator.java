package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

@Getter
@Setter
public class Calculator {
    private String number1;
    private String number2;
    private String operation;
    private BigDecimal result;

    public String getResult(){
        if (operation == null) {
            return null;
        }

        BigDecimal first = new BigDecimal(number1.replace(" ", ""));
        BigDecimal second = new BigDecimal(number2.replace(" ", ""));

        switch (operation) {
            case "+" -> {
                result = first.add(second);
            }
            case "-" -> {
                result = first.subtract(second);
            }
            case "*" -> {
                result = first.multiply(second);
            }
            case "/" -> {
                result = first.divide(second, RoundingMode.HALF_UP);
            }
            default -> {
            }
        }

        if (result == null) {
            return "";
        }

        result = formatValue(result);
        DecimalFormat formatter = new DecimalFormat("#,###.######");
        return formatter.format(result).replace(",", ".");
    }

    private BigDecimal formatValue(BigDecimal bigDecimal) {
        return bigDecimal.setScale(6, RoundingMode.HALF_UP).stripTrailingZeros();
    }
}
