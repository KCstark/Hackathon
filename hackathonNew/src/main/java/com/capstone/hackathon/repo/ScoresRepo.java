package com.capstone.hackathon.repo;

import com.capstone.hackathon.entities.Scores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoresRepo extends JpaRepository<Scores, Integer> {
    
}
