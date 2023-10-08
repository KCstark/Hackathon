package com.capstone.hackathon.repo;

import org.springframework.data.jpa.repository.JpaRepository;


import com.capstone.hackathon.entities.RegUsers;

public interface RegUsersRepo extends JpaRepository<RegUsers, Integer>{
    
}
