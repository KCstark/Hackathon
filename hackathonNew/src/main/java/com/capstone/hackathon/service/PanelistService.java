package com.capstone.hackathon.service;

import com.capstone.hackathon.entities.Panelist;
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
}
