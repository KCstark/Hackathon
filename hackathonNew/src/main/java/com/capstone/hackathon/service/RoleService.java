package com.capstone.hackathon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.hackathon.entities.Role;
import com.capstone.hackathon.repo.RoleRepo;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepo roleRepository;

    @Autowired
    public RoleService(RoleRepo roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Create a new role
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    // Get a role by ID
    public Optional<Role> getRoleById(int roleId) {
        return roleRepository.findById(roleId);
    }

    // Get all roles
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Update a role
    public Role updateRole(Role updatedRole) {
        return roleRepository.save(updatedRole);
    }

    // Delete a role by ID
    public void deleteRole(int roleId) {
        roleRepository.deleteById(roleId);
    }
}
