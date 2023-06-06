package ru.mirea.courseworkjavashop.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.mirea.courseworkjavashop.domain.dto.BalanceTopupDTO;
import ru.mirea.courseworkjavashop.domain.model.BalanceTopUpRequest;
import ru.mirea.courseworkjavashop.domain.model.Order;
import ru.mirea.courseworkjavashop.domain.model.Product;
import ru.mirea.courseworkjavashop.domain.model.User;
import ru.mirea.courseworkjavashop.mapper.UserMapper;
import ru.mirea.courseworkjavashop.repository.UserRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private AuthService authService;

    @Mock
    private ProductService productService;

    @Mock
    private BalanceTopUpRequestService balanceTopUpRequestService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private UserService userService;

    @Test
    public void testEncodePassword() {
        String password = "password";
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        String result = userService.encodePassword(password);

        assertEquals(encodedPassword, result);
    }

    @Test
    public void testSave() {
        User user = new User();

        userService.save(user);

        verify(userRepository).save(user);
    }

    @Test
    public void testFindByEmail() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
    }

    @Test
    public void testFindById() {
        int id = 1;
        User user = new User();
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }


    @Test
    public void testAddToCart() {
        Product product = new Product();
        product.setId(1);
        User user = new User();
        user.setId(1);
        user.setProducts(new HashSet<>());
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);

        when(authService.getAuthUser()).thenReturn(Optional.of(user));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        boolean result = userService.addToCart(product);

        assertTrue(result);
        assertEquals(1, user.getProducts().size());
        assertTrue(user.getProducts().contains(product));
    }

    @Test
    public void testRemoveFromCart() {
        Product product = new Product();
        product.setId(1);
        User user = new User();
        user.setId(1);
        user.setProducts(new HashSet<>(Arrays.asList(product)));
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);

        when(authService.getAuthUser()).thenReturn(Optional.of(user));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        boolean result = userService.removeFromCart(product);

        assertTrue(result);
        assertEquals(0, user.getProducts().size());
        assertFalse(user.getProducts().contains(product));
    }

    @Test
    public void testAddToBought_whenProductAlreadyBought() {
        User user = new User();
        Product product = new Product();
        Set<Product> boughtProducts = new HashSet<>();
        boughtProducts.add(product);
        user.setBoughtProducts(boughtProducts);

        boolean result = userService.addToBought(user, product);

        assertFalse(result);
        verify(userRepository, never()).save(user);
    }

    @Test
    public void testAddToBought_whenProductNotBought() {
        User user = new User();
        Product product = new Product();
        Set<Product> boughtProducts = new HashSet<>();
        user.setBoughtProducts(boughtProducts);

        boolean result = userService.addToBought(user, product);

        assertTrue(result);
        verify(userRepository, times(1)).save(user);
        assertTrue(user.getBoughtProducts().contains(product));
    }

    @Test
    public void testBalanceTopUp_whenUserNotAuthenticated() {
        BalanceTopupDTO balanceTopupDTO = new BalanceTopupDTO();
        when(authService.getAuthUser()).thenReturn(Optional.empty());

        BalanceTopUpRequest result = userService.balanceTopUp(balanceTopupDTO);

        assertNull(result);
        verify(balanceTopUpRequestService, never()).save(any(BalanceTopUpRequest.class));
    }

    @Test
    public void testBalanceTopUp_whenUserAuthenticated() {
        BalanceTopupDTO balanceTopupDTO = new BalanceTopupDTO();
        User user = new User();
        user.setBalance(100);
        when(authService.getAuthUser()).thenReturn(Optional.of(user));

        BalanceTopUpRequest result = userService.balanceTopUp(balanceTopupDTO);

        assertNotNull(result);
        verify(balanceTopUpRequestService, times(1)).save(any(BalanceTopUpRequest.class));
    }

    @Test
    public void testGetAllUsers() {
        List<User> expectedUsers = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> result = userService.getAllUsers();

        assertEquals(expectedUsers, result);
    }

    @Test
    public void testGetById() {
        User expectedUser = new User();
        when(userRepository.findById(1)).thenReturn(Optional.of(expectedUser));

        User result = userService.getById(1);

        assertEquals(expectedUser, result);
    }

    @Test
    public void testGetById_whenUserNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        User result = userService.getById(1);

        assertNull(result);
    }

    @Test
    public void testDeleteById() {
        userService.deleteById(1);

        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    public void testCheckout_whenUserNotAuthenticated() {
        when(authService.getAuthUser()).thenReturn(Optional.empty());

        boolean result = userService.checkout();

        assertFalse(result);
        verify(orderService, never()).save(any(Order.class));
    }

    @Test
    public void testCheckout_whenCartIsEmpty() {
        User user = new User();
        when(authService.getAuthUser()).thenReturn(Optional.of(user));

        boolean result = userService.checkout();

        assertFalse(result);
        verify(orderService, never()).save(any(Order.class));
    }

}
