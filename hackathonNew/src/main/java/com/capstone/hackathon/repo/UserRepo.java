package com.capstone.hackathon.repo;


import com.capstone.hackathon.entities.User;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

	@Transactional
	User findByEmail(String email);

}