package com.capstone.hackathon.iservice;

import java.util.List;

import com.capstone.hackathon.entities.User;

public interface UserIService {

//	User login(User user);
	User createUser(User user);// register

	String updateUserPassword(String email, User updatedUser);

	List<User> getAllUsers();

	User getUserById(int userId);

	String deleteUser(int userId);

	User findByEmail(String email);

}
