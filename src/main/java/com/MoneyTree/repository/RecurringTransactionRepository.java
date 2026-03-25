package com.MoneyTree.repository;

import com.MoneyTree.model.RecurringTransaction;
import com.MoneyTree.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface RecurringTransactionRepository extends JpaRepository<RecurringTransaction, Long> {
    List<RecurringTransaction> findByUser(User user);
    List<RecurringTransaction> findByUserAndActiveTrue(User user);
    List<RecurringTransaction> findByNextRunDateBeforeAndActiveTrue(LocalDate date);
}
