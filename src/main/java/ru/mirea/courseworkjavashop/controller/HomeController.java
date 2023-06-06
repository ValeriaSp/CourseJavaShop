package ru.mirea.courseworkjavashop.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.mirea.courseworkjavashop.service.AuthService;
import ru.mirea.courseworkjavashop.service.ProductService;

@Controller
@AllArgsConstructor
public class HomeController {
    private final ProductService productService;
    private final AuthService authService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("userInfo", authService.getUserInfo());
        model.addAttribute("products", productService.getAllProducts());
        return "home";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

}
