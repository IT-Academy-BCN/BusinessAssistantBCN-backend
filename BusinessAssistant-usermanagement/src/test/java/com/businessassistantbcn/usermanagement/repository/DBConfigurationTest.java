package com.businessassistantbcn.usermanagement.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@TestPropertySource(locations = "classpath:persistence-test.properties")
class DBConfigurationTest {

    @Autowired
    private Environment env;

    @Test
    void smokeTest() {
        String port = env.getProperty("spring.data.mongodb.port");
        String database = env.getProperty("spring.data.mongodb.database");
        String version = env.getProperty("spring.mongodb.embedded.version");
        assertEquals(27018, Integer.parseInt(port));
        assertEquals("test", database);
        assertEquals("3.2.2", version);
    }

}
