package com.capstone.hackathon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.hackathon.entities.Scores;
import com.capstone.hackathon.repo.ScoresRepo;

import java.util.List;

@Service
public class ScoresService {

    private final ScoresRepo scoresRepository;

    @Autowired
    public ScoresService(ScoresRepo scoresRepository) {
        this.scoresRepository = scoresRepository;
    }

    // Create a new score
    public Scores createScore(Scores score) {
        return scoresRepository.save(score);
    }

    // Get a score by ID
    public Scores getScoreById(int scoreId) {
        return scoresRepository.findById(scoreId).orElse(null);
    }

    // Get all scores
    public List<Scores> getAllScores() {
        return scoresRepository.findAll();
    }

    // Update a score
    public Scores updateScore(Scores updatedScore) {
        return scoresRepository.save(updatedScore);
    }

    // Delete a score by ID
    public void deleteScore(int scoreId) {
        scoresRepository.deleteById(scoreId);
    }
}
