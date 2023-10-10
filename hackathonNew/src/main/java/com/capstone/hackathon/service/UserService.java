package com.capstone.hackathon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.capstone.hackathon.entities.User;
import com.capstone.hackathon.errorHandling.EmailExistsException;
import com.capstone.hackathon.errorHandling.ResourceNotFoundException;
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
	// 1.Participant,2.TeamMember,3.Panelist,4.Judge

	//register user -done
	//login user
	//update pw
	//get user(id/email)- 
	//updateuser(id/email)
	//delete(id/email)- 
	//getAllUser -done
	//

    //(LINK)Register
    public ResponseEntity<String> registerUser(User registrationRequest) throws EmailExistsException {
		//check if the email aredy exists
		if (emailExist(registrationRequest.getEmail())) {
			throw new EmailExistsException("There is an account with that email adress: " + registrationRequest.getEmail());
		}
		// role confirmation from admin
		String role=registrationRequest.getRole();
		
		if(role!=null&&(registrationRequest.getRole().equals("Judge")||registrationRequest.getRole().equals("Panelist"))){
			 if(AdminPermission(registrationRequest.getEmail())){
				String newPw= hashPassword(registrationRequest.getPassword());
				registrationRequest.setPassword(newPw);
				ur.save(registrationRequest);
				return ResponseEntity.ok("User registered successfully.");
			 }
		}
		String newPw= hashPassword(registrationRequest.getPassword());
		registrationRequest.setPassword(newPw);
		ur.save(registrationRequest);
		return ResponseEntity.ok("User registered successfully.");
	}

	//if email already in DB or not
	private boolean emailExist(String email) {
		User u= ur.findByEmail(email);
		if(u!=null){
			return true;
		}
		return false;
	}

	//Encrypting pw
	private String hashPassword(String password){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}

	//(LINK)admin permission for judge & panelist reg req 
	private boolean AdminPermission(String email) {
		System.out.println("Email requesting to be Judge/Panelist: "+email);
		boolean ans=false;
		System.out.println("Allow? true or false");
		try (Scanner sc = new Scanner(System.in)) {
			ans=sc.nextBoolean();
		}
		System.out.println("Working, check status");
		return ans;
	}

	//(LINK)login
	public ResponseEntity<String> loginUser(User loginRequest) {
		User curr_user = ur.findByEmail(loginRequest.getEmail());
		if(curr_user==null){
			throw new ResourceNotFoundException(loginRequest.getEmail()+" doesn't exist, please register!");
		}
		if (curr_user.getPassword().equals(hashPassword(loginRequest.getPassword()))) {
			System.out.println("Welcome!");
			return ResponseEntity.ok(loginRequest.toString());
		} else {
			return ResponseEntity.badRequest().body("Invalid credentials (PW)");
		}
	}

	// Get a user by ID
    public Optional<User> getUserById(int userId){
		return ur.findById(userId);
    }

    // Get all users
    public List<User> getAllUsers() {
        return ur.findAll();
    }

    // Update a user
    public User updateUser(User updatedUser) {
        return ur.save(updatedUser);
    }

    // Delete a user by ID
    public void deleteUser(int userId) {
        ur.deleteById(userId);
    }
}
