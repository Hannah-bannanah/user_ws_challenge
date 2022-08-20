package com.technest.challenge_jmp.service;

import com.technest.challenge_jmp.model.User;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface IUserService {
    List<User> getAll();

    User getUserById(Integer userId);

	List<User> loadSampleUsers();

	int createUser(User newUser);
	
	int updateUser(int userId, User updatedUser);
	
	int partiallyUpdateUser(int userId, User update);

	User deleteById(Integer userId);
}
