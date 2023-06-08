package ru.mirea.courseworkjavashop.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    /**
     * Имя пользователя
     */
    @Column(name = "name")
    private String name;

    /**
     * Email пользователя
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;


    /**
     * Пароль пользователя
     */
    @Column(name = "password", nullable = false)
    private String password;


    /**
     * Баланс пользователя
     */
    @Column(name = "balance")
    private double balance;


    /**
     * Роль пользователя
     */
    @Column(name = "role", nullable = false)
    private String role;

    /**
     * Список топ-апов
     */
    @OneToMany
    @JoinColumn(name = "balance_top_up_request_id")
    private List<BalanceTopUpRequest> balanceTopUpRequests;


    /**
     * Корзина пользователя
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_products",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products = new HashSet<>();


    /**
     * Заказы пользователя
     */
    @OneToMany
    @JoinColumn(name = "order_id")
    private Set<Order> orders = new HashSet<>();

    /**
     * Купленные товары пользователя
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_bought_products",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> boughtProducts = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return getId() == user.getId() && Double.compare(user.getBalance(), getBalance()) == 0 && Objects.equals(getName(), user.getName()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getRole(), user.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getEmail(), getPassword(), getBalance(), getRole());
    }
}