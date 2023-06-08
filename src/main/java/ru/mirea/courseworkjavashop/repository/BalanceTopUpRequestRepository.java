package ru.mirea.courseworkjavashop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.courseworkjavashop.domain.model.BalanceTopUpRequest;

@Repository
public interface BalanceTopUpRequestRepository extends JpaRepository<BalanceTopUpRequest, Integer> {
}
