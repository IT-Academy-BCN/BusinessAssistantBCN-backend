package com.businessassistantbcn.usermanagement.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.businessassistantbcn.usermanagement.document.User;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;


@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
public class UserManagementRepositoryTest {
	
	
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
	public void setUp()  {
		iUserManagementRepository.save(user1);
		iUserManagementRepository.save(user2);
			
	}
   
    @AfterEach
    public void teardown() {
    	iUserManagementRepository.deleteAll();
    }
	
	@Test
	void tesSavetWithMongoRepository() {
	
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
