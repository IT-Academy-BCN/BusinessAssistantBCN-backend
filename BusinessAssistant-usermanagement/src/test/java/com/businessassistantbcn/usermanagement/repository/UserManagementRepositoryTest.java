package com.businessassistantbcn.usermanagement.repository;

import com.businessassistantbcn.usermanagement.document.User;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.PropertySource;
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

@PropertySource("classpath:test.properties")
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

        registry.add("spring.data.mongodb.uri", () -> container.getReplicaSetUrl("babcn-users"));
    }

    @Autowired
    private UserManagementRepository userRepository;

    ObjectId id1 = new ObjectId("63cfbcb31fa50abde8288a08");
    ObjectId id2 = new ObjectId("63cfbcb31fa50abde8288a09");

    String uuid_1 = "26977eee-89f8-11ec-a8a3-0242ac120002";
    String uuid_2 = "26977eee-89f8-11ec-a8a3-0242ac120003";
    String email_1 = "user1@mail.com";
    String email_2 = "user2@mail.com";
    Long latestAcces = System.currentTimeMillis();


    @BeforeEach
    public void setUp(){
        userRepository.deleteAll().block();
        User user1 = new User(id1, uuid_1, email_1, "abc123", null,latestAcces);
        User user2 = new User(id2, uuid_2, email_2, "abc123", null, latestAcces);
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
