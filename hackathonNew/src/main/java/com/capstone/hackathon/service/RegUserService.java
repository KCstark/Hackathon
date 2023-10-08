package com.capstone.hackathon.service;

import com.capstone.hackathon.entities.RegUsers;
import com.capstone.hackathon.repo.RegUsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegUserService {

    private final RegUsersRepo regUserRepo;

    @Autowired
    public RegUserService(RegUsersRepo regUsersRepository) {
        this.regUserRepo = regUsersRepository;
    }

    public List<RegUsers> getAllRegUsers() {
        return regUserRepo.findAll();
    }

    public Optional<RegUsers> getRegUserById(int id) {
        return regUserRepo.findById(id);
    }

    public RegUsers createRegUser(RegUsers regUser) {
        return regUserRepo.save(regUser);
    }

    public RegUsers updateRegUser(int id, RegUsers updatedRegUser) {
        Optional<RegUsers> existingRegUser = regUserRepo.findById(id);

        if (existingRegUser.isPresent()) {
            RegUsers regUser = existingRegUser.get();
            // Update other properties here as needed
            return regUserRepo.save(regUser);
        } else {
            return null; // RegUser with the given id does not exist
        }
    }

    public boolean deleteRegUser(int id) {
        Optional<RegUsers> existingRegUser = regUserRepo.findById(id);

        if (existingRegUser.isPresent()) {
            regUserRepo.deleteById(id);
            return true;
        } else {
            return false; // RegUser with the given id does not exist
        }
    }
}
