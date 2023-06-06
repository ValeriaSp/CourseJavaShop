package ru.mirea.courseworkjavashop.domain.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@NamedEntityGraphs({
        @NamedEntityGraph(name = "users", attributeNodes = @NamedAttributeNode("users"))
})
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    /*
     * Название товара
     */
    @Column(name = "name", nullable = false)
    private String name;


    /*
     * Описание товара
     */
    @Column(name = "description")
    private String description;


    /*
     * Цена товара
     */
    @Column(name = "price")
    private double price;

    /*
     * Содержание товара
     */
    @Column(name = "content")
    private String content;

    /*
     * Цена товара по скидке
     */
    @Column(name = "discount_price")
    private double discountPrice;

    /*
     * Есть ли скидка на товар
     */
    @Column(name = "discount")
    private boolean discount;

    /*
     * Картинка товара
     */
    @Column(name = "image")
    private String image;

    /*
     * Список покупателей у которых товар в корзине
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    /**
     * Список пользователей, которые купили товар
     * @param
     * @return
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_bought_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> usersBought = new HashSet<>();


    /**
     * Список заказов, в которых есть товар
     * @param
     * @return
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "products_orders",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    private Set<Order> orders = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return getId() == product.getId() && Double.compare(product.getPrice(), getPrice()) == 0 && Double.compare(product.getDiscountPrice(), getDiscountPrice()) == 0 && isDiscount() == product.isDiscount() && Objects.equals(getName(), product.getName()) && Objects.equals(getDescription(), product.getDescription()) && Objects.equals(getContent(), product.getContent()) && Objects.equals(getImage(), product.getImage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getPrice(), getContent(), getDiscountPrice(), isDiscount(), getImage());
    }
}
