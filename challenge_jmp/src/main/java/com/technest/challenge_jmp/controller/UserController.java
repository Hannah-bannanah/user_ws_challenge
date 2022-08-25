package com.technest.challenge_jmp.controller;

import com.technest.challenge_jmp.model.User;
import com.technest.challenge_jmp.service.IUserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for the users endpoint in the technest challenge
 * @author jana.montero
 *
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private IUserService userService;
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
        
    @Autowired public UserController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * Get a list of all users in DB
     * @return the list of users
     */
    @GetMapping()
    public List<User> getAllUsers() {
    	logger.debug("Received GET request at endpoint '/users'");  	
    	return userService.getAll();

    }

    /**
     * Searches for a user by userId
     * @param userId the userId of the searched user
     * @return the user object if found
     */
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Integer userId){
    	logger.debug("Received GET request at endpoint '/users/" + userId + "'");
    	User user = userService.getUserById(userId);
        return new ResponseEntity<User>(user, user != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /**
     * Creates a new user account
     * @param newUser the user data of the new account
     * @return the created user if the operation was successful
     */
    @PostMapping(consumes= MediaType.APPLICATION_JSON_VALUE,
            produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@RequestBody User newUser){
    	logger.debug("Received POST request at endpoint '/users'. Received user " + newUser);

    	User user = null;
    	HttpStatus httpStatus;
    	int userId = userService.createUser(newUser);
    	switch (userId) {
    	case -1:
    		httpStatus = HttpStatus.CONFLICT;
    		break;
    	case 0:
    		httpStatus = HttpStatus.BAD_REQUEST;
    		break;
    	default:
    		user = userService.getUserById(userId);
    		httpStatus = user != null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
    	}
        return new ResponseEntity<User>(user, httpStatus);
    }
    
    /**
     * Updates all data, except for the userId, for an existing user
     * @param userId the userId of the user to be updated
     * @param updatedUser all user data
     */
    @PutMapping(value="/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateUser(@PathVariable int userId, @RequestBody User updatedUser) {
    	logger.debug("Received PUT request at endpoint '/users'. Received user " + updatedUser);

		int userUpdated = userService.updateUser(userId, updatedUser);
		
		HttpStatus httpStatus;
		switch(userUpdated) {
		case 1:
			httpStatus = HttpStatus.NO_CONTENT;
			break;
		case 0:
			httpStatus = HttpStatus.NOT_FOUND;
			break;
		default:
			httpStatus = HttpStatus.BAD_REQUEST;
		}
    	
    	return new ResponseEntity<Void>(httpStatus);
    }
    
    /**
     * Partially updates an existing user. Only the provided fields will be updated
     * @param userId the userId of the user to be updated
     * @param update a user object where only the fields to be updated contain data
     * @return the user object after the update has taken place
     */
    @PatchMapping(value="/{userId}", consumes= MediaType.APPLICATION_JSON_VALUE,
    		produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> partiallyUpdateUser(@PathVariable int userId, @RequestBody User update) {
    	logger.debug("Received PATCH request at endpoint '/users'. Received update " + update);
    	
		int isUserUpdated = userService.partiallyUpdateUser(userId, update);
		User updatedUser = null;

		HttpStatus httpStatus;
		
		switch(isUserUpdated) {
		case 1:
			httpStatus = HttpStatus.OK;
			updatedUser = userService.getUserById(userId);
			break;
		case 0:
			httpStatus = HttpStatus.NOT_FOUND;
			break;
		default:
			httpStatus = HttpStatus.BAD_REQUEST;
		}
    	return new ResponseEntity<User>(updatedUser, httpStatus);
    }
    
    /**
     * Delete a user account
     * @param userId the userId of the user to be deleted
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable Integer userId) {
    	logger.debug("Received DELETE request at endpoint '/users/" + userId + "'");

    	User user = userService.deleteById(userId);
    	return new ResponseEntity<User>(user, user != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
    
    /**
     * Clears the database an reinitializes it with 10 user objects
     * @return the list of all users in the DB after the initialization
     */
    @GetMapping("/initialize")
    public ResponseEntity<List<User>> initializeDatabase(){
    	logger.debug("Started method initializeDatabase() in class " + this.getClass().getSimpleName());

    	List<User> users = userService.loadSampleUsers();
    	
    	logger.debug("Completed method initializeDatabase() in class " + this.getClass().getSimpleName());
    	return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }
}
