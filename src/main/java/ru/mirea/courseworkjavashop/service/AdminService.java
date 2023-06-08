package ru.mirea.courseworkjavashop.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.courseworkjavashop.domain.dto.AdminEditUserDTO;
import ru.mirea.courseworkjavashop.domain.model.*;
import ru.mirea.courseworkjavashop.mapper.UserMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {
    private final UserService userService;
    private final ProductService productService;
    private final BalanceTopUpRequestService balanceTopUpRequestService;
    private final UserMapper userMapper;
    private final OrderService orderService;

    /**
     * Получение всех заявок на пополнение баланса
     *
     * @return
     */
    public List<BalanceTopUpRequest> getAllBalanceTopUpRequests() {
        return balanceTopUpRequestService.getAllBalanceTopUpRequests();
    }


    /**
     * Получение всех пользователей
     */
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Получение пользователя по id
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public User getUserById(int id) {
        return userService.getById(id);
    }

    /**
     * Получение пользователя для редактирования
     *
     * @param id
     * @return
     */


    public AdminEditUserDTO getUserEditDTO(int id) {
        return userMapper.userToAdminEditUserDTO(getUserById(id));
    }

    /**
     * Редактирование пользователя
     *
     * @param userDTO
     */
    @Transactional
    public void editUser(AdminEditUserDTO userDTO) {
        User user = getUserById(userDTO.getId());
        user.setBalance(userDTO.getBalance());
        user.setRole(userDTO.getRole());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        if (userDTO.isChangePassword()) {
            user.setPassword(userService.encodePassword(userDTO.getPassword()));
        }
        userService.save(user);
    }

    /**
     * Удаление пользователя
     *
     * @param id
     */
    @Transactional
    public void deleteUser(int id) {
        userService.deleteById(id);
    }

    /**
     * Получение всех заказов
     *
     * @return
     */
    public List<Order> getAllOrders() {
        return orderService.getAll();
    }

    /**
     * Подтверждение запроса на пополнение баланса
     *
     * @param id
     */
    @Transactional
    public void confirmBalanceRequest(int id) {
        BalanceTopUpRequest request = balanceTopUpRequestService.getById(id);
        User user = request.getUser();
        user.setBalance(user.getBalance() + request.getAmount());
        request.setStatus(Status.ACCEPTED);
        balanceTopUpRequestService.save(request);
        userService.save(user);
    }

    /**
     * Отклонение запроса на пополнение баланса
     *
     * @param id
     */
    @Transactional
    public void rejectBalanceRequest(int id) {
        BalanceTopUpRequest request = balanceTopUpRequestService.getById(id);
        request.setStatus(Status.REJECTED);
        balanceTopUpRequestService.save(request);
    }

    public void deleteBalanceRequest(int id) {
        balanceTopUpRequestService.deleteById(id);
    }

    /**
     * Получение всех товаров
     *
     * @return
     */
    public List<Product> getAllProducts() {
        return productService.getAll();
    }


    /**
     * Получение товара по id
     *
     * @param id
     * @return
     */
    public Product getProductById(int id) {
        return productService.getById(id);
    }


    /**
     * Сохранение товара
     *
     * @param product
     */
    @Transactional
    public void saveProduct(Product product) {
        productService.save(product);
    }
}
