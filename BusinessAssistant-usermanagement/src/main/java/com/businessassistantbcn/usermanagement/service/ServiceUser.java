package com.businessassistantbcn.usermanagement.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.businessassistantbcn.usermanagement.document.User;
import com.businessassistantbcn.usermanagement.repository.RepositoryUser;

@Service
public class ServiceUser {

	@Autowired
	private RepositoryUser userDAO;
	
	//findByEmail
	
	public Optional<User> getUserbyEmail(String email) {
		return  userDAO.findByEmail(email);
	}
	
	//get User
	
	public Optional<User> getUserbyuuid(String uuid) {
		return  userDAO.findByuuid(uuid);
	}
	
	//new User
	
	public User newUser(User user) {
		return userDAO.insert(user);
	}
	//update User
	
	public User updateUser(User user) {
		return userDAO.insert(user);
	}
	
	//delete User
	public void deleteUser(User user) {
		userDAO.delete(user);
	}
	
}
