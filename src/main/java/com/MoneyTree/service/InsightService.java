package com.MoneyTree.service;

import com.MoneyTree.dto.response.InsightResponse;
import com.MoneyTree.model.User;
import com.MoneyTree.model.Pot;
import com.MoneyTree.model.enums.Category;
import com.MoneyTree.repository.*;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class InsightService {
    
    private final TransactionRepository transactionRepository;
    private final PotRepository potRepository;
    private final UserRepository userRepository;
    
    public InsightResponse getInsights(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());
        
        // Calculate totals
        Double monthlyIncome = transactionRepository.getTotalIncome(user, startOfMonth, endOfMonth);
        Double monthlySpent = transactionRepository.getTotalExpense(user, startOfMonth, endOfMonth);
        Double monthlySaved = (monthlyIncome != null ? monthlyIncome : 0.0) - (monthlySpent != null ? monthlySpent : 0.0);
        
        // Calculate saving rate
        Double totalIncome = monthlyIncome != null ? monthlyIncome : 0.0;
        Integer savingRate = totalIncome > 0 ? (int) ((monthlySaved / totalIncome) * 100) : 0;
        
        // Calculate total saved across all pots
        List<Pot> pots = potRepository.findByUser(user);
        Double totalSaved = pots.stream().mapToDouble(Pot::getSaved).sum();
        
        // Get category breakdown
        List<Object[]> categoryData = transactionRepository.getCategoryBreakdown(user, startOfMonth, endOfMonth);
        List<InsightResponse.CategoryBreakdown> categoryBreakdown = new ArrayList<>();
        Double totalExpense = categoryData.stream()
            .mapToDouble(data -> (Double) data[1])
            .sum();
        
        for (Object[] data : categoryData) {
            Category category = (Category) data[0];
            Double amount = (Double) data[1];
            InsightResponse.CategoryBreakdown breakdown = new InsightResponse.CategoryBreakdown();
            breakdown.setCategory(category);
            breakdown.setEmoji(getCategoryEmoji(category));
            breakdown.setAmount(amount);
            breakdown.setPercent(totalExpense > 0 ? (int) ((amount / totalExpense) * 100) : 0);
            categoryBreakdown.add(breakdown);
        }
        
        // Generate AI insights
        List<String> insights = generateInsights(monthlySpent, monthlyIncome, savingRate, categoryBreakdown);
        
        InsightResponse response = new InsightResponse();
        response.setTotalSaved(totalSaved);
        response.setMonthlyIncome(monthlyIncome != null ? monthlyIncome : 0.0);
        response.setMonthlySpent(monthlySpent != null ? monthlySpent : 0.0);
        response.setMonthlySaved(monthlySaved);
        response.setSavingRate(savingRate);
        response.setCategoryBreakdown(categoryBreakdown);
        response.setInsights(insights);
        
        return response;
    }
    
    public InsightResponse getInsightsByMonth(Long userId, int year, int month) {
        // Similar to getInsights but for specific month
        // For now, just return current month insights
        return getInsights(userId);
    }
    
    private String getCategoryEmoji(Category category) {
        Map<Category, String> emojiMap = new HashMap<>();
        emojiMap.put(Category.FOOD, "🍕");
        emojiMap.put(Category.TRAVEL, "✈️");
        emojiMap.put(Category.SHOPPING, "🛍️");
        emojiMap.put(Category.HEALTH, "💊");
        emojiMap.put(Category.LEARN, "📚");
        emojiMap.put(Category.FUN, "🎮");
        emojiMap.put(Category.HOME, "🏠");
        emojiMap.put(Category.SAVINGS, "💰");
        return emojiMap.getOrDefault(category, "📦");
    }
    
    private List<String> generateInsights(Double monthlySpent, Double monthlyIncome, 
                                          Integer savingRate, 
                                          List<InsightResponse.CategoryBreakdown> breakdown) {
        List<String> insights = new ArrayList<>();
        
        if (savingRate > 30) {
            insights.add("🌟 Amazing! You're saving over 30% of your income. Keep up the great work!");
        } else if (savingRate > 15) {
            insights.add("👍 Good job! You're saving a healthy portion of your income. Aim for 30% to grow faster!");
        } else if (savingRate < 10) {
            insights.add("💡 Try to increase your savings rate to 15-30%. Small changes add up over time!");
        }
        
        if (monthlySpent != null && monthlyIncome != null && monthlySpent > monthlyIncome * 0.7) {
            insights.add("⚠️ Your expenses are quite high. Consider reviewing your spending habits.");
        }
        
        if (!breakdown.isEmpty()) {
            Optional<InsightResponse.CategoryBreakdown> topCategory = breakdown.stream()
                    .max(Comparator.comparing(InsightResponse.CategoryBreakdown::getAmount));
            
            if (topCategory.isPresent()) {
                InsightResponse.CategoryBreakdown top = topCategory.get();
                insights.add("📊 Your highest spending category is " + top.getCategory() + 
                           " at ₹" + String.format("%.0f", top.getAmount()) + 
                           " (" + top.getPercent() + "% of total)");
            }
        }
        
        if (insights.isEmpty()) {
            insights.add("🌱 Keep tracking your expenses to get personalized insights!");
        }
        
        return insights;
    }
}