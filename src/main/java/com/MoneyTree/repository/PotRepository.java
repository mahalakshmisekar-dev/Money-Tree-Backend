// PotRepository.java
package com.MoneyTree.repository;

import com.MoneyTree.model.Pot;
import com.MoneyTree.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PotRepository extends JpaRepository<Pot, Long> {
    List<Pot> findByUser(User user);
    List<Pot> findByUserAndId(User user, Long id);
}