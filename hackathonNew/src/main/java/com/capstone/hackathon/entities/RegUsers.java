package com.capstone.hackathon.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Registered_Users")
public class RegUsers {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Registered_User_Id")
    private int RegId;

    @OneToOne
    @JoinColumn(name = "UserID") // This refers to the foreign key column in the registered_users table
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "IdeaId") // This refers to the foreign key column in the RegUsers table
    @JsonBackReference
    private Idea idea;

    @Override
    public String toString() {
        return "RegUsers [RegId=" + RegId + ", user=" + user.getEmail() + ", idea=" + idea.getTitle() + "]";
    }

    public int getRegId() {
        return RegId;
    }

    public void setRegId(int regId) {
        RegId = regId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Idea getIdea() {
        return idea;
    }

    public void setIdea(Idea idea) {
        this.idea = idea;
    }
}
