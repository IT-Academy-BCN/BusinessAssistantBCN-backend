package com.businessassistantbcn.usermanagement.service;

import com.businessassistantbcn.usermanagement.dto.IdOnly;
import com.businessassistantbcn.usermanagement.dto.input.UserEmailDto;
import com.businessassistantbcn.usermanagement.dto.output.UserDto;

import reactor.core.publisher.Mono;

public interface IUserManagementService {

    Mono<?> addUser(UserEmailDto userEmailDto);

    Mono<UserDto> getUserById(IdOnly idOnly);

    Mono<UserDto> getUserByEmail(UserEmailDto userEmailDto);

}
