package com.businessassistantbcn.usermanagement.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.businessassistantbcn.usermanagement.model.UserManagement;
import com.businessassistantbcn.usermanagement.repository.UserManagementRepository;
import com.init.JocDausM.Model.Jogador;



@Service
public class UserManagementService {

	@Autowired
	UserManagementRepository userManagementRepository;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	//1 Llistar users: retorna la llista de users
	public List<UserManagement> llistaUserManagement(){
		List<UserManagement> users = userManagementRepository.findAll();
		return users;
	}
	
	// 2 POST: /user : crea un user
	public UserManagement creaJogador(UserManagement userManagement) {
		userManagement.setUuid(sequenceGeneratorService.generateSequence(UserManagement.SEQUENCE_NAME));
		userManagementRepository.save(userManagement);
		return userManagement;
	}
	
	//3 Post:/updateuser  :update user
	public UserManagement UpdateUserManagement(UserManagement userManagement) {
		
		Optional<UserManagement> optionalUserManagement = userManagementRepository.findById(userManagement.getUuid());
		
		if (optionalUserManagement.isPresent()) {
			UserManagement updateUserManagement = optionalUserManagement.get();
			updateUserManagement.setEmail(userManagement.getEmail());
			updateUserManagement.setPassword(userManagement.getPassword());
			updateUserManagement.setRole(userManagement.getRole());
			userManagementRepository.save(updateUserManagement);
			return updateUserManagement;

		}else {
			UserManagement updateUserManagement= optionalUserManagement.get();
			return updateUserManagement;
		}
	}
	
	//4 Delete:/user delete user
	public void deleteUser(long uuid) {
Optional<UserManagement> optionalUserManagement = userManagementRepository.findById(uuid);
		
		if (optionalUserManagement.isPresent()) {
			UserManagement userManagement=optionalUserManagement.get();
			userManagementRepository.delete(userManagement);
		}
	}
}
