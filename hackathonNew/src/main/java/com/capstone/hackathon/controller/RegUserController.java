package com.capstone.hackathon.controller;

import com.capstone.hackathon.entities.RegUsers;
import com.capstone.hackathon.service.RegUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hackathon/regusers")
public class RegUserController{

    private final RegUserService regUsersService;

    @Autowired
    public RegUserController(RegUserService regUsersService) {
        this.regUsersService = regUsersService;
    }

    @GetMapping
    public ResponseEntity<List<RegUsers>> getAllRegUsers() {
        List<RegUsers> regUsers = regUsersService.getAllRegUsers();
        return new ResponseEntity<>(regUsers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegUsers> getRegUserById(@PathVariable int id) {
        Optional<RegUsers> regUser = regUsersService.getRegUserById(id);
        return regUser.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<RegUsers> createRegUser(@RequestBody RegUsers regUser) {
        RegUsers createdRegUser = regUsersService.createRegUser(regUser);
        return new ResponseEntity<>(createdRegUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegUsers> updateRegUser(@PathVariable int id, @RequestBody RegUsers updatedRegUser) {
        RegUsers updated = regUsersService.updateRegUser(id, updatedRegUser);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegUser(@PathVariable int id) {
        boolean deleted = regUsersService.deleteRegUser(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
