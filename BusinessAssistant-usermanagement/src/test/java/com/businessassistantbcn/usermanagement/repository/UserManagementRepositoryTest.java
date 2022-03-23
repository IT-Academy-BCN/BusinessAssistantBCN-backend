package com.businessassistantbcn.usermanagement.repository;


import com.businessassistantbcn.usermanagement.config.SpringMongoDBTestConfiguration;
import com.mongodb.client.MongoClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SpringMongoDBTestConfiguration.class })
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)

@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)

public class UserManagementRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MongoClient mongoClient;

    @Test
    void test(){
        assertNotNull(entityManager);
        assertNotNull(mongoClient);
    }

}