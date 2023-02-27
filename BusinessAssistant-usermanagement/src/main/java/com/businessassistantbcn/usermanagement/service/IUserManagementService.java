package com.businessassistantbcn.usermanagement.service;

import com.businessassistantbcn.usermanagement.dto.input.UserEmailDto;
import com.businessassistantbcn.usermanagement.dto.output.UserDto;

import com.businessassistantbcn.usermanagement.dto.input.UserUuidDto;
import reactor.core.publisher.Mono;

public interface IUserManagementService {

    Mono<?> addUser(UserEmailDto userEmailDto);

    Mono<UserDto> getUserByUuid(UserUuidDto userUuidDto);

    Mono<UserDto> getUserByEmail(UserEmailDto userEmailDto);

}
