package com.businessassistantbcn.usermanagement.service;

import com.businessassistantbcn.usermanagement.dto.EmailOnly;
import com.businessassistantbcn.usermanagement.dto.IdOnly;
import com.businessassistantbcn.usermanagement.dto.input.SingupDto;
import com.businessassistantbcn.usermanagement.dto.output.UserDto;

import reactor.core.publisher.Mono;

public interface IUserManagementService {

    Mono<?> addUser(SingupDto singupDto);

    Mono<UserDto> getUserById(IdOnly idOnly);

    Mono<UserDto> getUserByEmail(EmailOnly emailOnly);

}
