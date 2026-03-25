package com.MoneyTree.repository;

import com.MoneyTree.model.Pot;
import com.MoneyTree.model.Transaction;
import com.MoneyTree.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
    List<Transaction> findByUserAndTransactionDateBetween(User user, LocalDate start, LocalDate end);
    List<Transaction> findByUserAndPot(User user, Pot pot);
    
    @Query("SELECT t.category, SUM(t.amount) FROM Transaction t WHERE t.user = :user AND t.type = 'EXPENSE' AND t.transactionDate BETWEEN :start AND :end GROUP BY t.category")
    List<Object[]> getCategoryBreakdown(@Param("user") User user, @Param("start") LocalDate start, @Param("end") LocalDate end);
    
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.user = :user AND t.type = 'INCOME' AND t.transactionDate BETWEEN :start AND :end")
    Double getTotalIncome(@Param("user") User user, @Param("start") LocalDate start, @Param("end") LocalDate end);
    
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.user = :user AND t.type = 'EXPENSE' AND t.transactionDate BETWEEN :start AND :end")
    Double getTotalExpense(@Param("user") User user, @Param("start") LocalDate start, @Param("end") LocalDate end);
}