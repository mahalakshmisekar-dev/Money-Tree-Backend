package com.MoneyTree.model;

import com.MoneyTree.model.enums.PlantType;
import com.MoneyTree.model.enums.PlantStage;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pots")
public class Pot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double target;

    private Double saved = 0.0;
    private Double remaining;

    @Enumerated(EnumType.STRING)
    private PlantType plantType;

    @Enumerated(EnumType.STRING)
    private PlantStage plantStage = PlantStage.SEED;

    private Integer progressPercent = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "pot", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();

    @OneToMany(mappedBy = "pot", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Milestone> milestones = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        calculateRemaining();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void calculateRemaining() {
        this.remaining = this.target - this.saved;
        if (this.remaining < 0) this.remaining = 0.0;

        this.progressPercent = (int) ((this.saved / this.target) * 100);

        if (this.progressPercent >= 70) {
            this.plantStage = PlantStage.TREE;
        } else if (this.progressPercent >= 30) {
            this.plantStage = PlantStage.PLANT;
        } else {
            this.plantStage = PlantStage.SEED;
        }
    }

    public void updateSaved(Double amount, boolean isAdd) {
        if (isAdd) {
            this.saved += amount;
        } else {
            this.saved -= amount;
            if (this.saved < 0) this.saved = 0.0;
        }
        calculateRemaining();
    }

    // =========================
    // Getters & Setters
    // =========================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getTarget() { return target; }
    public void setTarget(Double target) { this.target = target; }

    public Double getSaved() { return saved; }
    public void setSaved(Double saved) { this.saved = saved; }

    public Double getRemaining() { return remaining; }
    public void setRemaining(Double remaining) { this.remaining = remaining; }

    public PlantType getPlantType() { return plantType; }
    public void setPlantType(PlantType plantType) { this.plantType = plantType; }

    public PlantStage getPlantStage() { return plantStage; }
    public void setPlantStage(PlantStage plantStage) { this.plantStage = plantStage; }

    public Integer getProgressPercent() { return progressPercent; }
    public void setProgressPercent(Integer progressPercent) { this.progressPercent = progressPercent; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<Transaction> getTransactions() { return transactions; }
    public void setTransactions(List<Transaction> transactions) { this.transactions = transactions; }

    public List<Milestone> getMilestones() { return milestones; }
    public void setMilestones(List<Milestone> milestones) { this.milestones = milestones; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

}