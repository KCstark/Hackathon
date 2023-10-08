package com.capstone.hackathon.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Score")
public class Scores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ScoreId")
    private int scoreId;

    @ManyToOne
    @JoinColumn(name = "AcceptedIdea")
    private AcceptedIdea acceptedIdea; // Reference to the accepted idea

    @ManyToOne
    @JoinColumn(name = "JudgeId")
    private Judges judge; // Reference to the judge who provided the score

    @Column(name = "ScoreValue")
    private int scoreValue;

    public int getScoreId() {
        return scoreId;
    }

    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }

    public AcceptedIdea getAcceptedIdea() {
        return acceptedIdea;
    }

    public void setAcceptedIdea(AcceptedIdea acceptedIdea) {
        this.acceptedIdea = acceptedIdea;
    }

    public Judges getJudge() {
        return judge;
    }

    public void setJudge(Judges judge) {
        this.judge = judge;
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(int scoreValue) {
        this.scoreValue = scoreValue;
    }
}

