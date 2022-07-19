package com.businessassistantbcn.usermanagement.config;

import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/*@DataMongoTest
@TestPropertySource(locations = "classpath:persistence-test.properties")*/
@Configuration
public class DBConfigurationTest {

    private static final String IP = "localhost";
    private static final int PORT = 28017;

/*    @Bean
    public ImmutableMongodConfig embeddedMongoConfiguration() throws IOException {
        return new MongodConfigBuilder()
                .version(Version.V4_0_2)
                .net(new Net(IP, PORT, Network.localhostIsIPv6()))
                .build();
    }*/

/*
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
*/

}
