package ru.mirea.courseworkjavashop.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.courseworkjavashop.domain.model.Product;
import ru.mirea.courseworkjavashop.domain.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    /**
     * Получение продукта по id
     */
    @EntityGraph(value = "users")
    Optional<Product> findById(int id);

    List<Product> findAllByOrders_User(User user);
}
