package com.capstone.hackathon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.hackathon.entities.User;
import com.capstone.hackathon.errorHandling.EmailExistsException;
import com.capstone.hackathon.errorHandling.NotAllowedException;
import com.capstone.hackathon.errorHandling.ResourceNotFoundException;
import com.capstone.hackathon.service.UserService;

@RestController
@RequestMapping("/hackathon/users")
public class UserController {
	// 1.participant(leader),2.teamMem,3.panelist,4.judge

	@Autowired
	private UserService us;

	// secure it
	@PostMapping("/register")
	 public ResponseEntity<String> registerUser(@RequestBody User registrationRequest) {
        try {
            String message = us.registerUser(registrationRequest);
            return ResponseEntity.ok(message);
        } catch (EmailExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (MailException e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to Send Mail "+ e.getMessage());
		}catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register user "+ ex.getMessage());
        }
    }

	// secure it
	@PostMapping("/login")
    public User loginUser(@RequestBody User loginRequest) {
        try {
            User loggedInUser = us.loginUser(loginRequest);
            return loggedInUser;
        } catch (ResourceNotFoundException e) {
            throw e; // Rethrow ResourceNotFoundException to return a 404 status code
        } catch (NotAllowedException e) {
            throw e; // Rethrow NotAllowedException to return a 403 status code
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred: " + e.getMessage());
        }
    }

	// *******************************************************************************
	@PutMapping("/update-password")
	public ResponseEntity<String> updatePassword(@RequestParam String email, @RequestBody User request) {
		try {
			String message = us.updatePassword(email, request);
			return ResponseEntity.ok(message);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.notFound().build(); // User not found
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update password");
		}
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<String> forgotPassword(@RequestParam String email) {
		try {
			us.forgotPassword(email);
			return ResponseEntity.ok("Password reset email sent successfully");
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (MailException e) {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
					.body("Failed to send password reset email");
		}
	}

	 @DeleteMapping("/{UserId}")
    public ResponseEntity<String> deleteAll(@PathVariable int UserId){
        us.deleteUser(UserId);
        return ResponseEntity.ok("this user deleted succefully!");
    }


}
