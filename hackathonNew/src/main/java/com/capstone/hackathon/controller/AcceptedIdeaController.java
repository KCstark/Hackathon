package com.capstone.hackathon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.capstone.hackathon.entities.AcceptedIdea;
import com.capstone.hackathon.service.AcceptedIdeaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hackathon/accepted-ideas")
public class AcceptedIdeaController {

    private final AcceptedIdeaService acceptedIdeaService;

    @Autowired
    public AcceptedIdeaController(AcceptedIdeaService acceptedIdeaService) {
        this.acceptedIdeaService = acceptedIdeaService;
    }

    // Create a new accepted idea
    @PostMapping
    public ResponseEntity<AcceptedIdea> createAcceptedIdea(@RequestBody AcceptedIdea acceptedIdea) {
        AcceptedIdea createdAcceptedIdea = acceptedIdeaService.createAcceptedIdea(acceptedIdea);
        return new ResponseEntity<>(createdAcceptedIdea, HttpStatus.CREATED);
    }

    // Get an accepted idea by ID
    @GetMapping("/{ideaId}")
    public ResponseEntity<AcceptedIdea> getAcceptedIdeaById(@PathVariable int ideaId) {
        Optional<AcceptedIdea> acceptedIdea = acceptedIdeaService.getAcceptedIdeaById(ideaId);
        return acceptedIdea.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get all accepted ideas
    @GetMapping
    public ResponseEntity<List<AcceptedIdea>> getAllAcceptedIdeas() {
        List<AcceptedIdea> acceptedIdeas = acceptedIdeaService.getAllAcceptedIdeas();
        return new ResponseEntity<>(acceptedIdeas, HttpStatus.OK);
    }

    // Update an accepted idea
    @PutMapping("/{ideaId}")
    public ResponseEntity<AcceptedIdea> updateAcceptedIdea(@PathVariable int ideaId, @RequestBody AcceptedIdea updatedAcceptedIdea) {
        Optional<AcceptedIdea> acceptedIdea = acceptedIdeaService.getAcceptedIdeaById(ideaId);
        if (acceptedIdea.isPresent()) {
            updatedAcceptedIdea.setIdeaId(ideaId);
            AcceptedIdea updated = acceptedIdeaService.updateAcceptedIdea(updatedAcceptedIdea);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete an accepted idea by ID
    @DeleteMapping("/{ideaId}")
    public ResponseEntity<Void> deleteAcceptedIdea(@PathVariable int ideaId) {
        Optional<AcceptedIdea> acceptedIdea = acceptedIdeaService.getAcceptedIdeaById(ideaId);
        if (acceptedIdea.isPresent()) {
            acceptedIdeaService.deleteAcceptedIdea(ideaId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

