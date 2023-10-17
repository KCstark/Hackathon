package com.capstone.hackathon.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Implementation")
public class Implementation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Impl_Id")
    private int implementationId;
    @Column(name = "Description")
    private String implDescription;
    @Column(name = "Documentation")
    private String documentation;

    @OneToOne
    @JoinColumn(name = "AcceptedIdea", unique = true)
    private AcceptedIdea acceptedIdea; // Reference to the accepted idea

    @Override
    public String toString() {
        return "Implementation [implementationId=" + implementationId + ", implDescription=" + implDescription
                + ", documentation=" + documentation + ", acceptedIdea=" + acceptedIdea.getIdeaId() + "]";
    }

    public int getImplementationId() {
        return implementationId;
    }

    public void setImplementationId(int implementationId) {
        this.implementationId = implementationId;
    }

    public AcceptedIdea getAcceptedIdea() {
        return acceptedIdea;
    }

    public void setAcceptedIdea(AcceptedIdea acceptedIdea) {
        this.acceptedIdea = acceptedIdea;
    }

    public String getDescription() {
        return implDescription;
    }

    public void setImplementationDescription(String implementationDescription) {
        this.implDescription = implementationDescription;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public String getImplDescription() {
        return implDescription;
    }

    public void setImplDescription(String implDescription) {
        this.implDescription = implDescription;
    }
}



