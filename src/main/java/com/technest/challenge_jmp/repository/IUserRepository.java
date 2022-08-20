package com.technest.challenge_jmp.repository;

import com.technest.challenge_jmp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUserRepository extends JpaRepository<User, Integer> {
	
	@Query("select u from User u where u.email = ?1")
	public User findByEmail(String email);
}
