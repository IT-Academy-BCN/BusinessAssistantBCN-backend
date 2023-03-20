package com.businessassistantbcn.usermanagement.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import com.businessassistantbcn.usermanagement.config.PropertiesConfig;
import com.businessassistantbcn.usermanagement.dto.output.ErrorDto;
import com.businessassistantbcn.usermanagement.dto.input.UserUuidDto;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.businessassistantbcn.usermanagement.document.Role;
import com.businessassistantbcn.usermanagement.document.User;
import com.businessassistantbcn.usermanagement.dto.output.UserDto;
import com.businessassistantbcn.usermanagement.dto.input.UserEmailDto;
import com.businessassistantbcn.usermanagement.repository.UserManagementRepository;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class UserManagementServiceTest {


    @Mock
    UserManagementRepository repository;

    @Mock
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); // Strength set as 12;
    @Mock
    PropertiesConfig propertiesConfig;

    @InjectMocks
    UserManagementService service;

    User user;
    UserEmailDto userEmailDto;
    UserUuidDto userUuidDto;

    @BeforeEach
    void setUp() {
        List<Role> roles = new ArrayList<>();
        String uuid = "cb5f0578-6574-4e9a-977d-fca06c7cb67b"; //UUID.randomUUID().toString();
        roles.add(Role.USER);
        ObjectId id = new ObjectId("63cfbcb31fa50abde8288a07");
        String email = "user@user.es";
        String password = "12345";
        long latestAccess = 1111111111;

        user = new User(id, uuid, email, password, roles, latestAccess);
        userEmailDto = new UserEmailDto(email, password);
        userUuidDto = new UserUuidDto(uuid);

    }

    @Test
    public void test_addExistUser(){
        when(service.limitUsersDbExceeded()).thenReturn(false);
        when(repository.findByEmail(userEmailDto.getEmail())).thenReturn(Mono.just(user));
        when(repository.save(any(User.class)/*user*/)).thenReturn(Mono.empty());

        Mono<UserDto> result = (Mono<UserDto>) service.addUser(userEmailDto);

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    public void test_addNewUser(){
        when(service.limitUsersDbExceeded()).thenReturn(false);
        when(repository.findByEmail(userEmailDto.getEmail())).thenReturn(Mono.empty());
        when(repository.save(any(User.class)/*user*/)).thenReturn(Mono.just(user));
        //when(encoder.encode(userEmailDto.getPassword())).thenReturn("passwordEncoded");

        Mono<UserDto> result = (Mono<UserDto>) service.addUser(userEmailDto);

        StepVerifier.create(result)
                .consumeNextWith(userDto->{
                    assertEquals("cb5f0578-6574-4e9a-977d-fca06c7cb67b"/*user.getUuid()*/, userDto.getUuid());
                })
                .verifyComplete();
    }

    @Test
    public void test_addUserLimitDbExceeded(){
        when(service.limitUsersDbExceeded()).thenReturn(true);
        when(repository.count()).thenReturn(Mono.just(201L));
        when(propertiesConfig.getError()).thenReturn("Users limit on database");

        Mono<ErrorDto> result = (Mono<ErrorDto>) service.addUser(userEmailDto);

        StepVerifier.create(result)
                .consumeNextWith(errorDto->{
                    assertEquals("Users limit on database"/*,propertiesConfig.getErr()*/, errorDto.getMessage());
                })
                .verifyComplete();
    }

    @Test
    public void test_getUserByUuid() {
        when(repository.findByUuid(userUuidDto.getUuid())).thenReturn(Mono.just(user));
        when(repository.save(user)).thenReturn(Mono.just(user));

        Mono<UserDto> user = service.getUserByUuid(userUuidDto);

        StepVerifier.create(user)
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
        when(repository.save(user)).thenReturn(Mono.just(user));

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