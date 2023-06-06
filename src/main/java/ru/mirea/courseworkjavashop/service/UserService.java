package ru.mirea.courseworkjavashop.service;

import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.courseworkjavashop.domain.dto.BalanceTopupDTO;
import ru.mirea.courseworkjavashop.domain.dto.UserEditDTO;
import ru.mirea.courseworkjavashop.domain.dto.UserRegisterDTO;
import ru.mirea.courseworkjavashop.domain.model.*;
import ru.mirea.courseworkjavashop.mapper.UserMapper;
import ru.mirea.courseworkjavashop.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthService authService;
    private final ProductService productService;
    private final BalanceTopUpRequestService balanceTopUpRequestService;
    private PasswordEncoder passwordEncoder;
    private OrderService orderService;

    /**
     * Хеширование пароля
     *
     * @param password
     * @return
     */
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }


    /**
     * Сохранение пользователя
     */
    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    /**
     * Поиск пользователя по email
     *
     * @param email
     * @return
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Поиск пользователя по id
     */
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }


    /**
     * Регистрация пользователя
     *
     * @param userRegisterDTO
     */
    @Transactional
    public void register(UserRegisterDTO userRegisterDTO) {
        save(userMapper.registerDTOToUser(userRegisterDTO));
    }


    /**
     * Добавление продукта в корзину
     *
     * @param product
     */
    @Transactional
    public boolean addToCart(Product product) {
        // Получаем авторизованного пользователя
        var user = authService.getAuthUser().orElse(null);

        // Если пользователь не авторизован, то ничего не делаем
        if (user == null) {
            return false;
        }

        // Добавляем товар в корзину
        var products = user.getProducts();

        // Если товар уже есть в корзине, то ничего не делаем
        if (products.contains(product)) {
            return false;
        }

        // Добавляем товар в корзину
        products.add(product);

        // Сохраняем пользователя
        save(user);
        return true;
    }

    /**
     * Удаление продукта из корзины
     *
     * @param product
     * @return
     */
    @Transactional
    public boolean removeFromCart(Product product) {
        // Получаем авторизованного пользователя
        var user = authService.getAuthUser().orElse(null);

        // Если пользователь не авторизован, то ничего не делаем
        if (user == null) {
            return false;
        }

        // Удаляем товар из корзины
        var products = user.getProducts();
        products.remove(product);

        // Сохраняем пользователя
        save(user);
        return true;
    }

    /**
     * Обновление данных пользователя
     *
     * @param user
     * @param userEditDTO
     */
    @Transactional
    public void update(User user, UserEditDTO userEditDTO) {
        user.setName(userEditDTO.getName());
        user.setEmail(userEditDTO.getEmail());
        save(user);
    }


    /**
     * Обновление данных пользователя
     *
     * @param userEditDTO
     */
    @Transactional
    public boolean update(UserEditDTO userEditDTO) {
        // Получаем авторизованного пользователя
        var user = authService.getAuthUser().orElse(null);

        // Если пользователь не авторизован, то ничего не делаем
        if (user == null) {
            return false;
        }

        // Проверяем, что email не занят другим пользователем
        var existing = findByEmail(userEditDTO.getEmail());
        if (existing.isPresent() && !existing.get().equals(user)) {
            return false;
        }

        // Обновляем данные пользователя
        update(user, userEditDTO);
        return true;
    }

    public UserEditDTO getUserEditDTO() {
        var user = authService.getAuthUser().orElse(null);
        if (user == null) {
            return null;
        }
        return userMapper.userToUserEditDTO(user);
    }

    /**
     * Добавление товара в корзину по id товара
     *
     * @param productId
     * @return
     */
    @Transactional
    public boolean addToCartByProductId(int productId) {
        // Получаем авторизованного пользователя
        var user = authService.getAuthUser().orElse(null);

        // Если пользователь не авторизован, то ничего не делаем
        if (user == null) {
            return false;
        }

        // Получаем товар по id
        var product = productService.findById(productId).orElse(null);

        // Получаем список товаров в корзине
        var products = user.getProducts();

        // Если товар уже есть в корзине, то ничего не делаем
        if (products.contains(product)) {
            return false;
        }

        // Добавляем товар в корзину
        products.add(product);

        // Сохраняем пользователя
        save(user);
        return true;
    }

    /**
     * Удаление товара из корзины по id товара
     *
     * @param productId
     * @return
     */
    @Transactional
    public boolean removeFromCartByProductId(int productId) {
        var product = productService.findById(productId).orElse(null);

        if (product == null) {
            return false;
        } else {
            return removeFromCart(product);
        }
    }

    /**
     * Добавление товара в купленные
     */
    @Transactional
    public boolean addToBought(User user, Product product) {
        // Получаем список купленных товаров
        var boughtProducts = user.getBoughtProducts();

        // Если товар уже есть в купленных, то ничего не делаем
        if (boughtProducts.contains(product)) {
            return false;
        }

        // Добавляем товар в купленные
        boughtProducts.add(product);

        // Сохраняем пользователя
        save(user);
        return true;
    }

    /**
     * Пополнение баланса
     *
     * @param balanceTopupDTO
     * @return
     */
    @Transactional
    public BalanceTopUpRequest balanceTopUp(BalanceTopupDTO balanceTopupDTO) {
        // Получаем авторизованного пользователя
        var user = authService.getAuthUser().orElse(null);

        // Если пользователь не авторизован, то ничего не делаем
        if (user == null) {
            return null;
        }

        // Создаём заявку на пополнение баланса
        var balanceTopUpRequest = new BalanceTopUpRequest();
        balanceTopUpRequest.setAmount(balanceTopupDTO.getAmount());
        balanceTopUpRequest.setUser(user);
        balanceTopUpRequest.setName(balanceTopupDTO.getName());
        balanceTopUpRequest.setPhone(balanceTopupDTO.getPhone());
        balanceTopUpRequest.setStatus(Status.NEW);

        // Сохраняем заявку
        balanceTopUpRequestService.save(balanceTopUpRequest);

        return balanceTopUpRequest;
    }

    /**
     * Получение всех пользователей
     *
     * @return
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /***
     * Получение пользователя по id
     * @param id
     * @return
     */
    public User getById(int id) {
        return findById(id).orElse(null);
    }

    /**
     * Удаление пользователя по id
     *
     * @param id
     */
    @Transactional
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    /**
     * Оформление заказа
     *
     * @return
     */
    @Transactional
    public boolean checkout() {
        // Получаем авторизованного пользователя
        var user = authService.getAuthUser().orElse(null);

        // Если пользователь не авторизован, то ничего не делаем
        if (user == null) {
            return false;
        }

        // Получаем список товаров в корзине
        var products = user.getProducts();

        // Если корзина пуста, то ничего не делаем
        if (products.isEmpty()) {
            return false;
        }

        var total = ProductService.getCartTotal(products);

        // Проверяем, хватает ли у пользователя денег на покупки
        if (user.getBalance() < total) {
            return false;
        }

        // Списываем деньги у пользователя
        user.setBalance(user.getBalance() - total);

        // Создаём заказ
        var order = new Order();
        order.setUser(user);
        for (Product product : products) {
            var toAdd = productService.findById(product.getId()).orElse(null);
            order.getProducts().add(toAdd);
            addToBought(user, toAdd);
        }
        order.setTotal(total);
        order.setDate(LocalDateTime.now());

        // Сохраняем заказ
        orderService.save(order);

        // Очищаем корзину
        products.clear();

        // Сохраняем пользователя
        save(user);

        return true;
    }

    /**
     * Проверка на то, купил ли пользователь продукт
     *
     * @param product
     * @return
     */
    public boolean isBought(Product product) {
        // Получаем авторизованного пользователя
        var user = authService.getAuthUser().orElse(null);

        // Если пользователь не авторизован, то ничего не делаем
        if (user == null) {
            return false;
        }

        // Получаем список купленных товаров
        var boughtProducts = user.getBoughtProducts();

        return boughtProducts.contains(product);
    }
}
