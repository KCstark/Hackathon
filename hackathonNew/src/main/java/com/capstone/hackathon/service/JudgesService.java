package com.capstone.hackathon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.hackathon.entities.Judges;
import com.capstone.hackathon.repo.JudgesRepo;

import java.util.List;
import java.util.Optional;

@Service
public class JudgesService {

    private final JudgesRepo judgesRepository;

    @Autowired
    public JudgesService(JudgesRepo judgesRepository) {
        this.judgesRepository = judgesRepository;
    }

    // Create a new judge
    public Judges createJudge(Judges judge) {
        return judgesRepository.save(judge);
    }

    // Get a judge by ID
    public Optional<Judges> getJudgeById(int judgeId) {
        return judgesRepository.findById(judgeId);
    }

    // Get all judges
    public List<Judges> getAllJudges() {
        return judgesRepository.findAll();
    }

    // Update a judge
    public Judges updateJudge(Judges updatedJudge) {
        return judgesRepository.save(updatedJudge);
    }

    // Delete a judge by ID
    public void deleteJudge(int judgeId) {
        judgesRepository.deleteById(judgeId);
    }
}

