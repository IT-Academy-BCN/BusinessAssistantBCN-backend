package com.businessassistantbcn.usermanagement.service;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

import com.businessassistantbcn.usermanagement.dto.UserUuidDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.reactivestreams.Publisher;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.businessassistantbcn.usermanagement.document.Role;
import com.businessassistantbcn.usermanagement.document.User;
import com.businessassistantbcn.usermanagement.dto.UserDto;
import com.businessassistantbcn.usermanagement.dto.UserEmailDto;
import com.businessassistantbcn.usermanagement.helper.DtoHelper;
import com.businessassistantbcn.usermanagement.repository.UserManagementRepository;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class UserManagementServiceTest {

    @Mock
    UserManagementRepository repository;

    @InjectMocks
    UserManagementService service;

    User user;
    UserDto userDto;
    UserEmailDto userEmailDto;
    UserUuidDto userUuidDto;

    @Test
    public void test_addUser() {
        List<Role> roles = new ArrayList<Role>();
        roles.add(Role.USER);
        user = new User(UUID. randomUUID().toString(), "user@user.es", "12345", roles);

        Mono<UserEmailDto> userEmDto = Mono.just(new UserEmailDto("user@user.es", "12345"));

        when(repository.save(user)).thenReturn(Mono.just(user));

        Publisher<User> setup = repository.save(user);
        Mono<UserDto> save = service.addUser(userEmDto);
        Publisher<UserDto> composite = Mono.from(setup).then(save);

        StepVerifier.create(composite)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void test_getUserByUuid() {
        List<Role> roles = new ArrayList<Role>();
        roles.add(Role.USER);
        String uuid = UUID.randomUUID().toString();
        String password = "12345";
        user = new User(uuid, "user@user.com", password, roles);
        userUuidDto = new UserUuidDto(uuid, password);

        when(repository.findByUuid(userUuidDto.getUuid())).thenReturn(Mono.just(user));
        when(repository.existsByUuid(userUuidDto.getUuid())).thenReturn(Mono.just(true));
        when(service.getUserByUuid(userUuidDto)).thenReturn(Mono.just(DtoHelper.convertToDto(user)));

        StepVerifier.create(Mono.just(DtoHelper.convertToDto(user)))
                .consumeNextWith(userDto -> {
                    assertEquals(userDto.getUuid(), uuid);
                })
                .verifyComplete();
    }

    @Test
    public void test_getUserByEmail() {
        List<Role> roles = new ArrayList<Role>();
        roles.add(Role.USER);
        String email = "user@user.com";
        user = new User(UUID.randomUUID().toString(), email, "12345", roles);
        userEmailDto = new UserEmailDto(email, "12345");

        when(repository.findByEmail(email)).thenReturn(Mono.just(user));
        when(repository.existsByEmail(userEmailDto.getEmail())).thenReturn(Mono.just(true));
        when(service.getUserByEmail(userEmailDto)).thenReturn(Mono.just(DtoHelper.convertToDto(user)));

        StepVerifier.create(Mono.just(DtoHelper.convertToDto(user)))
                .consumeNextWith(userDto -> {
                    assertEquals(userDto.getEmail(), email);
                })
                .verifyComplete();

    }

}
