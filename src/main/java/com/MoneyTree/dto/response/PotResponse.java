package com.MoneyTree.dto.response;

import com.MoneyTree.model.enums.PlantType;
import com.MoneyTree.model.enums.PlantStage;
import lombok.Data;

@Data
public class PotResponse {
    private Long id;
    private String name;
    private Double target;
    private Double saved;
    private Double remaining;
    private PlantType plantType;
    private PlantStage plantStage;
    private Integer progressPercent;
    private Integer transactionCount;
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
	public Double getTarget() {
		return target;
	}
	public void setTarget(Double target) {
		this.target = target;
	}
	public Double getSaved() {
		return saved;
	}
	public void setSaved(Double saved) {
		this.saved = saved;
	}
	public Double getRemaining() {
		return remaining;
	}
	public void setRemaining(Double remaining) {
		this.remaining = remaining;
	}
	public PlantType getPlantType() {
		return plantType;
	}
	public void setPlantType(PlantType plantType) {
		this.plantType = plantType;
	}
	public PlantStage getPlantStage() {
		return plantStage;
	}
	public void setPlantStage(PlantStage plantStage) {
		this.plantStage = plantStage;
	}
	public Integer getProgressPercent() {
		return progressPercent;
	}
	public void setProgressPercent(Integer progressPercent) {
		this.progressPercent = progressPercent;
	}
	public Integer getTransactionCount() {
		return transactionCount;
	}
	public void setTransactionCount(Integer transactionCount) {
		this.transactionCount = transactionCount;
	}
}