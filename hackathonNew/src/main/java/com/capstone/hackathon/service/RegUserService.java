package com.capstone.hackathon.service;

import com.capstone.hackathon.entities.Idea;
import com.capstone.hackathon.entities.RegUsers;
import com.capstone.hackathon.entities.User;
import com.capstone.hackathon.errorHandling.BadRequestException;
import com.capstone.hackathon.errorHandling.NotAllowedException;
import com.capstone.hackathon.errorHandling.ResourceNotFoundException;
import com.capstone.hackathon.repo.IdeaRepo;
import com.capstone.hackathon.repo.RegUsersRepo;
import com.capstone.hackathon.repo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RegUserService {

    private final RegUsersRepo regUserRepo;

    @Autowired
    public RegUserService(RegUsersRepo regUsersRepository) {
        this.regUserRepo = regUsersRepository;
    }

    @Autowired
    private IdeaRepo ideaRepository;

    @Autowired
    private RegUsersRepo regUserRepository;

    @Autowired
    private UserRepo userRepository;
    /*********************************************************************************************/
    public void addTeamMembers(int ideaId, List<String> teamMemberEmails) {
        // Fetch the idea by its ID
        Idea idea = ideaRepository.findById(ideaId)
                .orElseThrow(() -> new ResourceNotFoundException("Idea with ID " + ideaId + " not found"));

        if (teamMemberEmails.size() > 4) {
            throw new BadRequestException("You can only add up to 4 team members, including yourself.");
        }

        for (String email : teamMemberEmails) {
            // Check if the email is registered in the User table
            User userOptional = userRepository.findByEmail(email);

            if (userOptional!=null) {
                // Check if the email is already registered in the RegUser table
                if (regUserRepository.existsById(userOptional.getuId())) {
                    throw new NotAllowedException(email + " is already part of another team.");
                } else {
                    // Create a new RegUser and associate it with the idea
                    RegUsers regUser = new RegUsers();
                    regUser.setUser(userOptional);
                    regUser.setIdea(idea);
                    regUserRepository.save(regUser);
                    Set<RegUsers> ru=idea.getUserIdeaMappings();
                    ru.add(regUser);
                    idea.setUserIdeaMappings(ru);//saving reguser in set for idea
                    userOptional.setRegUsers(regUser);//saving regUser in User
                }
            } else {
                throw new ResourceNotFoundException(email + " is not a registered user.");
            }
        }
    }
    /*********************************************************************************************/
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
