package com.businessassistantbcn.usermanagement.mongodb;

import com.businessassistantbcn.usermanagement.config.SpringMongoDBTestConfiguration;
import com.businessassistantbcn.usermanagement.document.Role;
import com.businessassistantbcn.usermanagement.document.User;
import com.businessassistantbcn.usermanagement.repository.IUserManagementRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@EnableMongoRepositories(basePackages = "com.businessassistantbcn.usermanagement.repository")
@ContextConfiguration(classes = { SpringMongoDBTestConfiguration.class })
//@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)

public class MongoDBTest {

    List<Role> roles1 = new ArrayList<>();
    List<Role> roles2 = new ArrayList<>();

    @Autowired
    IUserManagementRepository iUserManagementRepository;

    @BeforeEach
    public void saveUser() throws IOException {

        roles1.add(Role.USER);
        roles2.add(Role.ADMIN);

        User user1 = User.builder()
                .uuid("97287d26-b57a-4898-817f-6fd3f295f5df")
                .email("test@mail.com")
                .password("user1234")
                .roles(roles1)
                .build();
        User user2 = User.builder()
                .uuid("9b7f669e-2af1-47aa-b468-f3b359e3c2fc")
                .email("test@test.com")
                .password("admin1234")
                .roles(roles2)
                .build();
        iUserManagementRepository.saveAll(List.of(user1,user2));
    }

    @Test
    void testUserDto(){
        assertThat(iUserManagementRepository.findAll().size()).isGreaterThan(1);
        assertEquals(iUserManagementRepository.count(),2);
    }

    @AfterEach
    public void deleteUsers()throws  IOException{
        iUserManagementRepository.deleteAll();
        assertThat(iUserManagementRepository.count()).isEqualTo(0);

    }
}
