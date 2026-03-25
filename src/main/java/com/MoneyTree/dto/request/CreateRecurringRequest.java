package com.MoneyTree.dto.request;

import com.MoneyTree.model.enums.TransactionType;
import com.MoneyTree.model.enums.Category;
import com.MoneyTree.model.enums.Frequency;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CreateRecurringRequest {
    private String label;
    private Double amount;
    private TransactionType type;
    private Category category;
    private Frequency frequency;
    private Long potId;
    private LocalDate startDate;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public TransactionType getType() {
		return type;
	}
	public void setType(TransactionType type) {
		this.type = type;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Frequency getFrequency() {
		return frequency;
	}
	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}
	public Long getPotId() {
		return potId;
	}
	public void setPotId(Long potId) {
		this.potId = potId;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
}