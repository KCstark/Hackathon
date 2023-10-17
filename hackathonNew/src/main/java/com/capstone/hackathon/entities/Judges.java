package com.capstone.hackathon.entities;


import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "Judges")
public class Judges {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JudgeId")
    private int judgeId;
    @Column(name = "Name")
    private String name;

    @OneToOne
    @JoinColumn(name = "UserID") 
    private User user;

    @OneToMany(mappedBy = "judge", cascade = CascadeType.ALL)
    // @JsonManagedReference
    private Set<Scores> scores;
    
    @Override
    public String toString() {
        return "Judges [judgeId=" + judgeId + ", name=" + name + ", user=" + user.getEmail() + ", scores=" + scores + "]";
    }

    public int getJudgeId() {
        return judgeId;
    }

    public void setJudgeId(int judgeId) {
        this.judgeId = judgeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Scores> getScores() {
        return scores;
    }

    public void setScores(Set<Scores> scores) {
        this.scores = scores;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
