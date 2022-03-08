package com.businessassistantbcn.usermanagement.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.businessassistantbcn.usermanagement.config.SpringMongoDBTestConfiguration;
import com.businessassistantbcn.usermanagement.document.Role;
import com.businessassistantbcn.usermanagement.document.User;
import com.businessassistantbcn.usermanagement.dto.UserCreationDto;
import com.businessassistantbcn.usermanagement.dto.UserDto;
import com.businessassistantbcn.usermanagement.helper.HelperDto;
import com.businessassistantbcn.usermanagement.repository.IUserManagementRepository;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SpringMongoDBTestConfiguration.class })
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class UserManagementServiceTest {
	
	@Mock
	IUserManagementRepository repository;
	
	@Mock
	HelperDto helperDto;
	
	@InjectMocks
	UserManagementService service;
	
	User user;
	UserDto userDto;
	UserCreationDto userCreationDto;
	
	@Test
    public void test_addUser() {
	List<Role> roles = new ArrayList<Role>();
	roles.add(Role.USER);
    user = new User(UUID. randomUUID().toString(), "user@user.com", "12345", roles);
    
    	when(repository.save(user)).thenReturn(user);
    	assertEquals(user, service.addUser(user));
       
     }

	 @Test
     public void test_getUserByUuid() {
	 List<Role> roles = new ArrayList<Role>();
	 roles.add(Role.USER);
     String Uuid = UUID.randomUUID().toString();
     user = new User(Uuid, "user@user.com", "12345", roles);
     
	     when(repository.findByUuid(Uuid)).thenReturn(Optional.of(user));
	 	 assertEquals(user, service.getUserByUuid(Uuid).get());
        	
     }

	 @Test
     public void test_getUserByEmail() {
	 List<Role> roles = new ArrayList<Role>();
	 roles.add(Role.USER);
     String email = "user@user.com";
     user = new User(UUID.randomUUID().toString(), email, "12345", roles);
         
	     when(repository.findByEmail(email)).thenReturn(Optional.of(user));
	 	 assertEquals(user, service.getUserByEmail(email).get());
     }

	 @Test
     public void test_convertToUser() {
	 List<Role> roles = new ArrayList<Role>();
	 roles.add(Role.USER);
	 List<String> rolesDto = new ArrayList();
	 rolesDto.add("user");
     String Uuid = UUID.randomUUID().toString();
     user = new User(Uuid, "user@user.com", "12345", roles); 
     userDto = new UserDto(Uuid, "user@user.com", rolesDto);
		 
		 when(helperDto.convertToUser(userDto)).thenReturn(user);
	 	 assertEquals(user, service.convertToUser(userDto));
		 
     }
     
	 @Test
     public void test_convertToUserFromCreationDto(){
	 List<Role> roles = new ArrayList<Role>();
	 roles.add(Role.USER);
	 List<String> rolesDto = new ArrayList<String>();
	 rolesDto.add("user");
	 String Uuid = UUID.randomUUID().toString();
     user = new User(Uuid, "user@user.com", "12345", roles); 
     userCreationDto = new UserCreationDto("user@user.com", "12345");
			 
		 when(helperDto.convertToUserFromCreationDto(userCreationDto)).thenReturn(user);
	 	 assertEquals(user, service.convertToUserFromCreationDto(userCreationDto));
     } 

	 @Test
     public void test_convertToDto() {
	 List<Role> roles = new ArrayList<Role>();
	 roles.add(Role.USER);
	 List<String> rolesDto = new ArrayList<String>();
	 rolesDto.add("user");
	 String Uuid = UUID.randomUUID().toString();
     user = new User(Uuid, "user@user.com", "12345", roles); 
     userDto = new UserDto(Uuid, "user@user.com", rolesDto);
			 
		 when(helperDto.convertToDto(user)).thenReturn(userDto);
	 	 assertEquals(userDto, service.convertToDto(user));

     }
	
	
	
	
}
