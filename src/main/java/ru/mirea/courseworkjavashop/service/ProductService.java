package ru.mirea.courseworkjavashop.service;

import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.courseworkjavashop.domain.model.Order;
import ru.mirea.courseworkjavashop.domain.model.Product;
import ru.mirea.courseworkjavashop.domain.model.User;
import ru.mirea.courseworkjavashop.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    /**
     * Получение всех продуктов
     *
     * @return
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Получение конкретного продукта
     *
     * @return
     */
    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    /**
     * Получение конкретного продукта
     *
     * @return
     */
    public Product getById(int id) {
        return findById(id).orElse(null);
    }


    /**
     * Подсчёт общей стоимости товаров в корзине
     *
     * @param products
     * @return
     */
    public static double getCartTotal(Set<Product> products) {

        // Считаем общую стоимость товаров в корзине с учётом скидок на товары

        // Сначала считаем товары без скидки (isDiscount = false)
        var total = products.stream()
                .filter(product -> !product.isDiscount())
                .mapToDouble(Product::getPrice)
                .sum();

        // Затем считаем товары со скидкой (isDiscount = true)
        total += products.stream()
                .filter(Product::isDiscount)
                .mapToDouble(Product::getDiscountPrice)
                .sum();

        return total;
    }

    /**
     * Получение всех продуктов
     *
     * @return
     */
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Transactional
    public void save(Product product) {
        productRepository.save(product);
    }


    /**
     * Получение всех продуктов которые купил пользователь
     * @param user
     * @return
     */
    public List<Product> getBoughtProductsByUser(User user) {
        return productRepository.findAllByOrders_User(user);
    }
}
