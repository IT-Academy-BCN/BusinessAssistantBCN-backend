package com.businessassistantbcn.usermanagement.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.businessassistantbcn.usermanagement.dto.input.SingupDto;
import org.springframework.beans.BeanUtils;

import com.businessassistantbcn.usermanagement.document.User;


import com.businessassistantbcn.usermanagement.dto.output.UserDto;
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
		userDto.setPassword(user.getPassword());

		return userDto;
	}

	public static User convertToUserFromEmailDto (SingupDto singupDto) {
		User user = new User();
		List<Role> roles = new ArrayList();

		BeanUtils.copyProperties(singupDto, user);

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
