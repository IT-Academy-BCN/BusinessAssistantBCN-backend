package com.businessassistantbcn.usermanagement.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import com.businessassistantbcn.usermanagement.document.User;
import com.businessassistantbcn.usermanagement.dto.UserCreationDto;
import com.businessassistantbcn.usermanagement.dto.UserDto;
import com.businessassistantbcn.usermanagement.helper.HelperDto;
import com.businessassistantbcn.usermanagement.repository.IUserManagementRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Component
public class UserManagementService implements IUserManagementService {

    @Autowired
    IUserManagementRepository userRepository;

    @Autowired 
    HelperDto helperDto;

    public User addUser(User user) {
       return userRepository.save(user);
    }

    public Optional<User> getUserByUuid(String uuid) {
        return userRepository.findByUuid(uuid);   	
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);    
    }

    public User convertToUser(UserDto userDto) {
        return helperDto.convertToUser(userDto);
    }
    
    public User convertToUserFromCreationDto(UserCreationDto userCreationDto){
        return helperDto.convertToUserFromCreationDto(userCreationDto);
    } 

    public UserDto convertToDto(User user) {
        return helperDto.convertToDto(user);

    }


}