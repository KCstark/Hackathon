package com.capstone.hackathon.repo;
import org.springframework.data.jpa.repository.JpaRepository;

import  com.capstone.hackathon.entities.Panelist;
public interface PanelistRepo  extends JpaRepository<Panelist, Integer>{
    
}
