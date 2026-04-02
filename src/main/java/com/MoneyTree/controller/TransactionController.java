package com.MoneyTree.controller;

import com.MoneyTree.dto.request.CreateTransactionRequest;
import com.MoneyTree.dto.response.TransactionResponse;
import com.MoneyTree.model.Transaction;
import com.MoneyTree.service.RecurringService;
import com.MoneyTree.service.TransactionService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
	private TransactionService transactionService;
  
    
    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getUserTransactions(1L); // Replace with actual user ID
        List<TransactionResponse> responses = transactions.stream().map(this::mapToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    
    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody CreateTransactionRequest request) {
        Transaction transaction = transactionService.createTransaction(1L, request); // Replace with actual user ID
        return ResponseEntity.ok(mapToResponse(transaction));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.ok().build();
    }
    
    private TransactionResponse mapToResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setLabel(transaction.getLabel());
        response.setAmount(transaction.getAmount());
        response.setType(transaction.getType());
        response.setCategory(transaction.getCategory());
        response.setMood(transaction.getMood());
        response.setNote(transaction.getNote());
        response.setTransactionDate(transaction.getTransactionDate());
        if (transaction.getPot() != null) {
            response.setPotId(transaction.getPot().getId());
            response.setPotName(transaction.getPot().getName());
        }
        return response;
    }
}
