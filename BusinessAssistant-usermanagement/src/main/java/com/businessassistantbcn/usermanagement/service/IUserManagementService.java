package com.businessassistantbcn.usermanagement.service;

import com.businessassistantbcn.usermanagement.dto.EmailOnly;
import com.businessassistantbcn.usermanagement.dto.IdOnly;
import com.businessassistantbcn.usermanagement.dto.SingUpRequest;
import com.businessassistantbcn.usermanagement.dto.UserDto;

import reactor.core.publisher.Mono;

public interface IUserManagementService {

    Mono<?> addUser(SingUpRequest singup);

    Mono<UserDto> getUserById(IdOnly idOnly);

    Mono<UserDto> getUserByEmail(EmailOnly emailOnly);

}
