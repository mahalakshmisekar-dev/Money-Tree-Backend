package com.MoneyTree.service;

import com.MoneyTree.dto.request.CreateTransactionRequest;
import com.MoneyTree.model.*;
import com.MoneyTree.model.enums.TransactionType;
import com.MoneyTree.repository.*;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private  TransactionRepository transactionRepository;
    @Autowired
    private  PotRepository potRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MilestoneRepository milestoneRepository;
    @Autowired
    private PotService potService;

    // Explicit constructor for Spring injection
    public TransactionService(
            TransactionRepository transactionRepository,
            PotRepository potRepository,
            UserRepository userRepository,
            MilestoneRepository milestoneRepository,
            PotService potService
    ) {
        this.transactionRepository = transactionRepository;
        this.potRepository = potRepository;
        this.userRepository = userRepository;
        this.milestoneRepository = milestoneRepository;
        this.potService = potService;
    }

    @Transactional
    public Transaction createTransaction(Long userId, CreateTransactionRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Transaction transaction = new Transaction();
        transaction.setLabel(request.getLabel());
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setCategory(request.getCategory());
        transaction.setMood(request.getMood());
        transaction.setNote(request.getNote());
        transaction.setTransactionDate(request.getTransactionDate());
        transaction.setUser(user);

        if (request.getPotId() != null) {
            Pot pot = potRepository.findById(request.getPotId())
                    .orElseThrow(() -> new RuntimeException("Pot not found"));
            transaction.setPot(pot);

            if (request.getType() == TransactionType.INCOME) {
                pot.updateSaved(request.getAmount(), true);
            } else {
                pot.updateSaved(request.getAmount(), false);
            }
            potRepository.save(pot);
            checkMilestones(pot);
        }

        return transactionRepository.save(transaction);
    }

    private void checkMilestones(Pot pot) {
        int[] milestones = {25, 50, 75, 100};

        for (int milestone : milestones) {
            if (pot.getProgressPercent() >= milestone &&
                !milestoneRepository.findByPotAndMilestone(pot, milestone).isPresent()) {

                Milestone ms = new Milestone();
                ms.setMilestone(milestone);
                ms.setPot(pot);
                milestoneRepository.save(ms);

                if (milestone == 100) {
                    User user = pot.getUser();
                    user.setActivePots(user.getActivePots() - 1);
                    user.setCompletedPots(user.getCompletedPots() + 1);
                    userRepository.save(user);
                }
            }
        }
    }

    public List<Transaction> getUserTransactions(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return transactionRepository.findByUser(user);
    }

    public List<Transaction> getPotTransactions(Long potId) {
        Pot pot = potRepository.findById(potId)
                .orElseThrow(() -> new RuntimeException("Pot not found"));
        return transactionRepository.findByUserAndPot(pot.getUser(), pot);
    }

    @Transactional
    public void deleteTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (transaction.getPot() != null) {
            Pot pot = transaction.getPot();
            if (transaction.getType() == TransactionType.INCOME) {
                pot.updateSaved(transaction.getAmount(), false);
            } else {
                pot.updateSaved(transaction.getAmount(), true);
            }
            potRepository.save(pot);
        }

        transactionRepository.delete(transaction);
    }
}