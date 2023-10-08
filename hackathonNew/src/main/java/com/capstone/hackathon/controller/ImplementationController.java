package com.capstone.hackathon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.capstone.hackathon.entities.Implementation;
import com.capstone.hackathon.service.ImplementationService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hackathon/implementations")
public class ImplementationController {

    private final ImplementationService implementationService;

    @Autowired
    public ImplementationController(ImplementationService implementationService) {
        this.implementationService = implementationService;
    }

    // Create a new implementation
    @PostMapping
    public ResponseEntity<Implementation> createImplementation(@RequestBody Implementation implementation) {
        Implementation createdImplementation = implementationService.createImplementation(implementation);
        return new ResponseEntity<>(createdImplementation, HttpStatus.CREATED);
    }

    // Get an implementation by ID
    @GetMapping("/{implementationId}")
    public ResponseEntity<Implementation> getImplementationById(@PathVariable int implementationId) {
        Optional<Implementation> implementation = implementationService.getImplementationById(implementationId);
        return implementation.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get all implementations
    @GetMapping
    public ResponseEntity<List<Implementation>> getAllImplementations() {
        List<Implementation> implementations = implementationService.getAllImplementations();
        return new ResponseEntity<>(implementations, HttpStatus.OK);
    }

    // Update an implementation
    @PutMapping("/{implementationId}")
    public ResponseEntity<Implementation> updateImplementation(@PathVariable int implementationId, @RequestBody Implementation updatedImplementation) {
        Optional<Implementation> implementation = implementationService.getImplementationById(implementationId);
        if (implementation.isPresent()) {
            updatedImplementation.setImplementationId(implementationId);
            Implementation updated = implementationService.updateImplementation(updatedImplementation);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete an implementation by ID
    @DeleteMapping("/{implementationId}")
    public ResponseEntity<Void> deleteImplementation(@PathVariable int implementationId) {
        Optional<Implementation> implementation = implementationService.getImplementationById(implementationId);
        if (implementation.isPresent()) {
            implementationService.deleteImplementation(implementationId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

