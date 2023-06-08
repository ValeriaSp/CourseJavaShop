package ru.mirea.courseworkjavashop.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mirea.courseworkjavashop.domain.dto.AdminEditUserDTO;
import ru.mirea.courseworkjavashop.domain.model.Product;
import ru.mirea.courseworkjavashop.domain.model.User;
import ru.mirea.courseworkjavashop.service.AdminService;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    /**
     * Страница запросов на пополнение баланса
     *
     * @param model
     * @return
     */
    @GetMapping("/balance-requests")
    public String balanceRequests(
            Model model
    ) {
        model.addAttribute("requests", adminService.getAllBalanceTopUpRequests());
        return "admin/balance-requests";
    }

    /**
     * Подтверждение запроса на пополнение баланса
     *
     * @param id
     */
    @PostMapping("/balance-requests/accept/{id}")
    public String confirmBalanceRequest(
            @PathVariable("id") int id
    ) {
        adminService.confirmBalanceRequest(id);
        return "redirect:/admin/balance-requests?success";
    }

    /**
     * Отклонение запроса на пополнение баланса
     *
     * @param id
     */
    @PostMapping("/balance-requests/reject/{id}")
    public String rejectBalanceRequest(
            @PathVariable("id") int id
    ) {
        adminService.rejectBalanceRequest(id);
        return "redirect:/admin/balance-requests?success";
    }

    /**
     * Удаление запроса на пополнение баланса
     *
     * @param id
     */
    @PostMapping("/balance-requests/delete/{id}")
    public String deleteBalanceRequest(
            @PathVariable("id") int id
    ) {
        adminService.deleteBalanceRequest(id);
        return "redirect:/admin/balance-requests?success";
    }

    /**
     * Страница пользователей
     *
     * @param model
     * @return
     */
    @GetMapping("/users")
    public String users(
            Model model
    ) {
        model.addAttribute("users", adminService.getAllUsers());
        return "admin/users";
    }

    /**
     * Редактирование пользователя
     *
     * @return
     */
    @GetMapping("/users/edit/{id}")
    public String editUser(
            @PathVariable("id") int id,
            Model model
    ) {
        model.addAttribute("userDTO", adminService.getUserEditDTO(id));
        return "admin/edit-user";
    }

    /**
     * Редактирование пользователя
     *
     * @return
     */
    @PostMapping("/users/edit")
    public String editPost(
            @Valid @ModelAttribute("userDTO") AdminEditUserDTO userDTO
    ) {
        adminService.editUser(userDTO);
        return "redirect:/admin/users?success";
    }

    /**
     * Страница со списом заказов
     *
     * @param model
     */
    @GetMapping("/orders")
    public String orders(
            Model model
    ) {
        model.addAttribute("orders", adminService.getAllOrders());
        return "admin/orders";
    }


    /**
     * Страница со списом товаров
     *
     * @param model
     */
    @GetMapping("/products")
    public String products(
            Model model
    ) {
        model.addAttribute("products", adminService.getAllProducts());
        return "admin/products";
    }

    /**
     * Редактирование товара
     *
     * @param id
     */
    @GetMapping("/products/edit/{id}")
    public String editProduct(
            @PathVariable("id") int id,
            Model model
    ) {
        model.addAttribute("product", adminService.getProductById(id));
        return "admin/edit-product";
    }

    /**
     * Редактирование товара
     */
    @PostMapping("/products/edit")
    public String editProductPost(
            @ModelAttribute("product") Product product
    ) {
        adminService.saveProduct(product);
        return "redirect:/admin/products?success";
    }

    /**
     * Добавление товара
     *
     * @return
     */
    @GetMapping("/products/add")
    public String addProduct(
            Model model
    ) {
        model.addAttribute("product", new Product());
        return "admin/add-product";
    }

    /**
     * Добавление товара
     *
     * @return
     */
    @PostMapping("/products/add")
    public String addProductPost(
            @ModelAttribute("product") Product product
    ) {
        adminService.saveProduct(product);
        return "redirect:/admin/products?success";
    }

}

