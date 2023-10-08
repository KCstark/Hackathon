package com.capstone.hackathon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.hackathon.entities.AcceptedIdea;
import com.capstone.hackathon.repo.AcceptedIdeaRepo;

import java.util.List;
import java.util.Optional;

@Service
public class AcceptedIdeaService {

    private final AcceptedIdeaRepo acceptedIdeaRepository;

    @Autowired
    public AcceptedIdeaService(AcceptedIdeaRepo acceptedIdeaRepository) {
        this.acceptedIdeaRepository = acceptedIdeaRepository;
    }

    // Create a new accepted idea
    public AcceptedIdea createAcceptedIdea(AcceptedIdea acceptedIdea) {
        return acceptedIdeaRepository.save(acceptedIdea);
    }

    // Get an accepted idea by ID
    public Optional<AcceptedIdea> getAcceptedIdeaById(int ideaId) {
        return acceptedIdeaRepository.findById(ideaId);
    }

    // Get all accepted ideas
    public List<AcceptedIdea> getAllAcceptedIdeas() {
        return acceptedIdeaRepository.findAll();
    }

    // Update an accepted idea
    public AcceptedIdea updateAcceptedIdea(AcceptedIdea updatedAcceptedIdea) {
        return acceptedIdeaRepository.save(updatedAcceptedIdea);
    }

    // Delete an accepted idea by ID
    public void deleteAcceptedIdea(int ideaId) {
        acceptedIdeaRepository.deleteById(ideaId);
    }
}
