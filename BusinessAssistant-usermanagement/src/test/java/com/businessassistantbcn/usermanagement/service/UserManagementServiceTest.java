package com.businessassistantbcn.usermanagement.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import com.businessassistantbcn.usermanagement.dto.UserUuidDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.businessassistantbcn.usermanagement.document.Role;
import com.businessassistantbcn.usermanagement.document.User;
import com.businessassistantbcn.usermanagement.dto.UserDto;
import com.businessassistantbcn.usermanagement.dto.UserEmailDto;
import com.businessassistantbcn.usermanagement.repository.UserManagementRepository;
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
    UserEmailDto userEmailDto;
    UserUuidDto userUuidDto;

    @BeforeEach
    void setUp() {
        List<Role> roles = new ArrayList<>();
        String uuid = UUID.randomUUID().toString();
        roles.add(Role.USER);
        String email = "user@user.es";
        String password = "12345";

        user = new User(uuid, email, password, roles);
        userEmailDto = new UserEmailDto(email, password);
        userUuidDto = new UserUuidDto(uuid);

    }

    @Test
    public void test_addUser() {

        when(repository.save(any(User.class))).thenReturn(Mono.just(user));
        when(repository.existsByEmail(userEmailDto.getEmail())).thenReturn(Mono.just(false));
        when(encoder.encode(userEmailDto.getPassword())).thenReturn("passwordEncoded");

        Mono<UserDto> save = service.addUser(userEmailDto);

        StepVerifier.create(save)
                .consumeNextWith(userDto -> {
                    assertEquals(userDto.getEmail(), userEmailDto.getEmail());
                })
                .verifyComplete();

    }

    @Test
    public void test_addUserWithEmailInUse() {

        when(repository.save(any(User.class))).thenReturn(Mono.just(user));
        when(repository.existsByEmail(userEmailDto.getEmail())).thenReturn(Mono.just(true));
        when(encoder.encode(userEmailDto.getPassword())).thenReturn("passwordEncoded");

        Mono<UserDto> save = service.addUser(userEmailDto);

        StepVerifier.create(save)
                .verifyComplete();

    }

    @Test
    public void test_donNotExistByEmail() {

        when(repository.save(any(User.class))).thenReturn(Mono.just(user));
        when(repository.existsByEmail(userEmailDto.getEmail())).thenReturn(Mono.empty());
        when(encoder.encode(userEmailDto.getPassword())).thenReturn("passwordEncoded");

        Mono<UserDto> save = service.addUser(userEmailDto);

        StepVerifier.create(save)
                .verifyComplete();

    }

    @Test
    public void test_getUserByUuid() {

        when(repository.findByUuid(userUuidDto.getUuid())).thenReturn(Mono.just(user));

        Mono<UserDto> user1 = service.getUserByUuid(userUuidDto);

        StepVerifier.create(user1)
                .consumeNextWith(userDto -> {
                    assertEquals(userDto.getUuid(), userUuidDto.getUuid());
                })
                .verifyComplete();
    }

    @Test
    public void test_getUserByUuidNotExist() {

        when(repository.findByUuid(userUuidDto.getUuid())).thenReturn(Mono.empty());

        Mono<UserDto> user1 = service.getUserByUuid(userUuidDto);

        StepVerifier.create(user1)
                .verifyComplete();
    }

    @Test
    public void test_getUserByEmail() {
        when(repository.findByEmail(userEmailDto.getEmail())).thenReturn(Mono.just(user));

        Mono<UserDto> user1 = service.getUserByEmail(userEmailDto);

        StepVerifier.create(user1)
                .consumeNextWith(userDto -> {
                    assertEquals(userDto.getEmail(), userEmailDto.getEmail());
                })
                .verifyComplete();

    }

    @Test
    public void test_getUserByEmailNotExist() {
        when(repository.findByEmail(userEmailDto.getEmail())).thenReturn(Mono.empty());

        Mono<UserDto> user1 = service.getUserByEmail(userEmailDto);

        StepVerifier.create(user1)
                .verifyComplete();

    }


}
