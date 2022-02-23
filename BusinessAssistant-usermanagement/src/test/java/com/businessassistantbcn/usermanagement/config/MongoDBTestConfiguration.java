package com.businessassistantbcn.usermanagement.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.businessassistantbcn.usermanagement.repository")
@PropertySource("classpath:persistence-test.properties")
@EnableTransactionManagement
public class MongoDBTestConfiguration {

    @Autowired
    private Environment environment;

    @Bean
    public MongoClient mongoClient() {
        /*
        mongodb://<user>:<pwd>@host1[:port1][,host2[:port2],…[,hostN[:portN]]]/<dbName>?options
         */
        //ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017"); //TODO parameter

        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017"); //TODO parameter
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(mongoClientSettings);


        // otra opcion

    }
}
