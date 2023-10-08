package com.capstone.hackathon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.capstone.hackathon.entities.Role;
import com.capstone.hackathon.errorHandling.ResourceNotFoundException;
import com.capstone.hackathon.service.RoleService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hackathon/roles")
public class RoleController {//used error handling here for reffrence

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // Create a new role
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role createdRole = roleService.createRole(role);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    // Get a role by ID
    @GetMapping("/{roleId}")
    public ResponseEntity<Role> getRoleById(@PathVariable int roleId) {
        Optional<Role> role = roleService.getRoleById(roleId);
        return role.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
        .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + roleId));
    }

    // Get all roles
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    // Update a role
    @PutMapping("/{roleId}")
    public ResponseEntity<Role> updateRole(@PathVariable int roleId, @RequestBody Role updatedRole) {
        Optional<Role> role = roleService.getRoleById(roleId);
        if (role.isPresent()) {
            updatedRole.setRoleId(roleId);
            Role updated = roleService.updateRole(updatedRole);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Role not found with ID: " + roleId);
        }
    }

    // Delete a role by ID
    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable int roleId) {
        Optional<Role> role = roleService.getRoleById(roleId);
        if (role.isPresent()) {
            roleService.deleteRole(roleId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            throw new ResourceNotFoundException("Role not found with ID: " + roleId);
        }
    }
}

