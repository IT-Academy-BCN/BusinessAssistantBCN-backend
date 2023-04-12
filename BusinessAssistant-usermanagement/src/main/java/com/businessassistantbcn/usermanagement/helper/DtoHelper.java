package com.businessassistantbcn.usermanagement.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.businessassistantbcn.usermanagement.dto.output.GenericResultDto;
import com.businessassistantbcn.usermanagement.dto.input.SingUpRequest;
import com.businessassistantbcn.usermanagement.dto.output.UserResponse;

import com.businessassistantbcn.usermanagement.document.User;


import com.businessassistantbcn.usermanagement.dto.io.UserDto;
import com.businessassistantbcn.usermanagement.document.Role;

public class DtoHelper {

	public static UserDto convertUserToUserDto(User user) {
		return UserDto.builder()
				.userId(user.getUuid())
				.userEmail(user.getEmail())
				.userRoles(convertToUserDtoRoles(user.getRoles()))
				.userPassword(user.getPassword()) //WARNING: for storage. NEVER serialized
				.build();
	}

	public static GenericResultDto<UserResponse> convertUserToGenericUserResponse(User user){
		UserResponse userResponse = convertUserToUserDto(user);
		return new GenericResultDto<>(userResponse);
	}

	public static User convertSingupToUser(SingUpRequest singup) {
		User user = new User();
		user.setEmail(singup.getUserEmail());
		user.setPassword(singup.getUserPassword());
		//User role, UUID and latestAcces given to all new users.
		user.setUuid(UUID.randomUUID().toString());
		user.setRoles(List.of(Role.USER));
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
