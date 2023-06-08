package ru.mirea.courseworkjavashop.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mirea.courseworkjavashop.domain.dto.AdminEditUserDTO;
import ru.mirea.courseworkjavashop.domain.model.BalanceTopUpRequest;
import ru.mirea.courseworkjavashop.domain.model.Order;
import ru.mirea.courseworkjavashop.domain.model.User;
import ru.mirea.courseworkjavashop.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
//@RunWith(SpringRunner.class)
public class AdminServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private ProductService productService;

    @Mock
    private BalanceTopUpRequestService balanceTopUpRequestService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private AdminService adminService;

    @Test
    public void getAllBalanceTopUpRequests() {
        List<BalanceTopUpRequest> balanceTopUpRequests = new ArrayList<>();
        when(balanceTopUpRequestService.getAllBalanceTopUpRequests()).thenReturn(balanceTopUpRequests);

        List<BalanceTopUpRequest> result = adminService.getAllBalanceTopUpRequests();

        assertEquals(balanceTopUpRequests, result);
        verify(balanceTopUpRequestService).getAllBalanceTopUpRequests();
    }

    @Test
    public void getAllUsers() {
        List<User> users = new ArrayList<>();
        when(userService.getAllUsers()).thenReturn(users);

        List<User> result = adminService.getAllUsers();

        assertEquals(users, result);
        verify(userService).getAllUsers();
    }

    @Test
    public void getUserById() {
        User user = new User();
        when(userService.getById(1)).thenReturn(user);

        User result = adminService.getUserById(1);

        assertEquals(user, result);
        verify(userService).getById(1);
    }

    @Test
    public void getUserEditDTO() {
        User user = new User();
        AdminEditUserDTO userDTO = new AdminEditUserDTO();
        when(userService.getById(1)).thenReturn(user);
        when(userMapper.userToAdminEditUserDTO(user)).thenReturn(userDTO);

        AdminEditUserDTO result = adminService.getUserEditDTO(1);

        assertEquals(userDTO, result);
        verify(userService).getById(1);
        verify(userMapper).userToAdminEditUserDTO(user);
    }

    @Test
    public void editUser() {
        AdminEditUserDTO userDTO = new AdminEditUserDTO();
        userDTO.setId(1);
        userDTO.setBalance(100);
        userDTO.setRole("ROLE_ADMIN");
        userDTO.setName("Test User");
        userDTO.setEmail("test@example.com");
        userDTO.setChangePassword(true);
        userDTO.setPassword("newpassword");
        User user = new User();
        when(userService.getById(1)).thenReturn(user);
        when(userService.encodePassword("newpassword")).thenReturn("encodedpassword");

        adminService.editUser(userDTO);

        verify(userService).getById(1);
        verify(userService).encodePassword("newpassword");
        verify(userService).save(user);
        assertEquals(100.00, user.getBalance(), 0.01);
        assertEquals("ROLE_ADMIN", user.getRole());
        assertEquals("Test User", user.getName());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("encodedpassword", user.getPassword());
    }

    @Test
    public void deleteUser() {
        adminService.deleteUser(1);

        verify(userService).deleteById(1);
    }

    @Test
    public void getAllOrders() {
        List<Order> orders = new ArrayList<>();
        when(orderService.getAll()).thenReturn(orders);

        List<Order> result = adminService.getAllOrders();

        assertEquals(orders, result);
        verify(orderService).getAll();
    }

    @Test
    public void getAllProducts() {
        adminService.getAllProducts();

        verify(productService).getAll();
    }
}
