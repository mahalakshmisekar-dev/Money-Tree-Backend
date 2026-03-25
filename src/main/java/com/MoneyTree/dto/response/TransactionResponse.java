package com.MoneyTree.dto.response;

import com.MoneyTree.model.enums.TransactionType;
import com.MoneyTree.model.enums.Category;
import com.MoneyTree.model.enums.Mood;
import lombok.Data;
import java.time.LocalDate;

@Data
public class TransactionResponse {
    private Long id;
    private String label;
    private Double amount;
    private TransactionType type;
    private Category category;
    private Mood mood;
    private String note;
    private LocalDate transactionDate;
    private Long potId;
    private String potName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public TransactionType getType() {
		return type;
	}
	public void setType(TransactionType type) {
		this.type = type;
	}
	public Mood getMood() {
		return mood;
	}
	public void setMood(Mood mood) {
		this.mood = mood;
	}
	public Long getPotId() {
		return potId;
	}
	public void setPotId(Long potId) {
		this.potId = potId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public LocalDate getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getPotName() {
		return potName;
	}
	public void setPotName(String potName) {
		this.potName = potName;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
}
