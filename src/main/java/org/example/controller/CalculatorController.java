package org.example.controller;

import org.example.dto.Calculator;
import org.example.dto.Error;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Controller
public class CalculatorController {
    @GetMapping("/")
    public String showCalculatorForm(Model model) {
        model.addAttribute("calculator", new Calculator());
        model.addAttribute("error", new Error());
        return "calculator";
    }

    @PostMapping("/")
    public String calculate(Model model, @ModelAttribute Calculator calculator, @ModelAttribute Error error) {
        model.addAttribute("calculator", calculator);

        switch (calculator.getRoundingStr()) {
            case "математическое" -> calculator.setResultRoundingMode(RoundingMode.HALF_UP);
            case "бухгалтерское" -> {
                calculator.setResultRoundingMode(RoundingMode.HALF_EVEN);
            }
            case "усечение" -> {
                calculator.setResultRoundingMode(RoundingMode.DOWN);
            }
            default -> {
            }
        }

        String number1 = calculator.getNumber1();
        String number2 = calculator.getNumber2();
        String number3 = calculator.getNumber3();
        String number4 = calculator.getNumber4();
        BigDecimal first;
        BigDecimal second;
        BigDecimal third;
        BigDecimal forth;

        try {
            first = new BigDecimal(number1.replace(" ", ""));
            second = new BigDecimal(number2.replace(" ", ""));
            third = new BigDecimal(number3.replace(" ", ""));
            forth = new BigDecimal(number4.replace(" ", ""));

            if (isExponentialNotation(first) || isExponentialNotation(second) ||
                    isExponentialNotation(third) || isExponentialNotation(forth) ||
                !isValidStringFormat(number1) || !isValidStringFormat(number2) ||
                    !isValidStringFormat(number3) || !isValidStringFormat(number4)) {
                throw new Exception();
            }
        } catch (Exception e) {
            model.addAttribute("error", new Error("Неверный формат чисел"));
            return "error";
        }

        if (("/".equals(calculator.getOperation2()) && BigDecimal.ZERO.equals(third)) ||
            ("/".equals(calculator.getOperation3()) && BigDecimal.ZERO.equals(forth)) ||
            ("/".equals(calculator.getOperation1()) &&
                calculator.getResultForTwoNumbers(number2, number3, calculator.getOperation2(),
                        10, RoundingMode.HALF_UP).equals(BigDecimal.ZERO))) {
            model.addAttribute("error", new Error("Деление на 0 запрещено"));
            return "error";
        }

        return "calculator";
    }

    @GetMapping("/error")
    public String error(Model model, @ModelAttribute Error error) {
        model.addAttribute("error", error);
        return "error";
    }

    private boolean isExponentialNotation(BigDecimal number) {
        return number.toString().toLowerCase().contains("e");
    }

    private boolean isValidStringFormat(String number) {
        if (!number.contains(" ")) {
            return true;
        }

        return number.matches("^[+-]?(\\d{1,3}( ?\\d{3})*|\\d+)(\\.\\d+)?$");
    }
}
