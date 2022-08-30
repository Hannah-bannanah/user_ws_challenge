package com.technest.challenge_jmp.service;

import com.technest.challenge_jmp.model.User;
import com.technest.challenge_jmp.repository.IUserRepository;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for the Users in the technest challenge
 * @author jana.montero
 *
 */
@Service
public class UserServiceImpl implements IUserService {
	
    private IUserRepository userRepository;
    private BCryptPasswordEncoder pwe; 
    
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired public UserServiceImpl(IUserRepository userRepository, BCryptPasswordEncoder pwe) {
        this.userRepository = userRepository;
        this.pwe = pwe;
    }

    /**
     * Gets all users from the database
     * @return the list of users
     */
    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    /**
     * Finds a user by id
     * @param userId the userId of the searched user
     * @return the user object if found, null otherwise
     */
    @Override
    public User getUserById(Integer userId) {
    	User user = userRepository.findById(userId).orElse(null);
        return user;
    }
    
	/**
	 * Create a new user entry
	 * @param newUser the user to be added
	 * @return the userId of the newly created user if successful, 
	 * -1 if a user with the same email already exists,
	 * 0 if there is an error when attempting to save the data
	 */
	@Override
	public int createUser(User newUser) {		
		
		User existingUser = userRepository.findByEmail(newUser.getEmail());
		if (existingUser != null) return -1;
		
    	try {
    		newUser.setPassword(pwe.encode(newUser.getPassword()));
			User user = userRepository.save(newUser);
			return user.getUserId();
		} catch (Exception e) {
    		logger.error("There was an error in the method createUser() in class " + this.getClass().getSimpleName() + ". The trace is: ", e);
    		return 0;
		}
	}
	
	/**
	 * Update an existing user by replace the entire user object
	 * @param userId the userId of the user to be updated
	 * @param updatedUser the user object that will replace the existing one
	 * @return 1 if the update is successful, 0 if the user doesn't exist, -1 if there is an error when attempting the update
	 */
	@Override
	public int updateUser(int userId, User updatedUser) {
		User user = userRepository.findById(userId).orElse(null);
		if (user == null) return 0;
		
		try {
			updatedUser.setUserId(userId);
			updatedUser.setPassword(pwe.encode(updatedUser.getPassword()));
			userRepository.save(updatedUser);
		} catch (Exception e) {
    		logger.error("There was an error in the method updateUser() in class " + this.getClass().getSimpleName() + ". The trace is: ", e);
    		return -1;
		}
		return 1;
	}
	
	/**
	 * Partially update an existing user. Only non-null fields which differ from existing data will be updated
	 * @param userId the userId of the user to be updated
	 * @update a user object where the fields to be updated are the only attributes not set to null
	 * @returns 1 if the update was successful, 0 if the user is not found,
	 * -1 if there is an error when attempting the save
	 */
	@Override
	public int partiallyUpdateUser(int userId, User update) {
		User user = userRepository.findById(userId).orElse(null);
		if (user == null) return 0;
		
		// we check if there are changes in the update object
		boolean isUpdate = false;
		if (StringUtils.isNotEmpty(update.getEmail()) 
				&& !StringUtils.equalsIgnoreCase(update.getEmail(), user.getEmail())) {
			user.setEmail(update.getEmail());
			isUpdate = true;
		}
		
		if (StringUtils.isNotEmpty(update.getFirstName())
				&& !StringUtils.equals(update.getFirstName(), user.getFirstName())) {
			user.setFirstName(update.getFirstName());
			isUpdate = true;
		}
		
		if (StringUtils.isNotEmpty(update.getLastName1())
			&& !StringUtils.equals(update.getLastName1(), user.getLastName1())) {
			user.setLastName1(update.getLastName1());
			isUpdate = true;
		}
		
		if (update.getLastName2() != null // we allow the update of removing a second last name by sending an empty string
				&& !StringUtils.equals(update.getLastName2(), user.getLastName2())) {
			user.setLastName2(update.getLastName2());
			isUpdate = true;
		}
		
		if (StringUtils.isNotEmpty(update.getPassword())
				&& !pwe.matches(update.getPassword(), user.getPassword())) {
			System.out.println("passwords dont match");
			user.setPassword(pwe.encode(update.getPassword()));
			isUpdate = true;
		}
		
		if (isUpdate) {
			try {
				userRepository.save(user);
			} catch (Exception e) {
	    		logger.error("There was an error in the method partiallyUpdateUser() in class " + this.getClass().getSimpleName() + ". The trace is: ", e);
				return -1;
			}
		};
		return 1;
	}

	/**
	 * Deletes an existing user entry
	 * @param userId the userId of the user to be deleted
	 * @return the deleted user object
	 */
	@Override
	public User deleteById(Integer userId) {
		User user = userRepository.findById(userId).orElse(null);
		if (user != null) 
			userRepository.deleteById(userId);
		return user;
	}

	/**
	 * Loads the database with a list of sample users
	 * @return the list of sample users
	 */
	@Override
	public List<User> loadSampleUsers() {
		logger.debug("Started method loadSampleUsers() in class " + this.getClass().getSimpleName());

		List<User> users = new ArrayList<User>();
				
		logger.debug("Clearing existing DB");
		userRepository.deleteAll();
		
		logger.debug("Creating new entities");
		for (int i = 1; i <= 10; i ++) {
			User user = new User();
			user.setFirstName("Name " + i);
			user.setLastName1("First Surname " + i);
			user.setLastName2(Math.random() < 0.5 ? "Second Surname " + i : null);
			user.setEmail(user.getFirstName().trim() + "@email.com");
			user.setPassword(pwe.encode("pwd" + i));
			users.add(user);
		}
		
		logger.debug("Completed method loadSampleUSers() in class " + this.getClass().getSimpleName());
		return (List<User>) userRepository.saveAll(users);
	}


}
