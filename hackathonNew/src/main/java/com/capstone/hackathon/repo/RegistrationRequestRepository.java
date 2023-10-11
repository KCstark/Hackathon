package com.capstone.hackathon.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.capstone.hackathon.entities.RegistrationRequest;

import jakarta.transaction.Transactional;

public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest, Integer> {
    @Transactional
    List<RegistrationRequest> findByStatus(String string);
    // You can define custom query methods here if needed
}