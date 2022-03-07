package com.businessassistantbcn.usermanagement.mongodb;

import com.businessassistantbcn.usermanagement.config.SpringMongoDBTestConfiguration;
import com.businessassistantbcn.usermanagement.dto.UserDto;
import com.businessassistantbcn.usermanagement.repository.UserManagementRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@EnableMongoRepositories(basePackages = "com.businessassistantbcn.usermanagement.repository")
@ContextConfiguration(classes = { SpringMongoDBTestConfiguration.class })
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)

public class MongoDBTest {

    @Autowired
    UserManagementRepository userManagementRepository;


    @BeforeEach
    public void saveUser() throws IOException {
        UserDto userDto1 = UserDto.builder()
                .uuid("97287d26-b57a-4898-817f-6fd3f295f5df")
                .email("test@mail.com")
                .role("User")
                .build();
        UserDto userDto2 = UserDto.builder()
                .uuid("9b7f669e-2af1-47aa-b468-f3b359e3c2fc")
                .email("test@test.com")
                .role("Admin")
                .build();
        userManagementRepository.saveAll(List.of(userDto1,userDto2));
    }

    @Test
    void testUserDto(){
        assertThat(userManagementRepository.findAll().size()).isGreaterThan(1);
        assertEquals(userManagementRepository.count(),2);
    }

    @AfterEach
    public void deleteUsers()throws  IOException{
        userManagementRepository.deleteAll();
        assertThat(userManagementRepository.count()).isEqualTo(0);

    }
}
