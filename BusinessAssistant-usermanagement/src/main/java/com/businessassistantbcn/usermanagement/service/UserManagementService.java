package com.businessassistantbcn.usermanagement.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.businessassistantbcn.usermanagement.dto.User;
import com.businessassistantbcn.usermanagement.repository.UserManagementRepository;



@Service
public class UserManagementService {

	@Autowired
	UserManagementRepository userManagementRepository;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	//1 Llistar users: retorna la llista de users
	public List<User> llistaUserManagement(){
		List<User> users = userManagementRepository.findAll();
		return users;
	}
	
	// 2 POST: /user : crea un user
	public User creaJogador(User userManagement) {
		userManagement.setUuid(sequenceGeneratorService.generateSequence(User.USER_SEQUENCE));
		userManagementRepository.save(userManagement);
		return userManagement;
	}
	
	//3 Post:/updateuser  :update user
	public User UpdateUserManagement(User userManagement) {
		
		Optional<User> optionalUserManagement = userManagementRepository.findById(userManagement.getUuid());
		
		if (optionalUserManagement.isPresent()) {
			User updateUserManagement = optionalUserManagement.get();
			updateUserManagement.setEmail(userManagement.getEmail());
			updateUserManagement.setPassword(userManagement.getPassword());
			updateUserManagement.setRole(userManagement.getRole());
			userManagementRepository.save(updateUserManagement);
			return updateUserManagement;

		}else {
			User updateUserManagement= optionalUserManagement.get();
			return updateUserManagement;
		}
	}
	
	//4 Delete:/user delete user
	public void deleteUser(long uuid) {
Optional<User> optionalUserManagement = userManagementRepository.findById(uuid);
		
		if (optionalUserManagement.isPresent()) {
			User userManagement=optionalUserManagement.get();
			userManagementRepository.delete(userManagement);
		}
	}
}
