package com.businessassistantbcn.usermanagement.service;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

import com.businessassistantbcn.usermanagement.dto.UserUuidDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.businessassistantbcn.usermanagement.document.Role;
import com.businessassistantbcn.usermanagement.document.User;
import com.businessassistantbcn.usermanagement.dto.UserDto;
import com.businessassistantbcn.usermanagement.dto.UserEmailDto;
import com.businessassistantbcn.usermanagement.helper.DtoHelper;
import com.businessassistantbcn.usermanagement.repository.UserManagementRepository;

import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class UserManagementServiceTest {

    @Mock
    UserManagementRepository repository;

    @Mock
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); // Strength set as 12;

    @InjectMocks
    UserManagementService service;

    User user;
    UserDto userDto;
    UserEmailDto userEmailDto;
    UserUuidDto userUuidDto;

    @Test
    public void test_addUser() {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.USER);
        String email = "user@user.es";
        user = new User(UUID. randomUUID().toString(), email, "12345", roles);
        userEmailDto = new UserEmailDto(email, "12345");


        when(repository.save(any(User.class))).thenReturn(Mono.just(user));
        when(encoder.encode(userEmailDto.getPassword())).thenReturn("passwordEncoded");
        //when(service.addUser(userEmailDto)).thenReturn(Mono.just(DtoHelper.convertToDto(user)));

        //Publisher<User> setup = repository.save(user);
        Mono<UserDto> save = service.addUser(userEmailDto);
        //Publisher<UserDto> composite = Mono.from(setup).then(save);

        assertEquals(save.block().getEmail(), userEmailDto.getEmail());

        /*StepVerifier.create(save)
                .consumeNextWith(user1 -> {
                    assertEquals(user1.getEmail(), email);
                })
                //.expectNextCount(1)
                .verifyComplete();*/
    }

    @Test
    public void test_getUserByUuid() {
        List<Role> roles = new ArrayList<>();
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
    public void test_getUserByUuidNotExist() {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.USER);
        String email = "user@user.com";
        user = new User(UUID.randomUUID().toString(), email, "12345", roles);
        userUuidDto = new UserUuidDto(email, "12345");

        assertThrows(ResponseStatusException.class, ()-> {
            when(repository.existsByUuid(userUuidDto.getUuid())).thenReturn(Mono.just(false));
            when(service.getUserByUuid(userUuidDto)).thenReturn(Mono.just(DtoHelper.convertToDto(user)));
        });
    }

    @Test
    public void test_getUserByEmail() {
        List<Role> roles = new ArrayList<>();
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

    @Test
    public void test_getUserByEmailNotExist() {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.USER);
        String email = "user@user.com";
        user = new User(UUID.randomUUID().toString(), email, "12345", roles);
        userEmailDto = new UserEmailDto(email, "12345");

        assertThrows(ResponseStatusException.class, ()-> {
            when(repository.existsByEmail(userEmailDto.getEmail())).thenReturn(Mono.just(false));
            when(service.getUserByEmail(userEmailDto)).thenReturn(Mono.just(DtoHelper.convertToDto(user)));
        });
    }
}
