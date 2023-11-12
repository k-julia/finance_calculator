package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class Calculator {
    private final List<String> highPriorityOperations = Arrays.asList( "*", "/");

    private String number1 = "0";
    private String number2 = "0";
    private String number3 = "0";
    private String number4 = "0";
    private String operation1;
    private String operation2;
    private String operation3;
    private BigDecimal result;
    private String roundedResult;
    private String roundingStr;
    private RoundingMode resultRoundingMode;

    public String getResult(){
        if (operation1 == null || operation2 == null || operation3 == null) {
            return null;
        }

        String res1 = getResultForTwoNumbers(number2, number3, operation2, 10, RoundingMode.HALF_UP).toPlainString();
        if (highPriorityOperations.contains(operation1) || !highPriorityOperations.contains(operation3)) {
            BigDecimal res2 = getResultForTwoNumbers(number1, res1, operation1, 10, RoundingMode.HALF_UP);
            result = getResultForTwoNumbers(res2.toPlainString(), number4, operation3, 6, resultRoundingMode);
        } else {
            BigDecimal res2 = getResultForTwoNumbers(res1, number4, operation3, 10, RoundingMode.HALF_UP);
            result = getResultForTwoNumbers(number1, res2.toPlainString(), operation1, 6, resultRoundingMode);
        }

        if (result == null) {
            return "";
        }

        DecimalFormat formatter = new DecimalFormat("#,###.######");
        BigDecimal tmp = result;
        roundedResult =  formatter.format(formatValue(tmp, 0, resultRoundingMode));
        return formatter.format(result).replace(",", ".");
    }

    private BigDecimal formatValue(BigDecimal bigDecimal, int scale, RoundingMode roundingMode) {
        return bigDecimal.setScale(scale, roundingMode).stripTrailingZeros();
    }

    public BigDecimal getResultForTwoNumbers(String number1, String number2, String operation,
                                              int scale, RoundingMode roundingMode) {
        if (number1 == null || number2 == null) {
            return null;
        }

        BigDecimal first = new BigDecimal(number1.replace(" ", ""));
        BigDecimal second = new BigDecimal(number2.replace(" ", ""));

        BigDecimal res = null;
        switch (operation) {
            case "+" -> {
                res = first.add(second);
            }
            case "-" -> {
                res = first.subtract(second);
            }
            case "*" -> {
                res = first.multiply(second);
            }
            case "/" -> {
                return first.divide(second, scale, roundingMode);
            }
            default -> {
                return res;
            }
        }

        return formatValue(res, scale, roundingMode);
    }

}
