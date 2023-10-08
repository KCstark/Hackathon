package com.capstone.hackathon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.hackathon.entities.Idea;
import com.capstone.hackathon.repo.IdeaRepo;

import java.util.List;
import java.util.Optional;

@Service
public class IdeaService {

    private final IdeaRepo ideaRepository;

    @Autowired
    public IdeaService(IdeaRepo ideaRepository) {
        this.ideaRepository = ideaRepository;
    }

    // Create a new idea
    public Idea createIdea(Idea idea) {
        return ideaRepository.save(idea);
    }

    // Get an idea by ID
    public Optional<Idea> getIdeaById(int ideaId) {
        return ideaRepository.findById(ideaId);
    }

    // Get all ideas
    public List<Idea> getAllIdeas() {
        return ideaRepository.findAll();
    }

    // Update an idea
    public Idea updateIdea(Idea updatedIdea) {
        return ideaRepository.save(updatedIdea);
    }

    // Delete an idea by ID
    public void deleteIdea(int ideaId) {
        ideaRepository.deleteById(ideaId);
    }
}
