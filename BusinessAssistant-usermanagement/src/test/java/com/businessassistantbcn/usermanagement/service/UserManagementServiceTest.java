package com.businessassistantbcn.usermanagement.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.businessassistantbcn.usermanagement.dto.Role;
import com.businessassistantbcn.usermanagement.dto.User;
import com.businessassistantbcn.usermanagement.repository.UserManagementRepository;

public class UserManagementServiceTest {
	

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
	public void test_listUserManagement() {
		user1=new User( 1l,"isabel@gmail.com", "isa",Role.ADMIN);	
		user2=new User( 2l,"ana@gmail.com", "ana",Role.ADMIN);
		users.add(user1);
		users.add(user2);
		
		when(userManagementRepository.findAll()).thenReturn(users);
		assertEquals(2,userManagementService.listUserManagement().size());
	}
	
	@Test
	@Order(2)
	public void test_createUser() {
		
		user3=new User( 3l,"maria@gmail.com", "maria",Role.ADMIN);	
		
		when(userManagementRepository.save(user3)).thenReturn(user3);
		
		assertEquals(user3, userManagementService.createUserManagement(user3));
	}
	
	@Test
	@Order(3)
	public void test_UpdateUserManagementFound() {
		user3=new User( 3l,"maria@gmail.com", "maria",Role.ADMIN);	
		User userModifyed= new User( 3l,"Maria@gmail.com", "Maria",Role.ADMIN);
		long id=user3.getUuid();
		when(userManagementRepository.findById(id)).thenReturn(Optional.of(user3));
		when(userManagementRepository.save(userModifyed)).thenReturn(userModifyed);
		assertEquals(userModifyed, userManagementService.UpdateUserManagement(userModifyed));
	}
	
	@Test
	@Order(4)
	public void test_UpdateUserManagementNotFound() {
			
		user3=new User( 25l,"maria@gmail.com", "maria",Role.ADMIN);	
		long id=25;
		when(userManagementRepository.findById(id)).thenReturn(Optional.empty());
		
		assertEquals(null, userManagementService.UpdateUserManagement(user3));
	}
	
	@Test
	@Order(5)
	public void test_deleteUser() {
		user3=new User( 3l,"maria@gmail.com", "maria",Role.ADMIN);	
		long id=user3.getUuid();
		when(userManagementRepository.findById(id)).thenReturn(Optional.of(user3));
		userManagementService.deleteUser(id);
		verify(userManagementRepository, times(1)).delete(user3);
		assertEquals("Usuario Borrado",userManagementService.deleteUser(id));
	}
	
	@Test
	@Order(6)
	public void test_deleteUsernotFound() {
		user3=new User( 25l,"maria@gmail.com", "maria",Role.ADMIN);	
		long id=25;
		when(userManagementRepository.findById(id)).thenReturn(Optional.empty());
		userManagementService.deleteUser(id);
		assertEquals("Usuario no encontrado",userManagementService.deleteUser(id));
	}
}
