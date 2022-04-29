package com.businessassistantbcn.usermanagement.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.businessassistantbcn.usermanagement.document.User;
import com.businessassistantbcn.usermanagement.dto.UserEmailDto;

import com.businessassistantbcn.usermanagement.dto.UserDto;
import com.businessassistantbcn.usermanagement.document.Role;

public class DtoHelper {
	
	//No se utilizar porque no se almacenan usuarios sin password.	
	public static User convertToUser (UserDto userDto) {
		User user = new User();
		List<Role> roles; 
		
		BeanUtils.copyProperties(userDto, user);
		roles = convertToUserRoles(userDto.getRoles());
		user.setRoles(roles);
		
		return user;
	}
	
	public static UserDto convertToDto (User user) {
		UserDto userDto = new UserDto();
		List<String> stringRoles;
			
		BeanUtils.copyProperties(user, userDto);
		stringRoles = convertToUserDtoRoles(user.getRoles());
		userDto.setRoles(stringRoles);
		
		return userDto;
	}
	
	public static User convertToUserFromEmailDto (UserEmailDto userEmailDto) {
		User user = new User();
		List<Role> roles = new ArrayList();
		
		BeanUtils.copyProperties(userEmailDto, user);
		
		//User role and UUID given to all new users.
		user.setUuid(UUID.randomUUID().toString());
		roles.add(Role.USER);
		user.setRoles(roles);
		
		return user;
	}

	//Convert from string to roles. 
	static List<Role> convertToUserRoles(List<String> stringRoles) {
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
	static List<String> convertToUserDtoRoles(List<Role> roles) {
		List<String> stringRoles = new ArrayList();
		
		for (Role r : roles) {
			if (r.name().equalsIgnoreCase("USER")) {
				stringRoles.add(r.USER.name());
			} if (r.name().equalsIgnoreCase("ADMIN")) {
				stringRoles.add(r.ADMIN.name());
			}	
		 }

		return stringRoles;
	}

}
