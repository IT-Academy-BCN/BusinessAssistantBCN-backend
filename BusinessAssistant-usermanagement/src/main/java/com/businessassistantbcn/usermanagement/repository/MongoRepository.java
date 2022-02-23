package com.businessassistantbcn.usermanagement.repository;

import com.businessassistantbcn.usermanagement.config.PropertiesConfig;
import com.businessassistantbcn.usermanagement.dto.User;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.PostConstruct;

@EnableConfigurationProperties({PropertiesConfig.class})
public class MongoRepository {
    @Autowired
    PropertiesConfig propertiesConfig;

    private final MongoClient client;
    private MongoCollection<User> userCollection;

    public MongoRepository(MongoClient client) {
        this.client = client;
    }

    @PostConstruct
    void init(){
        //userCollection = client.getDatabase("babcn-users").getCollection("users",User.class);
        userCollection = client.getDatabase(propertiesConfig.getDatabase()).getCollection(propertiesConfig.getCollection(),User.class);
    }

}
