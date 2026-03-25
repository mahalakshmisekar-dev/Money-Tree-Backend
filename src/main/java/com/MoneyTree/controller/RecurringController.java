package com.MoneyTree.controller;

import com.MoneyTree.dto.request.CreateRecurringRequest;
import com.MoneyTree.model.RecurringTransaction;
import com.MoneyTree.service.RecurringService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recurring")
public class RecurringController {
    
    @Autowired
    private RecurringService recurringService;

    
    @GetMapping
    public ResponseEntity<List<RecurringTransaction>> getAllRecurring() {
        return ResponseEntity.ok(recurringService.getUserRecurringTransactions(1L)); // Replace with actual user ID
    }
    
    @PostMapping
    public ResponseEntity<RecurringTransaction> createRecurring(@RequestBody CreateRecurringRequest request) {
        return ResponseEntity.ok(recurringService.createRecurringTransaction(1L, request)); // Replace with actual user ID
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelRecurring(@PathVariable Long id) {
        recurringService.cancelRecurringTransaction(id);
        return ResponseEntity.ok().build();
    }
}