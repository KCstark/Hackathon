package com.capstone.hackathon.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.hackathon.entities.Judges;

@Repository
public interface JudgesRepo extends JpaRepository<Judges, Integer> {

}