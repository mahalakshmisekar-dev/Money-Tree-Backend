package com.MoneyTree.dto.response;

import lombok.Data;
import java.util.List;

import com.MoneyTree.model.enums.Category;

@Data
public class InsightResponse {
    private Double totalSaved;
    private Double monthlyIncome;
    public Double getMonthlySpent() {
		return monthlySpent;
	}

	public void setMonthlySpent(Double monthlySpent) {
		this.monthlySpent = monthlySpent;
	}

	public Double getMonthlySaved() {
		return monthlySaved;
	}

	public void setMonthlySaved(Double monthlySaved) {
		this.monthlySaved = monthlySaved;
	}

	public Integer getSavingRate() {
		return savingRate;
	}

	public void setSavingRate(Integer savingRate) {
		this.savingRate = savingRate;
	}

	public List<CategoryBreakdown> getCategoryBreakdown() {
		return categoryBreakdown;
	}

	public void setCategoryBreakdown(List<CategoryBreakdown> categoryBreakdown) {
		this.categoryBreakdown = categoryBreakdown;
	}

	public List<String> getInsights() {
		return insights;
	}

	public void setInsights(List<String> insights) {
		this.insights = insights;
	}

	private Double monthlySpent;
    private Double monthlySaved;
    private Integer savingRate;
    private List<CategoryBreakdown> categoryBreakdown;
    private List<String> insights;
    
    @Data
    public static class CategoryBreakdown {
        private Category category;
        private String emoji;
        private Double amount;
        private Integer percent;
		public Category getCategory() {
			return category;
		}
		public void setCategory(Category category) {
			this.category = category;
		}
		public Double getAmount() {
			return amount;
		}
		public void setAmount(Double amount) {
			this.amount = amount;
		}
		public Integer getPercent() {
			return percent;
		}
		public void setPercent(Integer percent) {
			this.percent = percent;
		}
		public String getEmoji() {
			return emoji;
		}
		public void setEmoji(String emoji) {
			this.emoji = emoji;
		}
    }

	public Double getTotalSaved() {
		return totalSaved;
	}

	public void setTotalSaved(Double totalSaved) {
		this.totalSaved = totalSaved;
	}

	public Double getMonthlyIncome() {
		return monthlyIncome;
	}

	public void setMonthlyIncome(Double monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}
}