package ru.mirea.courseworkjavashop.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.courseworkjavashop.domain.model.Order;
import ru.mirea.courseworkjavashop.repository.OrderRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    /**
     * Получение всех заказов
     *
     * @return
     */
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    /**
     * Сохранение заказа
     *
     * @param order
     */
    @Transactional
    public void save(Order order) {
        orderRepository.save(order);
    }

    /**
     * Получение заказа по id
     *
     * @param id
     * @return
     */
    public Order getById(int id) {
        return orderRepository.findById(id).orElse(null);
    }
}
