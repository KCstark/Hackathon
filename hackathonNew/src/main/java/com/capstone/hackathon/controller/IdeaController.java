package com.capstone.hackathon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.capstone.hackathon.entities.Idea;
import com.capstone.hackathon.entities.Panelist;
import com.capstone.hackathon.entities.User;
import com.capstone.hackathon.errorHandling.ResourceNotFoundException;
import com.capstone.hackathon.repo.UserRepo;
import com.capstone.hackathon.service.IdeaService;
import com.capstone.hackathon.service.PanelistService;

import jakarta.annotation.PostConstruct;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hackathon/ideas")
public class IdeaController {
    @Autowired
    private UserRepo ur;
    @Autowired
    private PanelistService ps;
    @Autowired
    private IdeaService ideaService;

     List<Panelist> panel;
    Panelist currP = null;

    @PostConstruct
    public void init() {
        panel = ps.getAllPanelists();
    }

    // Endpoint to allow a logged-in user to add an idea
    @PostMapping("{useId}/add")
    public ResponseEntity<Idea> addIdea(@RequestBody Idea idea, @PathVariable int userId) {
        // Check if the user is logged in
        Optional<User> u = ur.findById(userId);
        if (!u.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        int size = panel.size();
        if (size <= 0) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // Save the idea to the repository
        currP = panel.remove(0);
        Idea createdIdea = ideaService.createIdea(idea);
        createdIdea.setPanelist(currP);
        panel.add(size - 1, currP);
        return new ResponseEntity<>(createdIdea, HttpStatus.CREATED);
    }

    // View Idea
    @GetMapping("/{ideaId}")
    public ResponseEntity<Idea> viewIdeaById(@PathVariable int ideaId) {
        Optional<Idea> idea = ideaService.getIdeaById(ideaId);
        return idea.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Create a new idea
    @PostMapping
    public ResponseEntity<Idea> createIdea(@RequestBody Idea idea) {
        Idea createdIdea = ideaService.createIdea(idea);
        return new ResponseEntity<>(createdIdea, HttpStatus.CREATED);
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
