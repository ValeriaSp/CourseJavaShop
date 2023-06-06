package ru.mirea.courseworkjavashop.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Покупатель
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Список товаров
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "products_orders",
        joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products = new HashSet<>();

    /**
     * Сумма заказа
     */
    @Column(name = "total")
    private double total;

    /**
     * Дата заказа
     */
    @Column(name = "date")
    private LocalDateTime date;

}
