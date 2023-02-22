package com.businessassistantbcn.usermanagement.service;

import com.businessassistantbcn.usermanagement.dto.UserEmailDto;
import com.businessassistantbcn.usermanagement.dto.UserDto;

import com.businessassistantbcn.usermanagement.dto.UserUuidDto;
import reactor.core.publisher.Mono;

public interface IUserManagementService {

    Mono<?> addUser(UserEmailDto userEmailDto);

    Mono<UserDto> getUserByUuid(UserUuidDto userUuidDto);

    Mono<UserDto> getUserByEmail(UserEmailDto userEmailDto);

}
