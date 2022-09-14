package com.businessassistantbcn.usermanagement.config;

import com.businessassistantbcn.usermanagement.repository.UserManagementRepository;
import com.businessassistantbcn.usermanagement.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;

@TestConfiguration
@DataMongoTest
public class TestConfig {

    @Autowired
    public UserManagementRepository userManagementRepository;

    @Bean
    public UserManagementService getUserManagementService(){
        return new UserManagementService();
    }

}
