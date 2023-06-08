package ru.mirea.courseworkjavashop.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mirea.courseworkjavashop.domain.dto.UserRegisterDTO;
import ru.mirea.courseworkjavashop.service.UserService;

@Controller
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(@ModelAttribute("user") UserRegisterDTO userRegisterDTO) {
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(
            @Valid @ModelAttribute("user") UserRegisterDTO userRegisterDTO,
            BindingResult result
    ) {
        var existing = userService.findByEmail(userRegisterDTO.getEmail());
        if (existing.isPresent()) {
            result.rejectValue("email", null, "Пользователь с таким email уже существует");
        }

        if (result.hasErrors()) {
            return "register";
        }

        userService.register(userRegisterDTO);

        return "redirect:/login";
    }
}
