package ru.mirea.courseworkjavashop.domain.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "balance_top_up_request")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BalanceTopUpRequest {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Пользователь
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Сумма пополнения
     */
    @Column(name = "amount")
    private int amount;

    /**
     * Номер телефона
     */
    @Column(name = "phone")
    private String phone;

    /**
     * Имя
     */
    @Column(name = "name")
    private String name;

    /**
     * Статус
     */
    @Column(name = "status")
    private Status status;
}
