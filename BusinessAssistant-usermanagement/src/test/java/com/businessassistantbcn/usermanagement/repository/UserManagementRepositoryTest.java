package com.businessassistantbcn.usermanagement.repository;

import com.businessassistantbcn.usermanagement.document.User;
<<<<<<< HEAD
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.reactivestreams.Publisher;
=======
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.*;
>>>>>>> develop
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@PropertySource("classpath:persistence-test.properties")
@DataMongoTest
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class UserManagementRepositoryTest {

    @Container
    static MongoDBContainer container = new MongoDBContainer("mongo")
            .withStartupTimeout(Duration.ofSeconds(60));

    @DynamicPropertySource
    static void initMongoProperties(DynamicPropertyRegistry registry) {
        System.out.println("container url: {}" + container.getReplicaSetUrl("babcn-users"));
        System.out.println("container host/port: {}/{}" + container.getHost() + " - " + container.getFirstMappedPort());

<<<<<<< HEAD
    ObjectId id1 = new ObjectId("63d9666bbdf0196d2c766fa0");
    ObjectId id2 = new ObjectId("63d9666bbdf0196d2c766fb1");
=======
        registry.add("spring.data.mongodb.uri", () -> container.getReplicaSetUrl("babcn-users"));
    }

    @Autowired
    private UserManagementRepository userRepository;

>>>>>>> develop
    String uuid_1 = "26977eee-89f8-11ec-a8a3-0242ac120002";
    String uuid_2 = "26977eee-89f8-11ec-a8a3-0242ac120003";
    String email_1 = "user1@mail.com";
    String email_2 = "user2@mail.com";
<<<<<<< HEAD
    User user1 = new User(id1, uuid_1,email_1,"abc123",null, System.currentTimeMillis());
    User user2 = new User(id2, uuid_2,email_2,"abc123",null, System.currentTimeMillis());


/*
    @Test
    public void ping(){
        userRepository.save(user1);

        userRepository.existsByEmail(email_1);

        userRepository.findByUuid(uuid_1);
//Véase https://dzone.com/articles/spring-boot-with-embedded-mongodb
=======
>>>>>>> develop


    @BeforeEach
    public void setUp(){
        userRepository.deleteAll().block();
        User user1 = new User(uuid_1, email_1, "abc123", null);
        User user2 = new User(uuid_2, email_2, "abc123", null);
        userRepository.saveAll(Flux.just(user1, user2)).blockLast();
    }

    @Test
    void findByUuidTest(){

        Mono<User> firstUser = userRepository.findByUuid(uuid_1);
        firstUser.blockOptional().ifPresentOrElse(
                u -> assertEquals(u.getUuid(), uuid_1),
                () -> fail("User not found: " + uuid_1));

        Mono<User> secondUser = userRepository.findByUuid(uuid_2);
        secondUser.blockOptional().ifPresentOrElse(
                u -> assertEquals(u.getUuid(), uuid_2),
                () -> fail("User not found: " + uuid_2));
    }

    @Test
    void findByEmailTest(){

        Mono<User> firstUser = userRepository.findByEmail(email_1);
        firstUser.blockOptional().ifPresentOrElse(
                u -> assertEquals(u.getEmail(), email_1),
                () -> fail("User not found: " + email_1));

        Mono<User> secondUser = userRepository.findByEmail(email_1);
        secondUser.blockOptional().ifPresentOrElse(
                u -> assertEquals(u.getEmail(), email_1),
                () -> fail("User not found: " + email_1));
    }

    @Test
    void existsByUuidTest(){
        Boolean exists = userRepository.existsByUuid(uuid_1).block();
        assertEquals(exists, true);
    }

    @Test
    void existsByEmailTest(){
        Boolean exists = userRepository.existsByEmail(email_1).block();
        assertEquals(exists, true);
    }


}
