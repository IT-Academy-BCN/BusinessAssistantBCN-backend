package com.businessassistantbcn.usermanagement.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Configuration
@EnableJpaRepositories(basePackages = "com.businessassistantbcn.usermanagement.repository")
@PropertySource("classpath:persistence-test.properties")
@EnableTransactionManagement
public class SpringMongoDBTestConfiguration {


    @Autowired
    private Environment env;

    @Bean
    public MongoClient mongoClient() throws Exception{
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        return MongoClients.create(MongoClientSettings.builder()
//                .applyConnectionString(new ConnectionString("mongodb://localhost:27017" ))
                .applyConnectionString(new ConnectionString(env.getProperty("spring.data.mongodb.uri") ))
                .codecRegistry(codecRegistry)
                .build());
    }


}
