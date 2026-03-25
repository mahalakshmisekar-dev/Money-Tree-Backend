package com.MoneyTree.controller;

import com.MoneyTree.dto.response.InsightResponse;
import com.MoneyTree.dto.response.UserResponse;
import com.MoneyTree.service.InsightService;
import com.MoneyTree.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    @Autowired
    private InsightService insightService;
    

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.getProfile(1L)); // Replace with actual user ID
    }
    
    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.getProfile(1L)); // Replace with actual user ID
    }
    
    @GetMapping("/me/insights")
    public ResponseEntity<InsightResponse> getInsights(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(insightService.getInsights(1L)); // Replace with actual user ID
    }
    
    @GetMapping("/me/insights/{year}/{month}")
    public ResponseEntity<InsightResponse> getInsightsByMonth(@AuthenticationPrincipal UserDetails userDetails,
                                                                @PathVariable int year,
                                                                @PathVariable int month) {
        return ResponseEntity.ok(insightService.getInsightsByMonth(1L, year, month)); // Replace with actual user ID
    }
}