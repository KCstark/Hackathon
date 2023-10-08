package com.capstone.hackathon.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.hackathon.entities.Idea;

@Repository
public interface IdeaRepo extends JpaRepository<Idea, Integer>  {
    
}
