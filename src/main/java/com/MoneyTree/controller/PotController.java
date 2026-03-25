package com.MoneyTree.controller;

import com.MoneyTree.dto.request.CreatePotRequest;
import com.MoneyTree.dto.response.MilestoneResponse;
import com.MoneyTree.dto.response.PotResponse;
import com.MoneyTree.dto.response.TransactionResponse;
import com.MoneyTree.model.Milestone;
import com.MoneyTree.model.Pot;
import com.MoneyTree.model.Transaction;
import com.MoneyTree.service.PotService;
import com.MoneyTree.service.TransactionService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pots")
public class PotController {
    @Autowired
	private PotService potService;
    @Autowired
    private TransactionService transactionService;

    
    @GetMapping
    public ResponseEntity<List<PotResponse>> getAllPots() {
        List<Pot> pots = potService.getUserPots(1L); // Replace with actual user ID
        List<PotResponse> responses = pots.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PotResponse> getPotById(@PathVariable Long id) {
        Pot pot = potService.getPotById(id);
        return ResponseEntity.ok(mapToResponse(pot));
    }
    
    @PostMapping
    public ResponseEntity<PotResponse> createPot(@RequestBody CreatePotRequest request) {
        Pot pot = potService.createPot(1L, request); // Replace with actual user ID
        return ResponseEntity.ok(mapToResponse(pot));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PotResponse> updatePot(@PathVariable Long id, @RequestBody CreatePotRequest request) {
        Pot pot = potService.updatePot(id, request);
        return ResponseEntity.ok(mapToResponse(pot));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePot(@PathVariable Long id) {
        potService.deletePot(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionResponse>> getPotTransactions(@PathVariable Long id) {
        List<Transaction> transactions = transactionService.getPotTransactions(id);
        List<TransactionResponse> responses = transactions.stream()
            .map(this::mapToTransactionResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    
    @GetMapping("/{id}/milestones")
    public ResponseEntity<List<MilestoneResponse>> getPotMilestones(@PathVariable Long id) {
        List<Milestone> milestones = potService.getPotMilestones(id);
        List<MilestoneResponse> responses = milestones.stream()
            .map(this::mapToMilestoneResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    
    private PotResponse mapToResponse(Pot pot) {
        PotResponse response = new PotResponse();
        response.setId(pot.getId());
        response.setName(pot.getName());
        response.setTarget(pot.getTarget());
        response.setSaved(pot.getSaved());
        response.setRemaining(pot.getRemaining());
        response.setPlantType(pot.getPlantType());
        response.setPlantStage(pot.getPlantStage());
        response.setProgressPercent(pot.getProgressPercent());
        response.setTransactionCount(pot.getTransactions() != null ? pot.getTransactions().size() : 0);
        return response;
    }
    
    private TransactionResponse mapToTransactionResponse(Transaction transaction) {
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
    
    private MilestoneResponse mapToMilestoneResponse(Milestone milestone) {
        MilestoneResponse response = new MilestoneResponse();
        response.setId(milestone.getId());
        response.setMilestone(milestone.getMilestone());
        response.setAchievedAt(milestone.getAchievedAt());
        return response;
    }
}