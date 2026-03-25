package com.MoneyTree.service;

import com.MoneyTree.dto.request.CreatePotRequest;
import com.MoneyTree.dto.response.PotResponse;
import com.MoneyTree.model.Milestone;
import com.MoneyTree.model.Pot;
import com.MoneyTree.model.User;
import com.MoneyTree.repository.MilestoneRepository;
import com.MoneyTree.repository.PotRepository;
import com.MoneyTree.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PotService {
    
    private final PotRepository potRepository;
    private final UserRepository userRepository;
    private final MilestoneRepository milestoneRepository;
    
    @Transactional
    public Pot createPot(Long userId, CreatePotRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Pot pot = new Pot();
        pot.setName(request.getName());
        pot.setTarget(request.getTarget());
        pot.setPlantType(request.getPlantType());
        pot.setUser(user);
        
        pot = potRepository.save(pot);
        
        // Update user's active pots count
        user.setActivePots(user.getActivePots() + 1);
        userRepository.save(user);
        
        return pot;
    }
    
    public List<Pot> getUserPots(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return potRepository.findByUser(user);
    }
    
    public Pot getPotById(Long potId) {
        return potRepository.findById(potId)
                .orElseThrow(() -> new RuntimeException("Pot not found"));
    }
    
    @Transactional
    public Pot updatePot(Long potId, CreatePotRequest request) {
        Pot pot = getPotById(potId);
        
        if (request.getName() != null) {
            pot.setName(request.getName());
        }
        if (request.getTarget() != null) {
            pot.setTarget(request.getTarget());
            pot.calculateRemaining();
        }
        if (request.getPlantType() != null) {
            pot.setPlantType(request.getPlantType());
        }
        
        return potRepository.save(pot);
    }
    
    @Transactional
    public void deletePot(Long potId) {
        Pot pot = getPotById(potId);
        User user = pot.getUser();
        
        // Update user's active pots count
        user.setActivePots(user.getActivePots() - 1);
        userRepository.save(user);
        
        potRepository.delete(pot);
    }
    
    @Transactional
    public void updatePotProgress(Long potId) {
        Pot pot = getPotById(potId);
        pot.calculateRemaining();
        potRepository.save(pot);
    }
    
    public List<Milestone> getPotMilestones(Long potId) {
        Pot pot = getPotById(potId);
        return milestoneRepository.findByPot(pot);
    }
}