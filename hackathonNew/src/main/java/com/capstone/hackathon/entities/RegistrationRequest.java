package com.capstone.hackathon.entities;

import jakarta.persistence.*;

@Entity
public class RegistrationRequest {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reqID")
	private int reqId;
	@Column(name = "Name")
	private String name;
	@Column(name = "Email", unique = true)
	private String email;
	@Column(name = "Password")
	private String password;
	@Column(name = "Role")
	private String role;// 1.Participant, 2.TeamMember, 3.Panelist, 4.Judge

    // Status field: "pending," "approved," or "rejected"
    private String status="Pending";

    public void setReqId(int reqId) {
        this.reqId = reqId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getReqId() {
        return reqId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }

    
}

