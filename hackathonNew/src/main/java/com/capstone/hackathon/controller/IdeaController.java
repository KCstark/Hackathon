package com.capstone.hackathon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.capstone.hackathon.entities.Idea;
import com.capstone.hackathon.entities.Panelist;
import com.capstone.hackathon.entities.RegUsers;
import com.capstone.hackathon.entities.User;
import com.capstone.hackathon.errorHandling.ResourceNotFoundException;
import com.capstone.hackathon.repo.RegUsersRepo;
import com.capstone.hackathon.repo.UserRepo;
import com.capstone.hackathon.service.IdeaService;
import com.capstone.hackathon.service.PanelistService;
import com.capstone.hackathon.service.RegUserService;

import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/hackathon/ideas")
public class IdeaController {
    @Autowired
    private UserRepo ur;
    @Autowired
    private PanelistService ps;
    @Autowired
    private IdeaService ideaService;
    @Autowired
    private RegUserService rs;
    @Autowired
    private RegUsersRepo rgr;

    List<Panelist> panel;
    Panelist currP = null;

    @PostConstruct
    public void init() {
        panel = ps.getAllPanelists();
    }

    // Endpoint to allow a logged-in user to add an idea
    @PostMapping("/{userId}/add")
    public ResponseEntity<Idea> addIdea(@RequestBody Idea idea, @PathVariable int userId) {
        // Check if the user is logged in
        Optional<User> u = ur.findById(userId);
        if (!u.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        int size = panel.size();
        if (size <= 0) {
            throw new ResourceNotFoundException("No panelist availale yet, cant save Idea, try again!");
        }
        // Save the idea to the repository
        currP = panel.remove(0);
        // System.out.println(currP);
        Idea createdIdea = ideaService.createIdea(idea);
        RegUsers rg = new RegUsers();
        rg.setIdea(createdIdea);
        rg.setUser(u.get());
        Set<RegUsers> r = createdIdea.getUserIdeaMappings();
        if(r == null){
            r = new HashSet<>();
        }
        r.add(rg);
        createdIdea.setUserIdeaMappings(r);
        rgr.save(rg);
        currP.getIdeas().size();

        createdIdea.setPanelist(currP);// saving panelist to idea
        Set<Idea> s = currP.getIdeas();
        if (s == null) {
            s = new HashSet<Idea>();
        }
        s.add(createdIdea);
        System.out.println(createdIdea);
        currP.setIdeas(s);// saving idea to panelist
        // System.out.println(currP.getIdeas());
        panel.add(size - 1, currP);
        
        return new ResponseEntity<>(createdIdea, HttpStatus.CREATED);
    }

    // Add Team Members
    // Endpoint to add team members to an idea
    @PostMapping("/{userId}/addTeamMembers")
    public ResponseEntity<String> addTeamMembersToIdea(@PathVariable int userId,
            @RequestBody List<String> teamMemberEmails) {
        // Call the service to add team members
        String s = rs.addTeamMembers(userId, teamMemberEmails);
        // You might want to return a response, depending on your use case.
        return new ResponseEntity<>(s, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/ideaByUserId")
    public ResponseEntity<Idea> getIdeaByUser(@PathVariable int userId) {
        try {
            Idea idea = ideaService.getIdeaByUser(userId);
            if (idea != null) {
                return new ResponseEntity<>(idea, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //get team for a idea
     @GetMapping("/{ideaId}/getTeam")
    public ResponseEntity<ArrayList<String>> getTeamForIdea(@PathVariable int ideaId) {
        try {
            ArrayList<String> team = ideaService.getTeam(ideaId);
            if (team != null && !team.isEmpty()) {
                return new ResponseEntity<>(team, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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

    // Update an idea for a user by their ID
    @PutMapping("/user/{userId}/update")
    public ResponseEntity<Idea> updateIdeaForUser(@PathVariable int userId, @RequestBody Idea updatedIdea) {
        Idea updated = ideaService.updateIdea(userId, updatedIdea);
        return new ResponseEntity<>(updated, HttpStatus.OK);
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
