package com.capstone.hackathon.service;

import com.capstone.hackathon.entities.AcceptedIdea;
import com.capstone.hackathon.entities.Idea;
import com.capstone.hackathon.entities.Panelist;
import com.capstone.hackathon.errorHandling.ResourceNotFoundException;
import com.capstone.hackathon.repo.AcceptedIdeaRepo;
import com.capstone.hackathon.repo.IdeaRepo;
import com.capstone.hackathon.repo.PanelistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PanelistService {

    private final PanelistRepo panelistRepository;

    @Autowired
    public PanelistService(PanelistRepo panelistRepository) {
        this.panelistRepository = panelistRepository;
    }

    public List<Panelist> getAllPanelists() {
        return panelistRepository.findAll();
    }

    public Optional<Panelist> getPanelistById(int id) {
        return panelistRepository.findById(id);
    }

    public Panelist createPanelist(Panelist panelist) {
        return panelistRepository.save(panelist);
    }

    public Panelist updatePanelist(int id, Panelist updatedPanelist) {
        Optional<Panelist> existingPanelist = panelistRepository.findById(id);

        if (existingPanelist.isPresent()) {
            Panelist panelist = existingPanelist.get();
            // Update other properties here as needed
            return panelistRepository.save(panelist);
        } else {
            return null; // Panelist with the given id does not exist
        }
    }

    public boolean deletePanelist(int id) {
        Optional<Panelist> existingPanelist = panelistRepository.findById(id);

        if (existingPanelist.isPresent()) {
            panelistRepository.deleteById(id);
            return true;
        } else {
            return false; // Panelist with the given id does not exist
        }
    }

    // ****************************************************************************
    // */
    @Autowired

    private IdeaRepo ideaRepo;

    public List<Idea> getIdeasForPanelist(int panelistId) {

        // Fetch ideas associated with the panelist ID from the IdeaRepo
        Optional<Panelist> n=panelistRepository.findById(panelistId);
        System.out.println(n);
        if(n.isPresent()){
            return ideaRepo.findByPanelist(n.get());
        }
        throw new ResourceNotFoundException(panelistId+"this ID for panelist not found !!");

    }

    @Autowired

    private AcceptedIdeaRepo acceptedIdeaRepo;

    public boolean acceptIdea(int panelistId, int ideaId) {

        // Check if the panelist and idea exist

        Panelist panelist = panelistRepository.findById(panelistId).orElse(null);

        Idea idea = ideaRepo.findById(ideaId).orElse(null);

        if (panelist != null && idea != null) {

            // Update the status of the idea to "idea accepted"

            idea.setStatus("SELECTED");

            ideaRepo.save(idea);

            AcceptedIdea acceptedIdea = new AcceptedIdea();

            acceptedIdea.setIdea(idea);

            acceptedIdeaRepo.save(acceptedIdea);

            return true; // Idea accepted successfully

        }

        return false; // Idea or panelist not found

    }

    public boolean rejectIdea(int panelistId, int ideaId) {

        // Check if the panelist and idea exist

        Panelist panelist = panelistRepository.findById(panelistId).orElse(null);

        Idea idea = ideaRepo.findById(ideaId).orElse(null);

        if (panelist != null && idea != null) {

            // Update the status of the idea to "REJECTED"

            idea.setStatus("REJECTED");

            ideaRepo.save(idea);

            return true; // Idea rejected successfully

        }

        return false; // Idea or panelist not found

    }

}
