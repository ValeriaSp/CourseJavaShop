package ru.mirea.courseworkjavashop.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.mirea.courseworkjavashop.domain.dto.AdminEditUserDTO;
import ru.mirea.courseworkjavashop.service.AdminService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new AdminController(adminService)).build();
    }

    @Test
    public void balanceRequestsTest() throws Exception {
        mockMvc.perform(get("/admin/balance-requests")).andExpect(status().isOk()).andExpect(view().name("admin/balance-requests"));
    }

    @Test
    public void confirmBalanceRequestTest() throws Exception {
        mockMvc.perform(post("/admin/balance-requests/accept/{id}", 1)).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/admin/balance-requests?success"));
        verify(adminService, times(1)).confirmBalanceRequest(1);
    }

    @Test
    public void rejectBalanceRequestTest() throws Exception {
        mockMvc.perform(post("/admin/balance-requests/reject/{id}", 1)).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/admin/balance-requests?success"));
        verify(adminService, times(1)).rejectBalanceRequest(1);
    }

    @Test
    public void deleteBalanceRequestTest() throws Exception {
        mockMvc.perform(post("/admin/balance-requests/delete/{id}", 1)).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/admin/balance-requests?success"));
        verify(adminService, times(1)).deleteBalanceRequest(1);
    }

    @Test
    public void usersTest() throws Exception {
        mockMvc.perform(get("/admin/users")).andExpect(status().isOk()).andExpect(view().name("admin/users"));
    }

    @Test
    public void editUserTest() throws Exception {
        when(adminService.getUserEditDTO(1)).thenReturn(new AdminEditUserDTO());
        mockMvc.perform(get("/admin/users/edit/{id}", 1)).andExpect(status().isOk()).andExpect(view().name("admin/edit-user")).andExpect(model().attributeExists("userDTO"));
    }

    @Test
    public void ordersTest() throws Exception {
        mockMvc.perform(get("/admin/orders")).andExpect(status().isOk()).andExpect(view().name("admin/orders"));
    }

    @Test
    public void productsTest() throws Exception {
        mockMvc.perform(get("/admin/products")).andExpect(status().isOk()).andExpect(view().name("admin/products"));
    }
}