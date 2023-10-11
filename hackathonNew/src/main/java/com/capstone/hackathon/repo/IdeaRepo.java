package com.capstone.hackathon.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.hackathon.entities.Idea;
import com.capstone.hackathon.entities.Panelist;

import jakarta.transaction.Transactional;

@Repository
public interface IdeaRepo extends JpaRepository<Idea, Integer>  {
    @Transactional
    List<Idea> findByPanelist(Panelist panelist);
}
