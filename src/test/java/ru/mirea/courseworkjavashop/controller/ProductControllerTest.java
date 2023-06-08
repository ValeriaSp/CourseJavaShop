package ru.mirea.courseworkjavashop.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import ru.mirea.courseworkjavashop.domain.model.Product;
import ru.mirea.courseworkjavashop.service.AuthService;
import ru.mirea.courseworkjavashop.service.ProductService;
import ru.mirea.courseworkjavashop.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @Mock
    private UserService userService;

    @Mock
    private AuthService authService;

    @Mock
    private Model model;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new ProductController(productService, userService, authService)).build();
    }

    @Test
    public void getProductByIdTest() throws Exception {

        Product product = new Product();
        product.setId(1);
        product.setName("Test");

        when(productService.getById(1)).thenReturn(product);

        mockMvc
                .perform(get("/products/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("product"));
    }
}
