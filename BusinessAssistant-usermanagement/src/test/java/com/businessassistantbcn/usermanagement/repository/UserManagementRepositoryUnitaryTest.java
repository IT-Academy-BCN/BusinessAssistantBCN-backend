package com.businessassistantbcn.usermanagement.repository;

import com.businessassistantbcn.usermanagement.UserManagementRepository;
import com.businessassistantbcn.usermanagement.document.User;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.util.function.Predicate;

@DataMongoTest
@TestPropertySource(locations = "classpath:persistence-embedded-test.properties")
public class UserManagementRepositoryUnitaryTest {

    @Autowired
    UserManagementRepository userRepository;


    String uuid_1 = "26977eee-89f8-11ec-a8a3-0242ac120002";
    String uuid_2 = "26977eee-89f8-11ec-a8a3-0242ac120003";
    String email_1 = "user1@mail.com";
    String email_2 = "user2@mail.com";
    User user1 = new User(uuid_1,email_1,"abc123",null);
    User user2 = new User(uuid_2,email_2,"abc123",null);

    @Test
    public void findByUUid() {

        Publisher<User> setup = this.userRepository.deleteAll() //
                .thenMany(this.userRepository.saveAll(Flux.just(user1, user2))) //
                .thenMany(this.userRepository.findByUuid(uuid_2));

        Predicate<User> userPredicate = user ->  uuid_2.equalsIgnoreCase(user.getUuid());

        StepVerifier
                .create(setup) //
                .expectNextMatches(userPredicate)  //
                .verifyComplete();
    }


    @Test
    public void findByEmail() {

        Publisher<User> setup = this.userRepository //
                .deleteAll() //
                .thenMany(this.userRepository.saveAll(Flux.just(user1, user2))) //
                .thenMany(this.userRepository.findByEmail(email_1));

        Predicate<User> userPredicate = user ->  email_1.equalsIgnoreCase(user.getEmail());

        StepVerifier
                .create(setup)
                .expectNextMatches(userPredicate)
                .verifyComplete();
    }
}
