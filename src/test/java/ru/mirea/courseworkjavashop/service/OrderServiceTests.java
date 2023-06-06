package ru.mirea.courseworkjavashop.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.mirea.courseworkjavashop.domain.model.Order;
import ru.mirea.courseworkjavashop.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceTests {
    @Mock
    private OrderRepository orderRepository;

    @Test
    void testGetAll() {
        List<Order> expectedOrders = new ArrayList<>();
        expectedOrders.add(new Order());
        expectedOrders.add(new Order());
        when(orderRepository.findAll()).thenReturn(expectedOrders);

        OrderService orderService = new OrderService(orderRepository);

        List<Order> actualOrders = orderService.getAll();

        verify(orderRepository, times(1)).findAll();
        assertThat(actualOrders).isEqualTo(expectedOrders);
    }

    @Test
    void testSave() {
        Order order = new Order();
        when(orderRepository.save(order)).thenReturn(order);

        OrderService orderService = new OrderService(orderRepository);

        orderService.save(order);

        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testGetById_orderFound() {
        int orderId = 1;
        Order expectedOrder = new Order();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(expectedOrder));

        OrderService orderService = new OrderService(orderRepository);

        Order actualOrder = orderService.getById(orderId);

        verify(orderRepository, times(1)).findById(orderId);
        assertThat(actualOrder).isEqualTo(expectedOrder);
    }

    @Test
    void testGetById_orderNotFound() {
        int orderId = 1;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        OrderService orderService = new OrderService(orderRepository);

        Order actualOrder = orderService.getById(orderId);

        verify(orderRepository, times(1)).findById(orderId);
        assertThat(actualOrder).isNull();
    }
}
