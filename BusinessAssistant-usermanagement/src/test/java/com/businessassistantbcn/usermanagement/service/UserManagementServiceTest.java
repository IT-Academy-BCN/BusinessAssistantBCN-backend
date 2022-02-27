package com.businessassistantbcn.usermanagement.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.businessassistantbcn.usermanagement.document.Role;
import com.businessassistantbcn.usermanagement.document.User;
import com.businessassistantbcn.usermanagement.repository.UserManagementRepository;

class UserManagementServiceTest {

	@Mock
	private UserManagementRepository userManagementRepository;
	
	@InjectMocks
	UserManagementService userManagementService;
	
	List<User> users;;
	User user1;
	User user2;
	User user3;
	
	
	@Test
	@Order(1)
	public void test_createUser() {
		
		user1=new User( "26977eee-89f8-11ec-a8a3-0242ac120003","maria@gmail.com", "maria",Role.ADMIN);	
		when(userManagementRepository.save(user1)).thenReturn(user1);
		
		User espected=userManagementService.createUserManagement(user1);
		
		assertEquals(user3, espected);
	}
	
	@Test
	@Order(2)
	public void test_listUserManagement() {
		user1=new User( "26977eee-89f8-11ec-a8a3-0242ac120002","isabel@gmail.com", "isa",Role.ADMIN);	
		user2=new User( "26977eee-89f8-11ec-a8a3-0242ac120003","ana@gmail.com", "ana",Role.ADMIN);
		users.add(user1);
		users.add(user2);
		
		when(userManagementRepository.findAll()).thenReturn(users);
		assertEquals(2,userManagementService.listUserManagement().size());
	}
	
	@Test
	@Order(3)
	public void test_UpdateUserManagementFound() {
		user3=new User( "26977eee-89f8-11ec-a8a3-0242ac120003","maria@gmail.com", "maria",Role.ADMIN);	
		User userModifyed= new User( "26977eee-89f8-11ec-a8a3-0242ac120003","Maria@gmail.com", "Maria",Role.ADMIN);
		String id=user3.getUuid();
		when(userManagementRepository.findById(id)).thenReturn(Optional.of(user3));
		when(userManagementRepository.save(userModifyed)).thenReturn(userModifyed);
		assertEquals(userModifyed, userManagementService.UpdateUserManagement(userModifyed));
	}
	
	@Test
	@Order(4)
	public void test_UpdateUserManagementNotFound() {
			
		user3=new User( "26977eee-89f8-11ec-a8a3-0242ac120005","maria@gmail.com", "maria",Role.ADMIN);	
		String id="26977eee-89f8-11ec-a8a3-0242ac120005";
		when(userManagementRepository.findById(id)).thenReturn(Optional.empty());
		
		assertEquals(null, userManagementService.UpdateUserManagement(user3));
	}
	
	@Test
	@Order(5)
	public void test_deleteUser() {
		user3=new User(  "26977eee-89f8-11ec-a8a3-0242ac120003","maria@gmail.com", "maria",Role.ADMIN);	
		String id=user3.getUuid();
		when(userManagementRepository.findById(id)).thenReturn(Optional.of(user3));
		userManagementService.deleteUser(id);
		verify(userManagementRepository, times(1)).delete(user3);
		assertEquals("Usuario Borrado",userManagementService.deleteUser(id));
	}
	
	@Test
	@Order(6)
	public void test_deleteUsernotFound() {
		user3=new User( "26977eee-89f8-11ec-a8a3-0242ac120003","maria@gmail.com", "maria",Role.ADMIN);	
		String id="26977eee-89f8-11ec-a8a3-0242ac120003";
		when(userManagementRepository.findById(id)).thenReturn(Optional.empty());
		userManagementService.deleteUser(id);
		assertEquals("Usuario no encontrado",userManagementService.deleteUser(id));
	}

}
