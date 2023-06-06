package ru.mirea.courseworkjavashop.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.mirea.courseworkjavashop.service.AuthService;
import ru.mirea.courseworkjavashop.service.ProductService;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.mockito.Mockito.when;

@WebMvcTest(HomeController.class)
public class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        when(authService.getUserInfo()).thenReturn(null);
        mockMvc = MockMvcBuilders.standaloneSetup(new HomeController(productService, authService)).build();
    }

    @Test
    public void testHome() throws Exception {
        when(productService.getAllProducts()).thenReturn(Collections.emptyList());
        when(authService.getUserInfo()).thenReturn(null);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attribute("products", Collections.emptyList()));
    }
}
