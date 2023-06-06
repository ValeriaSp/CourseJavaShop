package ru.mirea.courseworkjavashop.controller;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mirea.courseworkjavashop.service.AuthService;
import ru.mirea.courseworkjavashop.service.ProductService;
import org.springframework.ui.Model;
    import ru.mirea.courseworkjavashop.service.UserService;

@Controller
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;
    private final AuthService authService;

    /**
     * Получение конкретного продукта
     * @return
     */
    @GetMapping("/{id}")
    public String getProduct(
            @PathVariable("id") int id,
            Model model
    ) {
        model.addAttribute("userInfo", authService.getUserInfo());

        // Проверка на существование продукта
        var product = productService.getById(id);
        if (product == null) {
            return "redirect:/error";
        }
        model.addAttribute("product", product);
        model.addAttribute("isBought", userService.isBought(product));
        return "product";
    }
}
