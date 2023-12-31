package com.capstone.hackathon.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Ideas")
public class Idea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdeaId")
    private int ideaId;
    @Column(name = "Title")
    private String title;//
    @Column(name = "Description")
    private String description;//word limit 300 
    @Column(name = "Status")
    private String status="PENDING";

    @OneToMany(mappedBy = "idea", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<RegUsers> userIdeaMappings;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    // @JoinColumn(name = "PanelistId") // This refers to the foreign key column in the ideas table
    @JsonBackReference // This indicates the back reference in the bidirectional relationship
    private Panelist panelist;

    @OneToOne(mappedBy = "idea")
    private AcceptedIdea acceptedIdea; // Reference to the accepted idea, if applicable
    

    @Override
    public String toString() {
        return "Idea [ideaId=" + ideaId + ", title=" + title + ", description=" + description + ", status=" + status
                + ", userIdeaMappings=" + userIdeaMappings + ", acceptedIdea=" + acceptedIdea + "]";
    }

    public AcceptedIdea getAcceptedIdea() {
        return acceptedIdea;
    }

    public void setAcceptedIdea(AcceptedIdea acceptedIdea) {
        this.acceptedIdea = acceptedIdea;
    }

    public int getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(int ideaId) {
        this.ideaId = ideaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<RegUsers> getUserIdeaMappings() {
        return userIdeaMappings;
    }

    public void setUserIdeaMappings(Set<RegUsers> userIdeaMappings) {
        this.userIdeaMappings = userIdeaMappings;
    }

    public Panelist getPanelist() {
        return panelist;
    }

    public void setPanelist(Panelist panelist) {
        this.panelist = panelist;
    }
    
}
