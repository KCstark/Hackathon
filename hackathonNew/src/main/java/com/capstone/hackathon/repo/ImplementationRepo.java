package com.capstone.hackathon.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.hackathon.entities.Implementation;

@Repository
public interface ImplementationRepo extends JpaRepository<Implementation, Integer> {

}
