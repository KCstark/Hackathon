package com.capstone.hackathon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.hackathon.entities.Implementation;
import com.capstone.hackathon.repo.ImplementationRepo;

import java.util.List;
import java.util.Optional;

@Service
public class ImplementationService {

    private final ImplementationRepo implementationRepository;

    @Autowired
    public ImplementationService(ImplementationRepo implementationRepository) {
        this.implementationRepository = implementationRepository;
    }

    // Create a new implementation
    public Implementation createImplementation(Implementation implementation) {
        return implementationRepository.save(implementation);
    }

    // Get an implementation by ID
    public Optional<Implementation> getImplementationById(int implementationId) {
        return implementationRepository.findById(implementationId);
    }

    // Get all implementations
    public List<Implementation> getAllImplementations() {
        return implementationRepository.findAll();
    }

    // Update an implementation
    public Implementation updateImplementation(Implementation updatedImplementation) {
        return implementationRepository.save(updatedImplementation);
    }

    // Delete an implementation by ID
    public void deleteImplementation(int implementationId) {
        implementationRepository.deleteById(implementationId);
    }
}

