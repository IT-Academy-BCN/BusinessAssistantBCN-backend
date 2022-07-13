package com.businessassistantbcn.usermanagement.config;

import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

@Configuration
public class MongoDbTestConfiguration {

    private static final String IP = "localhost";
    private static final int PORT = 28018;

    @Bean
    public ImmutableMongodConfig embeddedMongoConfiguration() throws UnknownHostException {

        return MongodConfig.builder().version(Version.V3_2_20)
                .net(new Net(IP, PORT, Network.localhostIsIPv6()))
                .build();

    }

}
