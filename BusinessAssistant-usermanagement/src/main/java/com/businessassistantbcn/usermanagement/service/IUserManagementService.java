package com.businessassistantbcn.usermanagement.service;

import com.businessassistantbcn.usermanagement.dto.UserDto;
import com.businessassistantbcn.usermanagement.dto.UserEmailDto;
import reactor.core.publisher.Mono;

public interface IUserManagementService {

    public Mono<UserDto> addUser(Mono<UserEmailDto> userEmailDto);

    public Mono<UserDto> getUserByUuid(String uuid);

    public Mono<UserDto> getUserByEmail(String email);

}