package com.capstone.hackathon.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.*;

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
    
    @OneToMany(mappedBy = "panelist", cascade = CascadeType.ALL)
    private Set<Idea> ideas=new HashSet<>();

    @Override
    public String toString() {
        return "Panelist [PanelistId=" + PanelistId + ", user=" + user + ", ideas=" + ideas + "]";
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
