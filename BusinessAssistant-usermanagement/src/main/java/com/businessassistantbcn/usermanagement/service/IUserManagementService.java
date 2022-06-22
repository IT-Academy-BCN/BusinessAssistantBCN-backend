package com.businessassistantbcn.usermanagement.service;

import com.businessassistantbcn.usermanagement.dto.UserEmailDto;
import com.businessassistantbcn.usermanagement.dto.UserDto;

import reactor.core.publisher.Mono;

public interface IUserManagementService {

    public Mono<UserDto> addUser(Mono<UserEmailDto> userEmailDto);

    public Mono<UserDto> getUserByUuid(String uuid);

    public Mono<UserDto> getUserByEmail(UserEmailDto userEmailDto);

}
