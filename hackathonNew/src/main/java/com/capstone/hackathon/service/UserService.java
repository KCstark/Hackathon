package com.capstone.hackathon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.capstone.hackathon.entities.RegistrationRequest;
import com.capstone.hackathon.entities.User;
import com.capstone.hackathon.errorHandling.EmailExistsException;
import com.capstone.hackathon.errorHandling.ResourceNotFoundException;
import com.capstone.hackathon.repo.RegistrationRequestRepository;
import com.capstone.hackathon.repo.UserRepo;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class UserService {

	private final UserRepo ur;

	// @Autowired
	// private BCryptPasswordEncoder bc;

	@Autowired
	public UserService(UserRepo ur) {
		this.ur = ur;
	}

	@Autowired
	private RegistrationRequestRepository rr;

	@Autowired
	private EmailService emailService;
	// 1.Participant,2.TeamMember,3.Panelist,4.Judge

	// register user -done
	// login user- done
	// forget pw()- done
	// update pw()- done
	// get user(id/email)- done
	// updateuser(id/email)- done
	// delete(id/email)- done
	// getAllUser -done

	// (LINK)Register
	public String registerUser(User registrationRequest) throws EmailExistsException {
		// check if the email aredy exists
		if (emailExist(registrationRequest.getEmail())) {
			throw new EmailExistsException(
					"There is an account with that email adress: " + registrationRequest.getEmail());
		}
		// role confirmation from admin
		String role = registrationRequest.getRole();
		if (role != null && (registrationRequest.getRole().equals("Judge")
				|| registrationRequest.getRole().equals("Panelist"))) {

			RegistrationRequest newRequest = new RegistrationRequest();
			newRequest.setPassword(hashPassword(registrationRequest.getPassword()));
			newRequest.setEmail(registrationRequest.getEmail());
			newRequest.setName(registrationRequest.getName());
			newRequest.setRole(role);
			rr.save(newRequest);
			return "Registration request submitted. Waiting for admin approval. You will recieve an email confirmation.";
		}
		String newPw = hashPassword(registrationRequest.getPassword());
		registrationRequest.setPassword(newPw);
		ur.save(registrationRequest);
		return "User registered successfully.";
	}

	// if email already in DB or not
	private boolean emailExist(String email) {
		User u = ur.findByEmail(email);
		if (u != null) {
			return true;
		}
		return false;
	}

	// Encrypting pw
	private String hashPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}


	// (LINK)login
	public String loginUser(User loginRequest) {
		User curr_user = ur.findByEmail(loginRequest.getEmail());
		if (curr_user == null) {
			throw new ResourceNotFoundException(loginRequest.getEmail() + " doesn't exist, please register!");
		}
		if (curr_user.getPassword().equals(hashPassword(loginRequest.getPassword()))) {
			System.out.println("Welcome!");
			return "Welcome " + loginRequest.getName() + ", how are you today? :)";
		} else {
			return "Invalid credentials (Password wrong!)";
		}
	}

	// Get a user by ID
	public User getUserById(int userId) {
		Optional<User> u = ur.findById(userId);
		if (u.isPresent()) {
			return u.get();
		}
		throw new ResourceNotFoundException("This userId dont exits!");
	}

	// Get a user by email
	public User getUserByEmail(String email) {
		if (!emailExist(email))
			throw new ResourceNotFoundException("No such email exist!");
		return ur.findByEmail(email);
	}

	// Get all users
	public List<User> getAllUsers() {
		return ur.findAll();
	}

	// Update a user
	public User updateUser(User updatedUser) {
		if (updatedUser != null) {
			return ur.save(updatedUser);
		} else {
			throw new ResourceNotFoundException("No such user!");
		}

	}

	// Delete a user by ID
	public String deleteUser(int userId) {
		Optional<User> u = ur.findById(userId);
		if (u.isPresent()) {
			ur.deleteById(userId);
			return "Succefully deleted!";
		}
		throw new ResourceNotFoundException("No user for this userId!");
	}

	// ***************************************************************************************
	// */
	// UpdatePw
	public String updatePassword(String email, User request) {
		User user = ur.findByEmail(email);
		if (user == null) {
			// User with the provided email doesn't exist
			throw new ResourceNotFoundException("User not found!!");
		}
		String hashedPassword = hashPassword(request.getPassword());
		user.setPassword(hashedPassword);
		ur.save(user);

		return "Password updated successfully";
	}

	// forgetPw
	public ResponseEntity<String> forgotPassword(String email) {
		if (!emailExist(email)) {
			throw new ResourceNotFoundException("Your email is not in our database please register first!");
		}

		String resetLink = "http://localhost:8081/hackathon/users/update-password**(form page)**";

		try {
			// Send the password reset email
			emailService.sendPasswordResetEmail(email, resetLink);
			return ResponseEntity.ok("Password reset email sent successfully");
		} catch (MailException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to send password reset email");
		}
	}
}
