package com.businessassistantbcn.usermanagement.repository;

import com.businessassistantbcn.usermanagement.document.User;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.util.function.Predicate;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@TestPropertySource(locations = "classpath:persistence-test.properties")
//TODO - PLATFORM BUG - java.lang.RuntimeException: Could not start process: <EOF>
public class UserManagementRepositoryTest {

    @Autowired
    UserManagementRepository userRepository;

    ObjectId id1 = new ObjectId("63d9666bbdf0196d2c766fa0");
    ObjectId id2 = new ObjectId("63d9666bbdf0196d2c766fb1");
    String uuid_1 = "26977eee-89f8-11ec-a8a3-0242ac120002";
    String uuid_2 = "26977eee-89f8-11ec-a8a3-0242ac120003";
    String email_1 = "user1@mail.com";
    String email_2 = "user2@mail.com";
    User user1 = new User(id1, uuid_1,email_1,"abc123",null, System.currentTimeMillis());
    User user2 = new User(id2, uuid_2,email_2,"abc123",null, System.currentTimeMillis());


/*
    @Test
    public void ping(){
        userRepository.save(user1);

        userRepository.existsByEmail(email_1);

        userRepository.findByUuid(uuid_1);
//Véase https://dzone.com/articles/spring-boot-with-embedded-mongodb

       User user = userRepository.findByUuid(uuid_1).block();
        System.out.println(user.getEmail());
        System.out.println(user.getUuid());
}
*/
    /*@Test
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

    @Test
    public void existsByEmail() {

        Publisher<Boolean> setup = this.userRepository //
                .deleteAll() //
                .thenMany(this.userRepository.saveAll(Flux.just(user1, user2))) //
                .thenMany(this.userRepository.existsByEmail(email_1));

        Predicate<Boolean> userPredicate = exist ->  true;

        StepVerifier
                .create(setup)
                .expectNextMatches(userPredicate)
                .verifyComplete();
    }

    @Test
    public void existsByUuid() {

        Publisher<Boolean> setup = this.userRepository //
                .deleteAll() //
                .thenMany(this.userRepository.saveAll(Flux.just(user1, user2))) //
                .thenMany(this.userRepository.existsByUuid(uuid_1));

        Predicate<Boolean> userPredicate = exist ->  true;

        StepVerifier
                .create(setup)
                .expectNextMatches(userPredicate)
                .verifyComplete();
    }
*/
}
