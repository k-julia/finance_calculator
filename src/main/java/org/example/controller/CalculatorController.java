package org.example.controller;

import org.example.dto.Calculator;
import org.example.dto.Error;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;

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

        String number1 = calculator.getNumber1();
        String number2 = calculator.getNumber2();
        BigDecimal first;
        BigDecimal second;

        try {
            first = new BigDecimal(number1.replace(" ", ""));
            second = new BigDecimal(number2.replace(" ", ""));

            if (isExponentialNotation(first) || isExponentialNotation(second) ||
                !isValidStringFormat(number1) ||
                    !isValidStringFormat(number2)) {
                throw new Exception();
            }
        } catch (Exception e) {
            model.addAttribute("error", new Error("Неверный формат чисел"));
            return "error";
        }

        if (("/".equals(calculator.getOperation()) && BigDecimal.ZERO.equals(second))) {
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
