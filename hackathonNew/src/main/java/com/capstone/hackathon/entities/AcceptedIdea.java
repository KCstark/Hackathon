package com.capstone.hackathon.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "AcceptedIdea")
public class AcceptedIdea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdeaId")
    private int ideaId;

    @OneToOne
    @JoinColumn(name = "RealIdeaId")
    private Idea idea; // Reference to the associated idea

    @OneToOne(mappedBy = "acceptedIdea", cascade = CascadeType.ALL, orphanRemoval = true)
    private Implementation ideaImplementation; // Reference to the implementation

    @OneToMany(mappedBy = "acceptedIdea", cascade = CascadeType.ALL, orphanRemoval = true)
    // @JsonManagedReference
    private Set<Scores> scores;

    @Override
    public String toString() {
        return "AcceptedIdea [ideaId=" + ideaId + ", idea=" + idea.getTitle() + ", ideaImplementation=" + ideaImplementation.getDescription()
                + ", scores=" + scores + "]";
    }

    public int getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(int ideaId) {
        this.ideaId = ideaId;
    }

    public Idea getIdea() {
        return idea;
    }

    public void setIdea(Idea idea) {
        this.idea = idea;
    }

    public Implementation getIdeaImplementation() {
        return ideaImplementation;
    }

    public void setIdeaImplementation(Implementation ideaImplementation) {
        this.ideaImplementation = ideaImplementation;
    }

    public Set<Scores> getScores() {
        return scores;
    }

    public void setScores(Set<Scores> scores) {
        this.scores = scores;
    }
}