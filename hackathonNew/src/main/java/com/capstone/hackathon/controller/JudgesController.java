package com.capstone.hackathon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.capstone.hackathon.entities.Judges;
import com.capstone.hackathon.service.JudgesService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hackathon/judges")
public class JudgesController {

    private final JudgesService judgesService;

    @Autowired
    public JudgesController(JudgesService judgesService) {
        this.judgesService = judgesService;
    }

    // Create a new judge
    @PostMapping
    public ResponseEntity<Judges> createJudge(@RequestBody Judges judge) {
        Judges createdJudge = judgesService.createJudge(judge);
        return new ResponseEntity<>(createdJudge, HttpStatus.CREATED);
    }

    // Get a judge by ID
    @GetMapping("/{judgeId}")
    public ResponseEntity<Judges> getJudgeById(@PathVariable int judgeId) {
        Optional<Judges> judge = judgesService.getJudgeById(judgeId);
        return judge.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get all judges
    @GetMapping
    public ResponseEntity<List<Judges>> getAllJudges() {
        List<Judges> judges = judgesService.getAllJudges();
        return new ResponseEntity<>(judges, HttpStatus.OK);
    }

    // Update a judge
    @PutMapping("/{judgeId}")
    public ResponseEntity<Judges> updateJudge(@PathVariable int judgeId, @RequestBody Judges updatedJudge) {
        Optional<Judges> judge = judgesService.getJudgeById(judgeId);
        if (judge.isPresent()) {
            updatedJudge.setJudgeId(judgeId);
            Judges updated = judgesService.updateJudge(updatedJudge);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a judge by ID
    @DeleteMapping("/{judgeId}")
    public ResponseEntity<Void> deleteJudge(@PathVariable int judgeId) {
        Optional<Judges> judge = judgesService.getJudgeById(judgeId);
        if (judge.isPresent()) {
            judgesService.deleteJudge(judgeId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

