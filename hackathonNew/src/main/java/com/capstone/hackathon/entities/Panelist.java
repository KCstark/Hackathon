package com.capstone.hackathon.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Panelist_Table")
public class Panelist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Panelist_Id")
    private int PanelistId;

    @OneToOne
    @JoinColumn(name = "UserID") // This refers to the foreign key column in the panelists table
    private User user;
    
    @OneToMany(mappedBy = "panelist", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Idea> ideas;

    @Override
    public String toString() {
        return "Panelist [PanelistId=" + PanelistId + ", user=" + user.getEmail() + ", ideas=" + ideas + "]";
    }

    public void setPanelistId(int panelistId) {
        PanelistId = panelistId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setIdeas(Set<Idea> ideas) {
        this.ideas = ideas;
    }

    public int getPanelistId() {
        return PanelistId;
    }

    public User getUser() {
        return user;
    }

    public Set<Idea> getIdeas() {
        return ideas;
    }

    

}
