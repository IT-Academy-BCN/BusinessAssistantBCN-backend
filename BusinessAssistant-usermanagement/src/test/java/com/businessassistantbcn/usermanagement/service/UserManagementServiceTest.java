package com.businessassistantbcn.usermanagement.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import com.businessassistantbcn.usermanagement.config.PropertiesConfig;
import com.businessassistantbcn.usermanagement.dto.output.GenericResultDto;
import com.businessassistantbcn.usermanagement.dto.input.EmailOnly;
import com.businessassistantbcn.usermanagement.dto.input.IdOnly;
import com.businessassistantbcn.usermanagement.dto.input.SingUpRequest;
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

    User user;

    SingUpRequest singup;
    IdOnly idOnly;

    EmailOnly emailOnly;


    @BeforeEach
    void setUp() {
        List<Role> roles = new ArrayList<>();
        String uuid = "cb5f0578-6574-4e9a-977d-fca06c7cb67b"; //UUID.randomUUID().toString();
        roles.add(Role.USER);
        ObjectId id = new ObjectId("63cfbcb31fa50abde8288a07");
        String email = "user@user.es";
        String password = "12345";
        long latestAccess = 1111111111;

        singup = new UserDto(null, email, null, password);
        idOnly = new UserDto(uuid, null, null, null);
        emailOnly = new UserDto(null, email, null, null);
        user = new User(id, uuid, email, password, roles, latestAccess);

    }

    @Test
    public void addNewUserTest(){
        int maxUsers = 10;
        when(propertiesConfig.getEnabled()).thenReturn(true);
        when(propertiesConfig.getMaxusers()).thenReturn(maxUsers);
        String errorLimitMsg = "Users limit on database";
        when(propertiesConfig.getErrorLimitDb()).thenReturn(errorLimitMsg);

        when(repository.count()).thenReturn(Mono.just(Long.valueOf(maxUsers-1)));
        when(repository.findByEmail(singup.getUserEmail())).thenReturn(Mono.empty());
        when(repository.save(any(User.class)/*user*/)).thenReturn(Mono.just(user));

        Mono<GenericResultDto<?>> genericResultMono = service.addUser(singup);

        StepVerifier.create(genericResultMono)
                .assertNext(result -> {
                    UserResponse userResponse = (UserResponse) result.getResults()[0];
                    assertEquals(singup.getUserEmail(), userResponse.getUserEmail());
                    assertEquals(List.of(Role.USER.name()), userResponse.getUserRoles());
                })
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void addUserLimitDbExceededTest(){
        int maxUsers = 10;
        when(propertiesConfig.getEnabled()).thenReturn(true);
        when(propertiesConfig.getMaxusers()).thenReturn(maxUsers);
        String errorLimitMsg = "Users limit on database";
        when(propertiesConfig.getErrorLimitDb()).thenReturn(errorLimitMsg);
        when(repository.count()).thenReturn(Mono.just(Long.valueOf(maxUsers)));
        //System.out.println(propertiesConfig.getMaxusers());
        //System.out.println(repository.count().block());
        //System.out.println(propertiesConfig.getMaxusers() > repository.count().block());
        when(repository.findByEmail(singup.getUserEmail())).thenReturn(Mono.empty());
        when(repository.save(user/*user*/)).thenReturn(Mono.just(user));

        Mono<GenericResultDto<?>> genericResultMono = service.addUser(singup);
        StepVerifier.create(genericResultMono)
                .assertNext(result -> {
                    ErrorDto errorDto = (ErrorDto) result.getResults()[0];
                    assertEquals(errorLimitMsg, errorDto.getErrorMessage());
                })
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void singupEmailExistTest(){
        int maxUsers = 10;
        when(propertiesConfig.getEnabled()).thenReturn(true);
        when(propertiesConfig.getMaxusers()).thenReturn(maxUsers);
        String errorLimitMsg = "Users limit on database";
        when(propertiesConfig.getErrorLimitDb()).thenReturn(errorLimitMsg);

        when(repository.count()).thenReturn(Mono.just(Long.valueOf(maxUsers-1)));
        when(repository.findByEmail(singup.getUserEmail())).thenReturn(Mono.just(user));
        when(repository.save(any(User.class)/*user*/)).thenReturn(Mono.just(user));


        Mono<GenericResultDto<?>> genericResultMono = service.addUser(singup);
        String errorMsg = "already exist other user whit email: "+singup.getUserEmail();
        StepVerifier.create(genericResultMono)
                .assertNext(result -> {
                    ErrorDto errorDto = (ErrorDto) result.getResults()[0];
                    assertEquals(errorMsg, errorDto.getErrorMessage());
                })
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void getUserByIdTest() {
        String idRequired = idOnly.getUserId();
        when(repository.findByUuid(idRequired)).thenReturn(Mono.just(user));
        when(repository.save(user)).thenReturn(Mono.just(user));
        Mono<GenericResultDto<UserResponse>> genericResultMono = service.getUserById(idOnly);


        StepVerifier.create(genericResultMono)
                .assertNext(result -> {
                    UserResponse userResponse = result.getResults()[0];
                    assertEquals(idOnly.getUserId(), userResponse.getUserId());
                    assertNotNull(userResponse.getUserEmail(), "user email is null");
                    assertNotNull(userResponse.getUserRoles(), "user roles is null");
                })
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void getUserByUuidNotExistTest() {
        String idRequired = idOnly.getUserId();
        when(repository.findByUuid(idRequired)).thenReturn(Mono.empty());
        when(repository.save(any(User.class)/*user*/)).thenReturn(Mono.empty());
        Mono<GenericResultDto<UserResponse>> genericResultMono = service.getUserById(idOnly);

        StepVerifier.create(genericResultMono)
                .expectComplete()
                .verify();
    }

    @Test
    public void getUserByEmailTest() {
        String emailRequired = emailOnly.getUserEmail();
        when(repository.findByEmail(emailOnly.getUserEmail())).thenReturn(Mono.just(user));
        when(repository.save(user)).thenReturn(Mono.just(user));
        Mono<GenericResultDto<UserResponse>> genericResultMono = service.getUserByEmail(emailOnly);

        StepVerifier.create(genericResultMono)
                .assertNext(result -> {
                    UserResponse userResponse = result.getResults()[0];
                    assertEquals(emailOnly.getUserEmail(), userResponse.getUserEmail());
                    assertNotNull(userResponse.getUserId(), "user id is null");
                    assertNotNull(userResponse.getUserRoles(), "user roles is null");
                })
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void getUserByEmailNotExistTest() {
        String emailRequired = emailOnly.getUserEmail();
        when(repository.findByEmail(emailRequired)).thenReturn(Mono.empty());
        when(repository.save(any(User.class)/*user*/)).thenReturn(Mono.empty());
        Mono<GenericResultDto<UserResponse>> genericResultMono = service.getUserByEmail(emailOnly);
        StepVerifier.create(genericResultMono)
                .expectComplete()
                .verify();
    }
}