package com.capstone.hackathon.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.hackathon.repo.JudgesRepo;
import com.capstone.hackathon.repo.PanelistRepo;
import com.capstone.hackathon.repo.RegistrationRequestRepository;
import com.capstone.hackathon.repo.UserRepo;
import com.capstone.hackathon.service.EmailService;
import com.capstone.hackathon.service.UserService;
import com.capstone.hackathon.entities.Judges;
import com.capstone.hackathon.entities.Panelist;
import com.capstone.hackathon.entities.RegistrationRequest;
import com.capstone.hackathon.entities.User;
import com.capstone.hackathon.errorHandling.ResourceNotFoundException;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RegistrationRequestRepository registrationRequestRepository;
    @Autowired
    private UserRepo ur;
    @Autowired
    EmailService es;
    @Autowired
    private UserService us;
    @Autowired
    private JudgesRepo jr;
    @Autowired
    private PanelistRepo pr;

    @GetMapping("/get")
	public ResponseEntity<List<User>> getUserProfile() {
		List<User> user = ur.findAll();
		if (user.size() > 0) {
			return ResponseEntity.ok(user);
		} else {
			System.out.println("No User yet!");
			return ResponseEntity.notFound().build();
		}
	}

    @GetMapping("/requests")
    public List<RegistrationRequest> getPendingRequests() {
        // Retrieve all pending registration requests
        return registrationRequestRepository.findByStatus("Pending");
    }

    @PostMapping("/approve/{requestId}")
    public ResponseEntity<String> approveRequest(@PathVariable int requestId) {
        // Approve the registration request
        RegistrationRequest request = registrationRequestRepository.findById(requestId).orElse(null);
        if (request == null) {
            return ResponseEntity.notFound().build();
        }

        // Update the status to "approved"
        request.setStatus("approved");
        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        ur.save(user);
        if(request.getRole().equals("Judge")){
            Judges j= new Judges();
            j.setName(user.getName());
            j.setUser(user);
            jr.save(j);
        }else{
            Panelist p= new Panelist();
            p.setUser(user);
            pr.save(p);
        }
        // registrationRequestRepository.save(request);
        registrationRequestRepository.deleteById(requestId);

        return ResponseEntity.ok("Request Accepted successfully");


        // Notify the user that their request is approved
        // try {
        //     // Send email
        //     es.confirmationEmail(user.getEmail(), user.getRole(), "Approved");
        //     return ResponseEntity.ok("Email sent successfully to: " + user.getEmail());
        // } catch (MailException e) {
        //     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        //             .body("Failed to send email");
        // }
    }

    @PostMapping("/reject/{requestId}")
    public ResponseEntity<String> rejectRequest(@PathVariable int requestId) {
        // Reject the registration request
        RegistrationRequest request = registrationRequestRepository.findById(requestId).orElse(null);
        if (request == null) {
            return ResponseEntity.notFound().build();
        }

        // Update the status to "rejected"
        request.setStatus("rejected");
        String mail = request.getEmail();
        String role = request.getRole();
        // registrationRequestRepository.save(request);
        registrationRequestRepository.deleteById(requestId);
        return ResponseEntity.ok("Request rejected successfully");

        // Notify the user that their request is approved
        // try {
        //     // Send email
        //     es.confirmationEmail(mail, role, "rejected");
        //     return ResponseEntity.ok("Email sent successfully to: " + mail);
        // } catch (MailException e) {
        //     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        //             .body("Failed to send email");
        // }
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteAll(){
        ur.deleteAll();
        return ResponseEntity.ok("All users deleted succefully!");
    }

    @DeleteMapping("/allRequests")
    public ResponseEntity<String> deleteAllReq(){
        registrationRequestRepository.deleteAll();
        return ResponseEntity.ok("All requests deleted succefully!");
    }

    // Delete a user by ID
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) {
        try {
            String result = us.deleteUser(userId);
            return ResponseEntity.ok(result);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build(); // User not found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user");
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable int userId) {
        try {
            User user = us.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build(); // User not found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getByEmail")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        try {
            User user = us.getUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build(); // User not found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUserProfile(@PathVariable int userId, @RequestBody User updatedUser) {
        Optional<User> u = ur.findById(userId);
        try {
            if (u.isPresent()) {
                updatedUser.setuId(userId);
                ur.save(updatedUser);
                return ResponseEntity.ok("User profile updated successfully.");
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // User not found
        }
        return null;

    }

}
