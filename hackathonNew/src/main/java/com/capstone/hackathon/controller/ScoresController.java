package com.capstone.hackathon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.capstone.hackathon.entities.Scores;
import com.capstone.hackathon.service.ScoresService;

import java.util.List;

@RestController
@RequestMapping("/hackathon/scores")
public class ScoresController {

    private final ScoresService scoresService;

    @Autowired
    public ScoresController(ScoresService scoresService) {
        this.scoresService = scoresService;
    }

    // Create a new score
    @PostMapping
    public ResponseEntity<Scores> createScore(@RequestBody Scores score) {
        Scores createdScore = scoresService.createScore(score);
        return new ResponseEntity<>(createdScore, HttpStatus.CREATED);
    }

    // Get a score by ID
    @GetMapping("/{scoreId}")
    public ResponseEntity<Scores> getScoreById(@PathVariable int scoreId) {
        Scores score = scoresService.getScoreById(scoreId);
        if (score != null) {
            return new ResponseEntity<>(score, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get all scores
    @GetMapping
    public ResponseEntity<List<Scores>> getAllScores() {
        List<Scores> scores = scoresService.getAllScores();
        return new ResponseEntity<>(scores, HttpStatus.OK);
    }

    // Update a score
    @PutMapping("/{scoreId}")
    public ResponseEntity<Scores> updateScore(@PathVariable int scoreId, @RequestBody Scores updatedScore) {
        Scores score = scoresService.getScoreById(scoreId);
        if (score != null) {
            updatedScore.setScoreId(scoreId);
            Scores updated = scoresService.updateScore(updatedScore);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a score by ID
    @DeleteMapping("/{scoreId}")
    public ResponseEntity<Void> deleteScore(@PathVariable int scoreId) {
        Scores score = scoresService.getScoreById(scoreId);
        if (score != null) {
            scoresService.deleteScore(scoreId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
