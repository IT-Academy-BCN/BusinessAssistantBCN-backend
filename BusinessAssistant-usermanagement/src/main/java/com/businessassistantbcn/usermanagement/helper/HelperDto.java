package com.businessassistantbcn.usermanagement.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.businessassistantbcn.usermanagement.document.User;
import com.businessassistantbcn.usermanagement.dto.UserCreationDto;
import com.businessassistantbcn.usermanagement.dto.UserDto;
import com.businessassistantbcn.usermanagement.document.Role;

@Component
public class HelperDto {
	
	//No se utilizar porque no se almacenan usuarios sin password.	
	public User convertToUser (UserDto userDto) {
		User user = new User();
		List<Role> roles; 
		
		BeanUtils.copyProperties(userDto, user);
		roles = convertToUserRoles(userDto.getRoles());
		user.setRoles(roles);
		
		return user;
	}
	
	public UserDto convertToDto (User user) {
		UserDto userDto = new UserDto();
		List<String> stringRoles;
			
		BeanUtils.copyProperties(user, userDto);
		stringRoles = convertToUserDtoRoles(user.getRoles());
		userDto.setRoles(stringRoles);
		
		return userDto;
	}
	
	public User convertToUserFromCreationDto (UserCreationDto userCreationDto) {
		User user = new User();
		List<Role> roles = new ArrayList();
		
		BeanUtils.copyProperties(userCreationDto, user);
		
		//User role and UUID given to all new users.
		user.setUuid(UUID.randomUUID().toString());
		roles.add(Role.USER);
		user.setRoles(roles);
		
		return user;
	}
	
	//Convert from string to roles. 
	List<Role> convertToUserRoles(List<String> stringRoles) {
		List<Role> roles = new ArrayList();	
		
		for (String s : stringRoles) {
			if (s.equalsIgnoreCase("USER")) {
				roles.add(Role.USER);
			} if (s.equalsIgnoreCase("ADMIN")) {
				roles.add(Role.ADMIN);
			}	
		 }
		
		return roles;
	}
	
	//Convert from roles to strings. 
	List<String> convertToUserDtoRoles(List<Role> roles) {
		List<String> stringRoles = new ArrayList();
		
		for (Role r : roles) {
			if (r.USER.name().equalsIgnoreCase("USER")) {
				stringRoles.add(r.USER.name());
			} if (r.USER.name().equalsIgnoreCase("ADMIN")) {
				stringRoles.add(r.ADMIN.name());
			}	
		 }

		return stringRoles;
	}

}
