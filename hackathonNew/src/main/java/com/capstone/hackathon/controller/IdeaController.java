package com.capstone.hackathon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.capstone.hackathon.entities.Idea;
import com.capstone.hackathon.service.IdeaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ideas")
public class IdeaController {

    private final IdeaService ideaService;

    @Autowired
    public IdeaController(IdeaService ideaService) {
        this.ideaService = ideaService;
    }

    // Create a new idea
    @PostMapping
    public ResponseEntity<Idea> createIdea(@RequestBody Idea idea) {
        Idea createdIdea = ideaService.createIdea(idea);
        return new ResponseEntity<>(createdIdea, HttpStatus.CREATED);
    }

    // Get an idea by ID
    @GetMapping("/{ideaId}")
    public ResponseEntity<Idea> getIdeaById(@PathVariable int ideaId) {
        Optional<Idea> idea = ideaService.getIdeaById(ideaId);
        return idea.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get all ideas
    @GetMapping
    public ResponseEntity<List<Idea>> getAllIdeas() {
        List<Idea> ideas = ideaService.getAllIdeas();
        return new ResponseEntity<>(ideas, HttpStatus.OK);
    }

    // Update an idea
    @PutMapping("/{ideaId}")
    public ResponseEntity<Idea> updateIdea(@PathVariable int ideaId, @RequestBody Idea updatedIdea) {
        Optional<Idea> idea = ideaService.getIdeaById(ideaId);
        if (idea.isPresent()) {
            updatedIdea.setIdeaId(ideaId);
            Idea updated = ideaService.updateIdea(updatedIdea);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete an idea by ID
    @DeleteMapping("/{ideaId}")
    public ResponseEntity<Void> deleteIdea(@PathVariable int ideaId) {
        Optional<Idea> idea = ideaService.getIdeaById(ideaId);
        if (idea.isPresent()) {
            ideaService.deleteIdea(ideaId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
