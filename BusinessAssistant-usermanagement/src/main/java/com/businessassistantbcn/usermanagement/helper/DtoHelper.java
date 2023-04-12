package com.businessassistantbcn.usermanagement.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.businessassistantbcn.usermanagement.dto.GenericResultDto;
import com.businessassistantbcn.usermanagement.dto.SingUpRequest;
import com.businessassistantbcn.usermanagement.dto.UserResponse;
import org.springframework.beans.BeanUtils;

import com.businessassistantbcn.usermanagement.document.User;


import com.businessassistantbcn.usermanagement.dto.UserDto;
import com.businessassistantbcn.usermanagement.document.Role;
import reactor.core.publisher.Mono;

public class DtoHelper {

	public static UserDto convertToUserDtoFromUser(User user) {
		UserDto userDto = new UserDto();
		List<String> stringRoles;

		BeanUtils.copyProperties(user, userDto);
		stringRoles = convertToUserDtoRoles(user.getRoles());
		userDto.setUserRoles(stringRoles);
		userDto.setUserPassword(user.getPassword()); //WARNING: for storage. NEVER serialized

		return userDto;
	}

	public static GenericResultDto<UserResponse> userToGenericResponse(User user){
		UserResponse userResponse = convertToUserDtoFromUser(user);
		return new GenericResultDto<>(userResponse);
	}

	public static User convertToUserFromSingup(SingUpRequest singup) {
		User user = new User();
		List<Role> roles = new ArrayList();

		BeanUtils.copyProperties(singup, user);

		//User role, UUID and latestAcces given to all new users.
		user.setUuid(UUID.randomUUID().toString());
		roles.add(Role.USER);
		user.setRoles(roles);
		user.setLatestAccess(System.currentTimeMillis());

		return user;
	}

	//Convert from string to roles.
	static List<Role> convertToUserRoles(List<String> stringRoles) {
		List<Role> roles = new ArrayList();

		for (String s : stringRoles) {
			if (s.equalsIgnoreCase("USER")) {
				roles.add(Role.USER);
			}else if (s.equalsIgnoreCase("ADMIN")) {
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
			}else if (r.name().equalsIgnoreCase("ADMIN")) {
				stringRoles.add(r.ADMIN.name());
			}
		}

		return stringRoles;
	}



}
