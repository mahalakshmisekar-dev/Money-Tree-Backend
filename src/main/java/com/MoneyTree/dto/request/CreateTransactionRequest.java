package com.MoneyTree.dto.request;

import com.MoneyTree.model.enums.TransactionType;
import com.MoneyTree.model.enums.Category;
import com.MoneyTree.model.enums.Mood;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CreateTransactionRequest {
    private String label;
    private Double amount;
    private TransactionType type;
    private Category category;
    private Mood mood;
    private String note;
    private Long potId;
    private LocalDate transactionDate;
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
	public Mood getMood() {
		return mood;
	}
	public void setMood(Mood mood) {
		this.mood = mood;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Long getPotId() {
		return potId;
	}
	public void setPotId(Long potId) {
		this.potId = potId;
	}
	public LocalDate getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}
}