package com.MoneyTree.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MilestoneResponse {
    private Long id;
    private Integer milestone;
    private LocalDateTime achievedAt;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getMilestone() {
		return milestone;
	}
	public void setMilestone(Integer milestone) {
		this.milestone = milestone;
	}
	public LocalDateTime getAchievedAt() {
		return achievedAt;
	}
	public void setAchievedAt(LocalDateTime achievedAt) {
		this.achievedAt = achievedAt;
	}
}