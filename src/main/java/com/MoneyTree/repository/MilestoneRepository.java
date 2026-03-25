package com.MoneyTree.repository;

import com.MoneyTree.model.Milestone;
import com.MoneyTree.model.Pot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
    List<Milestone> findByPot(Pot pot);
    Optional<Milestone> findByPotAndMilestone(Pot pot, Integer milestone);
}