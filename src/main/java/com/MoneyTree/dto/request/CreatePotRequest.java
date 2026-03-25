package com.MoneyTree.dto.request;

import com.MoneyTree.model.enums.PlantType;
import lombok.Data;

@Data
public class CreatePotRequest {
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
	public PlantType getPlantType() {
		return plantType;
	}
	public void setPlantType(PlantType plantType) {
		this.plantType = plantType;
	}
	private String name;
    private Double target;
    private PlantType plantType;
}
