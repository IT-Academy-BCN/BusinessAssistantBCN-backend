package com.businessassistantbcn.usermanagement.repository;


import com.businessassistantbcn.usermanagement.config.SpringMongoDBTestConfiguration;
import com.businessassistantbcn.usermanagement.document.Role;
import com.businessassistantbcn.usermanagement.document.User;
import com.mongodb.client.MongoClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SpringMongoDBTestConfiguration.class })
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@EnableMongoRepositories(basePackages = "com.businessassistantbcn.usermanagement.repository")

public class UserManagementRepositoryTest {
    @Autowired
    IUserManagementRepository iUserManagementRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MongoClient mongoClient;

    @Test
    void test(){
        assertNotNull(entityManager);
        assertNotNull(mongoClient);
    }

    @Test
    public void testCreateUser(){
        List<Role> roleList = new ArrayList<>();
        roleList.add(Role.ADMIN);
        User user = new User();
        user.setUuid("9b7f669e-2af1-47aa-b468-f3b359e3c2fc");
        user.setEmail("test@mail.com");
        user.setPassword("admin1234");
        user.setRoles(roleList);
        User user2 =  iUserManagementRepository.save(user);
        assertThat(user2).isNotNull();
        assertThat(user2.getEmail()).isEqualTo(user.getEmail());

    }

}