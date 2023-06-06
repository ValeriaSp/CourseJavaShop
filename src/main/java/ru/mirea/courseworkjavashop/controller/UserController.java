package ru.mirea.courseworkjavashop.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mirea.courseworkjavashop.domain.dto.BalanceTopupDTO;
import ru.mirea.courseworkjavashop.domain.dto.UserEditDTO;
import ru.mirea.courseworkjavashop.domain.model.BalanceTopUpRequest;
import ru.mirea.courseworkjavashop.service.AuthService;
import ru.mirea.courseworkjavashop.service.ProductService;
import ru.mirea.courseworkjavashop.service.UserService;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final AuthService authService;
    private final UserService userService;
    private final ProductService productService;

    /**
     * Страница профиля пользователя
     *
     * @return
     */
    @GetMapping("/profile")
    public String profile(Model model) {

        // Добавляем информацию о пользователе в модель
        model.addAttribute("userInfo", authService.getUserInfo());

        // Если пользователь авторизован, то добавляем его в модель
        var user = authService.getAuthUser().orElse(null);

        var cart = user.getProducts();
        model.addAttribute("cart", cart);
        model.addAttribute("cartTotal", ProductService.getCartTotal(cart));

        model.addAttribute("boughtProducts", productService.getBoughtProductsByUser(user));

        return "profile";
    }

    /**
     * Изменение данных пользователя
     *
     * @return
     */
    @GetMapping("/edit")
    public String edit(
            @ModelAttribute("userDTO") UserEditDTO userEditDTO,
            Model model
    ) {

        // Добавляем информацию о пользователе в модель
        model.addAttribute("userInfo", authService.getUserInfo());

        userEditDTO = userService.getUserEditDTO();
        model.addAttribute("userDTO", userEditDTO);

        return "edit-profile";
    }

    /**
     * Изменение данных пользователя
     *
     * @return
     */
    @PostMapping("/edit")
    public String editPost(
            @Valid @ModelAttribute("userDTO") UserEditDTO userEditDTO,
            BindingResult result,
            Model model
    ) {
        model.addAttribute("userInfo", authService.getUserInfo());

        if (result.hasErrors()) {
            return "edit-profile";
        }

        if (userService.update(userEditDTO)) {
            return "redirect:/user/profile";
        } else {
            result.rejectValue("email", null, "Пользователь с таким email уже существует");
            return "edit-profile";
        }
    }

    /**
     * Добавление в корзину
     *
     * @return
     */
    @PostMapping("/add-to-cart/{id}")
    public String addToCart(Model model, @PathVariable int id) {
        model.addAttribute("userInfo", authService.getUserInfo());

        if (userService.addToCartByProductId(id)) {
            return "redirect:/products/" + id + "?success";
        } else {
            return "redirect:/products/" + id + "?error";
        }
    }

    /**
     * Удаление из корзины
     *
     * @return
     */
    @PostMapping("/remove-from-cart/{id}")
    public String removeFromCart(Model model, @PathVariable int id) {
        model.addAttribute("userInfo", authService.getUserInfo());

        if (userService.removeFromCartByProductId(id)) {
            return "redirect:/user/profile?success";
        } else {
            return "redirect:/user/profile?error";
        }
    }

    /**
     * Оформление заказа
     *
     * @return
     */
    @PostMapping("/checkout")
    public String checkout() {
        if (userService.checkout()) {
            return "redirect:/user/profile?success";
        } else {
            return "redirect:/user/profile?error";
        }
    }

    /**
     * Пополнение баланса
     *
     * @return
     */
    @GetMapping("/balance-top-up")
    public String balanceTopUp(
            @ModelAttribute("balanceTopUpDTO") BalanceTopupDTO balanceTopUp,
            Model model) {
        model.addAttribute("userInfo", authService.getUserInfo());
        model.addAttribute("success", false);
        balanceTopUp.setAmount(100);
        return "balance-top-up";
    }

    /**
     * Пополнение баланса
     *
     * @return
     */
    @PostMapping("/balance-top-up")
    public String balanceTopUpPost(
            @Valid @ModelAttribute("balanceTopUpDTO") BalanceTopupDTO balanceTopUp,
            BindingResult result,
            Model model) {
        model.addAttribute("userInfo", authService.getUserInfo());
        model.addAttribute("success", false);

        if (result.hasErrors()) {
            return "balance-top-up";
        }

        var req = userService.balanceTopUp(balanceTopUp);

        if (req != null) {
            model.addAttribute("request", req);
            model.addAttribute("success", true);
        } else {
            result.rejectValue("amount", null, "Ошибка пополнения баланса");
        }
        return "balance-top-up";
    }
}
