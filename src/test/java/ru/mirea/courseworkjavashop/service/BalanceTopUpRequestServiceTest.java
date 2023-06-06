package ru.mirea.courseworkjavashop.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mirea.courseworkjavashop.domain.model.BalanceTopUpRequest;
import ru.mirea.courseworkjavashop.repository.BalanceTopUpRequestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BalanceTopUpRequestServiceTest {

    @Mock
    private BalanceTopUpRequestRepository balanceTopUpRequestRepository;

    @InjectMocks
    private BalanceTopUpRequestService balanceTopUpRequestService;

    @Test
    public void testSave() {
        BalanceTopUpRequest request = new BalanceTopUpRequest();
        balanceTopUpRequestService.save(request);
        verify(balanceTopUpRequestRepository, times(1)).save(request);
    }

    @Test
    public void testGetAllBalanceTopUpRequests() {
        List<BalanceTopUpRequest> requests = new ArrayList<>();
        when(balanceTopUpRequestRepository.findAll()).thenReturn(requests);

        List<BalanceTopUpRequest> result = balanceTopUpRequestService.getAllBalanceTopUpRequests();

        verify(balanceTopUpRequestRepository, times(1)).findAll();
        assertEquals(requests, result);
    }

    @Test
    public void testGetById() {
        BalanceTopUpRequest request = new BalanceTopUpRequest();
        when(balanceTopUpRequestRepository.findById(anyInt())).thenReturn(Optional.of(request));

        BalanceTopUpRequest result = balanceTopUpRequestService.getById(1);

        verify(balanceTopUpRequestRepository, times(1)).findById(1);
        assertEquals(request, result);
    }

    @Test
    public void testDeleteById() {
        balanceTopUpRequestService.deleteById(1);
        verify(balanceTopUpRequestRepository, times(1)).deleteById(1);
    }
}
