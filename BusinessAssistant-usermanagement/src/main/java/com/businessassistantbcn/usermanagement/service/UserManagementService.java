package com.businessassistantbcn.usermanagement.service;

import com.businessassistantbcn.usermanagement.dto.UserUuidDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.businessassistantbcn.usermanagement.document.User;
import com.businessassistantbcn.usermanagement.dto.UserEmailDto;
import com.businessassistantbcn.usermanagement.dto.UserDto;
import com.businessassistantbcn.usermanagement.helper.DtoHelper;
import com.businessassistantbcn.usermanagement.repository.UserManagementRepository;
import java.util.Optional;

@Service
public class UserManagementService implements IUserManagementService {

    @Autowired
    UserManagementRepository userRepository;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); // Strength set as 12;

    private boolean existByEmail(UserEmailDto userEmailDto){

        Optional<Boolean> aBoolean = userRepository.existsByEmail(userEmailDto.getEmail()).blockOptional();

        boolean result;

        if (aBoolean.isPresent()){
            result = aBoolean.get();
        } else {
            result = true;
        }

        return result;

    }

    public Mono<UserDto> addUser(UserEmailDto userEmailDto) {

        Mono<UserDto> response;

        if(existByEmail(userEmailDto)){
            response = Mono.empty();
        }else {
            userEmailDto.setPassword(encoder.encode(userEmailDto.getPassword()));
            response = userRepository.save(DtoHelper.convertToUserFromEmailDto(userEmailDto))
                                    .map(DtoHelper::convertToDto);
        }
        return response;
    }

    @Override
    public Mono<UserDto> getUserByUuid(UserUuidDto userUuidDto) {

        Mono<User> user = userRepository.findByUuid(userUuidDto.getUuid());

        Mono<UserDto> response;

        if(user.blockOptional().isEmpty()){
            response = Mono.empty();
        }else{
            response = user.map(DtoHelper::convertToDto);
        }
        return response;

    }

    public Mono<UserDto> getUserByEmail(UserEmailDto userEmailDto) {

        Mono<User> user = userRepository.findByEmail(userEmailDto.getEmail());
        Mono<UserDto> response;

        if(user.blockOptional().isEmpty()){
            response = Mono.empty();
        }else{
            response = user.map(DtoHelper::convertToDto);
        }
        return response;
    }
}