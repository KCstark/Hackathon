package com.capstone.hackathon.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "UserID")
	private int uId;
	@Column(name = "Name")
	private String name;
	@Column(name = "Email", unique = true)
	private String email;
	@Column(name = "Password")
	private String password;
	@Column(name = "Role")
	private String role;// 1.Participant, 2.TeamMember, 3.Panelist, 4.Judge
	// @Column(name = "PhoneNo", unique = true)
	// private int phone;
	
	@OneToOne(mappedBy="user", cascade=CascadeType.ALL)
	private Panelist panelist;

	@OneToOne(mappedBy="user", cascade=CascadeType.ALL)
	private Judges judges;

	@OneToOne(mappedBy="user", cascade=CascadeType.ALL)
	private RegUsers regUsers;
	
	@Override
	public String toString() {
		return "User [uId=" + uId + ", name=" + name + ", email=" + email + ", password=" + password + ", panelist="
				+ panelist.getPanelistId() + ", judges=" + judges.getJudgeId() + ", regUsers=" + regUsers.getIdea().getTitle() + ", role=" + role + "]";
	}

	public void setuId(int uId) {
		this.uId = uId;
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

	public void setPanelist(Panelist panelist) {
		this.panelist = panelist;
	}

	public void setJudges(Judges judges) {
		this.judges = judges;
	}

	public void setRegUsers(RegUsers regUsers) {
		this.regUsers = regUsers;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getuId() {
		return uId;
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

	public Panelist getPanelist() {
		return panelist;
	}

	public Judges getJudges() {
		return judges;
	}

	public RegUsers getRegUsers() {
		return regUsers;
	}

	public String getRole() {
		return role;
	}

	
}
