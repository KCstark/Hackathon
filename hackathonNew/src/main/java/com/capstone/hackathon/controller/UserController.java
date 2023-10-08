package com.capstone.hackathon.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.hackathon.entities.User;
import com.capstone.hackathon.repo.UserRepo;
import com.capstone.hackathon.service.UserService;

@RestController
@RequestMapping("/hackathon/users")
public class UserController {
// 1.participant(leader),2.teamMem,3.panelist,4.judge
	@Autowired
	private UserRepo ur;// change it with user service later
	@Autowired
	private UserService us;

	// secure it
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User registrationRequest) {
		return us.registerUser(registrationRequest);
	}

	// secure it 
	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody User loginRequest) {
		return us.loginUser(loginRequest);
	}

	@GetMapping("/admin/get")
	public ResponseEntity<List<User>> getUserProfile() {
		
		List<User> user= ur.findAll();
		if (user.size()>0) {
			return ResponseEntity.ok(user);
		} else {
			System.out.println("No User yet!");
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/admin/{userId}")
	public ResponseEntity<User> getUserProfile(@PathVariable int userId) {
		Optional<User> user = ur.findById(userId);
		if (user.isPresent()) {
			return ResponseEntity.ok(user.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/admin/{userId}")
	public ResponseEntity<String> updateUserProfile(@PathVariable int userId, @RequestBody User updatedUser) {
		updatedUser.setuId(userId);
		ur.save(updatedUser);
		return ResponseEntity.ok("User profile updated successfully.");
	}

}
