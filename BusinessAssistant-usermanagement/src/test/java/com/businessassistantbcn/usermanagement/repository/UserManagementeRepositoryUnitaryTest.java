package com.businessassistantbcn.usermanagement.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.businessassistantbcn.usermanagement.config.SpringMongoDBTestConfiguration;
import com.businessassistantbcn.usermanagement.document.User;



@EnableMongoRepositories(basePackages="com.businessassistantbcn.usermanagement.repository")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SpringMongoDBTestConfiguration.class })
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class UserManagementeRepositoryUnitaryTest {
	
	@Autowired
	MongoTemplate mongoTemplate; 
	 
	@Autowired
	IUserManagementRepository iUserManagementRepository;
	
	String uuid_1 = "26977eee-89f8-11ec-a8a3-0242ac120002";
	String uuid_2 = "26977eee-89f8-11ec-a8a3-0242ac120003";
	String email_1 = "user1@mail.com";
	String email_2 = "user2@mail.com";
	User user1 = new User(uuid_1,
			email_1,
			"abc123",
			null);
	
	User user2 = new User(uuid_2,
			email_2,
			"abc123",
			null);

	
	@BeforeEach 
	public void setUp() throws IOException  {
		
		
		
		iUserManagementRepository.save(user1);
		iUserManagementRepository.save(user2);
			
	}
   
    @AfterEach
    public void teardown() throws IOException {
    	iUserManagementRepository.deleteAll();
    	
    }
	
	@Test
	void testSave() {
	
	 assertEquals(iUserManagementRepository.count(), 2);
		
	}
	
	@Test
	void testFindByUuid() {
		
		Optional<User> foundUuid_1 = iUserManagementRepository
				.findByUuid(uuid_1);
		Optional<User> foundUuid_2 = iUserManagementRepository
				.findByUuid(uuid_2);

		assertTrue(foundUuid_1.isPresent());
		assertTrue(foundUuid_2.isPresent());
		
		
	}
	
	@Test
	void testFindByEmail() {
		
		Optional<User> foundEmail_1 = iUserManagementRepository
				.findByEmail(email_1);
		Optional<User> foundEmail_2 = iUserManagementRepository
				.findByEmail(email_2);

		assertTrue(foundEmail_1.isPresent());
		assertTrue(foundEmail_2.isPresent());
		
	}
	
}



