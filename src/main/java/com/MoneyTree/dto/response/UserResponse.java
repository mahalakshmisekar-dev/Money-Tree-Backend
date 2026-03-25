package com.MoneyTree.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private Integer streak;
    private Integer activePots;
    private Integer completedPots;
    private String currency;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getStreak() {
		return streak;
	}
	public void setStreak(Integer streak) {
		this.streak = streak;
	}
	public Integer getActivePots() {
		return activePots;
	}
	public void setActivePots(Integer activePots) {
		this.activePots = activePots;
	}
	public Integer getCompletedPots() {
		return completedPots;
	}
	public void setCompletedPots(Integer completedPots) {
		this.completedPots = completedPots;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
}