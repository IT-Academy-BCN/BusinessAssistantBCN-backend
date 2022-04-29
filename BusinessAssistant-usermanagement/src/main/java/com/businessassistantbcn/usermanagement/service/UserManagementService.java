package com.businessassistantbcn.usermanagement.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import com.businessassistantbcn.usermanagement.document.User;
import com.businessassistantbcn.usermanagement.dto.UserEmailDto;
import com.businessassistantbcn.usermanagement.dto.UserDto;
import com.businessassistantbcn.usermanagement.helper.DtoHelper;
import com.businessassistantbcn.usermanagement.repository.UserManagementRepository;

import lombok.extern.slf4j.Slf4j;


@Service
public class UserManagementService implements IUserManagementService {

    @Autowired
    UserManagementRepository userRepository;


    public Mono<UserDto> addUser(Mono<UserEmailDto> userEmailDto) { 	
    Mono <User> user = userEmailDto.map(DtoHelper::convertToUserFromEmailDto);
    
           user.flatMap(userRepository::save).subscribe();
       
           return user.map(DtoHelper::convertToDto);
    	
    }

    @Override
    public Mono<UserDto> getUserByUuid(String uuid) {
        return userRepository.findByUuid(uuid).map(DtoHelper::convertToDto);
    }

    public Mono<UserDto> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(DtoHelper::convertToDto);
    }
}