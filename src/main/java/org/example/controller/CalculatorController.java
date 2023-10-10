package org.example.controller;

import org.example.dto.Calculator;
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
        return "calculator";
    }

    @PostMapping("/")
    public String calculate(Model model, @ModelAttribute Calculator calculator) {
        model.addAttribute("calculator", calculator);

        if (isExponentialNotation(calculator.getNumber1()) ||
                isExponentialNotation(calculator.getNumber2())) {
            return "error";
        }

        return "calculator";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    private boolean isExponentialNotation(BigDecimal number) {
        return number.toString().toLowerCase().contains("e");
    }
}
