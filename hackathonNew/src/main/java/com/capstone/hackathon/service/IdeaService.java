package com.capstone.hackathon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.hackathon.entities.Idea;
import com.capstone.hackathon.entities.RegUsers;
import com.capstone.hackathon.entities.User;
import com.capstone.hackathon.errorHandling.ResourceNotFoundException;
import com.capstone.hackathon.repo.IdeaRepo;
import com.capstone.hackathon.repo.UserRepo;

import java.util.List;
import java.util.Optional;

@Service
public class IdeaService {

    private final IdeaRepo ideaRepository;

    @Autowired
    public IdeaService(IdeaRepo ideaRepository) {
        this.ideaRepository = ideaRepository;
    }

    @Autowired
    private UserRepo ur;

    // Create a new idea
    public Idea createIdea(Idea idea) {
        return ideaRepository.save(idea);
    }

    // Get an idea by ID
    public Optional<Idea> getIdeaById(int ideaId) {
        return ideaRepository.findById(ideaId);
    }

    // Get all ideas
    public List<Idea> getAllIdeas() {
        return ideaRepository.findAll();
    }

    // Update an idea wrt user ID
    public Idea updateIdea(int uId,Idea updatedIdea) {//title,descp
        Optional<User> u=ur.findById(uId);
        if(u.isPresent()){
            RegUsers ru=u.get().getRegUsers();
            if(ru==null){
                throw new ResourceNotFoundException("User is registerd but has not submitted any Idea yet!");
            }
            Idea i=ru.getIdea();
            i.setDescription(updatedIdea.getDescription());
            i.setTitle(updatedIdea.getTitle());
            updatedIdea.setIdeaId(i.getIdeaId());
            return updatedIdea;
        }else{
            throw new ResourceNotFoundException("User not registred!!");
        }
    }

    // Delete an idea by ID
    public void deleteIdea(int ideaId) {
        ideaRepository.deleteById(ideaId);
    }

}
