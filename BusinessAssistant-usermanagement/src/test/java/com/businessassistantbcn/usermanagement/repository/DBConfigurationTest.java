package com.businessassistantbcn.usermanagement.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;

@DataMongoTest
@TestPropertySource(locations = "classpath:persistence-test.properties")
public class DBConfigurationTest {

    @Autowired
    private Environment env;

    @Test
    public void smokeTest() {
        System.out.println("activeProfiles:"+env.getProperty("spring.data.mongodb.port"));
    }

}
