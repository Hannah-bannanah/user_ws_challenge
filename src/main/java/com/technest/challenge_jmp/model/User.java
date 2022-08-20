package com.technest.challenge_jmp.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

import lombok.Data;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public @Data class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int userId;

	private String email;

	private String firstName;

	@Column(name = "last_name_1")
	private String lastName1;
	
	@Column(name="last_name_2")
	private String lastName2;

	private String password;

	public User() {
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return userId == other.userId;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", email=" + email + ", firstName=" + firstName + ", lastName1=" + lastName1
				+ ", lastName2=" + lastName2 + ", password=" + password + "]";
	}
	
}