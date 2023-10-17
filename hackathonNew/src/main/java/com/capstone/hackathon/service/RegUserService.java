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

import java.util.HashSet;
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
    public String addTeamMembers(int userId, List<String> teamMemberEmails) {
        // Fetch the user by its ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found"));

        if (teamMemberEmails.size() > 3) {
            throw new BadRequestException("You can only add up to 3 more members, excluding yourself.");
        }
        Idea idea= user.getRegUsers().getIdea();
        for (String email : teamMemberEmails) {
            // Check if the email is registered in the User table
            User userOptional = userRepository.findByEmail(email);

            if (userOptional!=null) {
                // Check if the email is already registered in the RegUser table
                if (regUserRepo.existsById(userOptional.getuId())) {
                    throw new NotAllowedException(email + " is already part of another team.");
                } else {
                    // Create a new RegUser and associate it with the idea
                    RegUsers regUser = new RegUsers();
                    regUser.setUser(userOptional);
                    regUser.setIdea(idea);
                    regUserRepo.save(regUser);
                    Set<RegUsers> ru=idea.getUserIdeaMappings();
                    if(ru==null){
                        ru=new HashSet<>();
                    }
                    ru.add(regUser);
                    idea.setUserIdeaMappings(ru);//saving reguser in set for idea
                    userOptional.setRegUsers(regUser);//saving regUser in User
                    
                }
            } else {
                throw new ResourceNotFoundException(email + " is not a registered user.");
            }
        }
        return "Team added succesfully";
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
