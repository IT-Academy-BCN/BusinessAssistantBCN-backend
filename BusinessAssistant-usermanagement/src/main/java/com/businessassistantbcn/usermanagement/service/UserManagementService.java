package com.businessassistantbcn.usermanagement.service;

import com.businessassistantbcn.usermanagement.dto.UserUuidDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import com.businessassistantbcn.usermanagement.document.User;
import com.businessassistantbcn.usermanagement.dto.UserEmailDto;
import com.businessassistantbcn.usermanagement.dto.UserDto;
import com.businessassistantbcn.usermanagement.helper.DtoHelper;
import com.businessassistantbcn.usermanagement.repository.UserManagementRepository;

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
    public Mono<UserDto> getUserByUuid(UserUuidDto userUuidDto) {
        if(userRepository.existsByUuid(userUuidDto.getUuid()).block()){
            if(userRepository.existsByPassword(userUuidDto.getPassword()).block()){
                return userRepository.findByUuid(userUuidDto.getUuid()).map(DtoHelper::convertToDto);
            }else{
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    public Mono<UserDto> getUserByEmail(UserEmailDto userEmailDto) {
        if (userRepository.existsByEmail(userEmailDto.getEmail()).block()){
                if(userRepository.existsByPassword(userEmailDto.getPassword()).block()){
                    return userRepository.findByEmail(userEmailDto.getEmail()).map(DtoHelper::convertToDto);
                }else{
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
                }
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public Mono<UserDto> getUser(String email, String password) {
        return userRepository.findByEmail(email).map(DtoHelper::convertToDto);
    }

}