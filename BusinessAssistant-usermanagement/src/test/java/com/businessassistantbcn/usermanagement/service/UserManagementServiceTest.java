package com.businessassistantbcn.usermanagement.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import com.businessassistantbcn.usermanagement.config.PropertiesConfig;
import com.businessassistantbcn.usermanagement.dto.input.EmailOnly;
import com.businessassistantbcn.usermanagement.dto.input.IdOnly;
import com.businessassistantbcn.usermanagement.dto.input.SingUpRequest;
import com.businessassistantbcn.usermanagement.dto.output.GenericResultDto;
import com.businessassistantbcn.usermanagement.dto.output.ErrorDto;
import com.businessassistantbcn.usermanagement.dto.output.UserResponse;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.businessassistantbcn.usermanagement.document.Role;
import com.businessassistantbcn.usermanagement.document.User;
import com.businessassistantbcn.usermanagement.dto.io.UserDto;
import com.businessassistantbcn.usermanagement.repository.UserManagementRepository;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
//@PropertySource("classpath:mapping-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@Log4j2
public class UserManagementServiceTest {

    @Autowired
    IUserManagementService service;

    @MockBean
    UserManagementRepository repository;

    @MockBean
    PropertiesConfig propertiesConfig;

    private User user;

    private UserDto userDto;

    private int maxUsers;

    private String errorLimitMsg;


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

        userDto = UserDto.builder()
                .userId(uuid)
                .userEmail(email)
                .userRoles(roles.stream()
                        .map(role -> role.toString()).toList())
                .userPassword(password)
                .build();

        when(propertiesConfig.getEnabled()).thenReturn(true);
        maxUsers = 10;
        when(propertiesConfig.getMaxusers()).thenReturn(maxUsers);
        errorLimitMsg = "Users limit on database";
        when(propertiesConfig.getErrorLimitDb()).thenReturn(errorLimitMsg);
    }

    @Test
    void addNewUserTest(){
        mockNumUsersAllowed(1);
        mockFindByEmailResponse(Mono.empty());
        mockSaveResponse(Mono.just(user));

        Mono<GenericResultDto<?>> genericResultMono = doAddUser();
        StepVerifier.create(genericResultMono)
                .assertNext(result -> {
                    assertInstanceOf(UserResponse.class,result.getResults()[0]);
                    UserResponse userResponse = (UserResponse) result.getResults()[0];
                    assertNotNull(userResponse.getUserId());
                    assertEquals(userDto.getUserEmail(), userResponse.getUserEmail());
                    assertEquals(userDto.getUserRoles(), userResponse.getUserRoles());
                })
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void singupEmailExistTest(){
        mockNumUsersAllowed(1);
        mockFindByEmailResponse(Mono.just(user));
        mockSaveResponse(Mono.just(user));
        String errorMsg = "already exist other user whit email: "+userDto.getUserEmail();
        assertOneError(doAddUser(), errorMsg);
    }

    @Test
    void addUserLimitDbExceededTest(){
        mockNumUsersAllowed(0);
        mockFindByEmailResponse(Mono.empty());
        mockSaveResponse(Mono.just(user));
        assertOneError(doAddUser(), errorLimitMsg);
    }

    private Mono<GenericResultDto<?>> doAddUser(){
        SingUpRequest singup = userDto;
        return service.addUser(singup);
    }

    private void assertOneError (Mono<GenericResultDto<?>> genericResultMono, String message){
        StepVerifier.create(genericResultMono)
                .assertNext(result -> {
                    ErrorDto errorDto = (ErrorDto) result.getResults()[0];
                    assertEquals(message, errorDto.getErrorMessage());
                })
                .expectNextCount(0)
                .verifyComplete();
    }



    @Test
    void getUserByIdTest() {
        mockFoundByIdlResponse(Mono.just(user));
        mockSaveResponse(Mono.just(user));

        Mono<GenericResultDto<UserResponse>> genericResultMono = doFindById();
        StepVerifier.create(genericResultMono)
                .assertNext(result -> {
                    UserResponse userResponse = result.getResults()[0];
                    assertEquals(userDto.getUserId(), userResponse.getUserId());
                    assertNotNull(userResponse.getUserEmail(), "user email is null");
                    assertNotNull(userResponse.getUserRoles(), "user roles is null");
                })
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void getUserByUuidNotExistTest() {
        mockFoundByIdlResponse(Mono.empty());
        mockSaveResponse(Mono.empty());
        assertCompletedEmpty(doFindById());
    }

    private Mono<GenericResultDto<UserResponse>> doFindById(){
        IdOnly idOnly = userDto;
        return service.getUserById(idOnly);
    }

    @Test
    void getUserByEmailTest() {
        mockSaveResponse(Mono.just(user));
        mockFindByEmailResponse(Mono.just(user));

        Mono<GenericResultDto<UserResponse>> genericResultMono = doFindByEmail();
        StepVerifier.create(genericResultMono)
                .assertNext(result -> {
                    UserResponse userResponse = result.getResults()[0];
                    assertEquals(userDto.getUserEmail(), userResponse.getUserEmail());
                    assertNotNull(userResponse.getUserId(), "user id is null");
                    assertNotNull(userResponse.getUserRoles(), "user roles is null");
                })
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void getUserByEmailNotExistTest() {
        mockSaveResponse(Mono.empty());
        mockFindByEmailResponse(Mono.empty());
        assertCompletedEmpty(doFindByEmail());
    }

    private Mono<GenericResultDto<UserResponse>> doFindByEmail(){
        EmailOnly emailOnly = userDto;
        return service.getUserByEmail(emailOnly);
    }

    private <T> void assertCompletedEmpty(Mono<T> mono){
        StepVerifier.create(mono)
                .expectComplete()
                .verify();
    }

    private void mockNumUsersAllowed(int usersallowed){
        when(repository.count()).thenReturn(Mono.just(Long.valueOf(maxUsers-usersallowed)));
    }

    private void mockSaveResponse(Mono<User> monoReturn){
        when(repository.save(any(User.class))).thenReturn(monoReturn);
    }

    private  void mockFindByEmailResponse(Mono<User> monoReturn){
        when(repository.findByEmail(userDto.getUserEmail())).thenReturn(monoReturn);
    }

    private void mockFoundByIdlResponse(Mono<User> monoReturn){
        when(repository.findByUuid(userDto.getUserId())).thenReturn(monoReturn);
    }


}