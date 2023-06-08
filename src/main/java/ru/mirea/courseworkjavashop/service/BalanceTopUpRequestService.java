package ru.mirea.courseworkjavashop.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.courseworkjavashop.domain.model.BalanceTopUpRequest;
import ru.mirea.courseworkjavashop.repository.BalanceTopUpRequestRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class BalanceTopUpRequestService {
    private final BalanceTopUpRequestRepository balanceTopUpRequestRepository;

    @Transactional
    public void save(BalanceTopUpRequest balanceTopUpRequest) {
        balanceTopUpRequestRepository.save(balanceTopUpRequest);
    }

    public List<BalanceTopUpRequest> getAllBalanceTopUpRequests() {
        return balanceTopUpRequestRepository.findAll();
    }

    /**
     * Получение заявки на пополнение баланса по id
     *
     * @param id
     * @return
     */
    public BalanceTopUpRequest getById(int id) {
        return balanceTopUpRequestRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteById(int id) {
        balanceTopUpRequestRepository.deleteById(id);
    }
}
