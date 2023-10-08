package com.capstone.hackathon.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.hackathon.entities.AcceptedIdea;

@Repository
public interface AcceptedIdeaRepo extends JpaRepository<AcceptedIdea, Integer> {

}
