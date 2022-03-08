package com.businessassistantbcn.usermanagement.service;

import java.util.Optional;
import com.businessassistantbcn.usermanagement.document.User;
import com.businessassistantbcn.usermanagement.dto.UserCreationDto;
import com.businessassistantbcn.usermanagement.dto.UserDto;

public interface IUserManagementService {

	public User addUser(User user);
	
	public Optional<User> getUserByUuid(String uuid);

    public Optional<User> getUserByEmail(String email);
          
    public User convertToUser(UserDto userDto);  
    
    public User convertToUserFromCreationDto(UserCreationDto userDto); 

    public UserDto convertToDto(User user);
  
}
