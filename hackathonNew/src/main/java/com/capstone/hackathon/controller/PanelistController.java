package com.capstone.hackathon.controller;

import com.capstone.hackathon.entities.Panelist;
import com.capstone.hackathon.service.PanelistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hackathon/panelists")
public class PanelistController {

    private final PanelistService panelistService;

    @Autowired
    public PanelistController(PanelistService panelistService) {
        this.panelistService = panelistService;
    }

    @GetMapping
    public ResponseEntity<List<Panelist>> getAllPanelists() {
        List<Panelist> panelists = panelistService.getAllPanelists();
        return new ResponseEntity<>(panelists, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Panelist> getPanelistById(@PathVariable int id) {
        Optional<Panelist> panelist = panelistService.getPanelistById(id);
        return panelist.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Panelist> createPanelist(@RequestBody Panelist panelist) {
        Panelist createdPanelist = panelistService.createPanelist(panelist);
        return new ResponseEntity<>(createdPanelist, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Panelist> updatePanelist(@PathVariable int id, @RequestBody Panelist updatedPanelist) {
        Panelist updated = panelistService.updatePanelist(id, updatedPanelist);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePanelist(@PathVariable int id) {
        boolean deleted = panelistService.deletePanelist(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
