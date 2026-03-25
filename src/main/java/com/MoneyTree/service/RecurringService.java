package com.MoneyTree.service;

import com.MoneyTree.dto.request.CreateRecurringRequest;
import com.MoneyTree.dto.request.CreateTransactionRequest;
import com.MoneyTree.model.*;
import com.MoneyTree.model.enums.Frequency;
import com.MoneyTree.repository.*;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecurringService {
    @Autowired
    private RecurringTransactionRepository recurringRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private PotRepository potRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionService transactionService;
    
    
    @Transactional
    public RecurringTransaction createRecurringTransaction(Long userId, CreateRecurringRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        RecurringTransaction recurring = new RecurringTransaction();
        recurring.setLabel(request.getLabel());
        recurring.setAmount(request.getAmount());
        recurring.setType(request.getType());
        recurring.setCategory(request.getCategory());
        recurring.setFrequency(request.getFrequency());
        recurring.setUser(user);

        if (request.getPotId() != null) {
            Pot pot = potRepository.findById(request.getPotId())
                    .orElseThrow(() -> new RuntimeException("Pot not found"));
            recurring.setPot(pot);
        }

        recurring.setNextRunDate(request.getStartDate() != null ? request.getStartDate() : LocalDate.now());

        return recurringRepository.save(recurring);
    }

    @Scheduled(cron = "0 0 0 * * *") // Run daily at midnight
    @Transactional
    public void processRecurringTransactions() {
        List<RecurringTransaction> dueTransactions = recurringRepository
                .findByNextRunDateBeforeAndActiveTrue(LocalDate.now());

        for (RecurringTransaction recurring : dueTransactions) {
            CreateTransactionRequest transactionRequest = new CreateTransactionRequest();
            transactionRequest.setLabel(recurring.getLabel());
            transactionRequest.setAmount(recurring.getAmount());
            transactionRequest.setType(recurring.getType());
            transactionRequest.setCategory(recurring.getCategory());
            transactionRequest.setMood(null);
            transactionRequest.setNote("Recurring transaction");
            transactionRequest.setPotId(recurring.getPot() != null ? recurring.getPot().getId() : null);
            transactionRequest.setTransactionDate(LocalDate.now());

            transactionService.createTransaction(recurring.getUser().getId(), transactionRequest);

            LocalDate nextRun = calculateNextRunDate(recurring.getNextRunDate(), recurring.getFrequency());
            recurring.setNextRunDate(nextRun);
            recurringRepository.save(recurring);
        }
    }

    private LocalDate calculateNextRunDate(LocalDate currentDate, Frequency frequency) {
        switch (frequency) {
            case DAILY:
                return currentDate.plusDays(1);
            case WEEKLY:
                return currentDate.plusWeeks(1);
            case MONTHLY:
                return currentDate.plusMonths(1);
            default:
                return currentDate.plusDays(1);
        }
    }

    public List<RecurringTransaction> getUserRecurringTransactions(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return recurringRepository.findByUser(user);
    }

    @Transactional
    public void cancelRecurringTransaction(Long id) {
        RecurringTransaction recurring = recurringRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurring transaction not found"));
        recurring.setActive(false);
        recurringRepository.save(recurring);
    }
}