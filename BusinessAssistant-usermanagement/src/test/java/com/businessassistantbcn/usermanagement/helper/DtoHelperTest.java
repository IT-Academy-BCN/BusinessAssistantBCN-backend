package com.businessassistantbcn.usermanagement.helper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.businessassistantbcn.usermanagement.document.Role;
import com.businessassistantbcn.usermanagement.document.User;
import com.businessassistantbcn.usermanagement.dto.UserCreationDto;
import com.businessassistantbcn.usermanagement.dto.UserDto;

@ExtendWith(SpringExtension.class)
public class DtoHelperTest {
	
    @MockBean
	@Autowired
    DtoHelper helperDto;
       
    User user;
    UserDto userDto;
    UserCreationDto userCreationDto;
    List<Role> roles;
    List<String> rolesString;
     
    @Test
    public void test_convertToUser() {
    rolesString = new ArrayList<String>();
    rolesString.add("ADMIN");
    rolesString.add("USER");
    userDto = new UserDto("7e10fe51-772e-441f-874d-1c03dee79ad9", 
    		              "user@Dto.es", rolesString);
    user = new User();
    
    when(helperDto.convertToUser(userDto)).thenReturn(user);
    BeanUtils.copyProperties(userDto, user); 
    
    assertEquals(user, helperDto.convertToUser(userDto));
    assertEquals("7e10fe51-772e-441f-874d-1c03dee79ad9", user.getUuid());
    assertEquals("user@Dto.es", user.getEmail());  
    assertEquals(null, user.getPassword());
    }
    
    
    @Test
    public void test_convertToDto() {
    roles = new ArrayList<Role>();
    
    roles.add(Role.ADMIN);
    roles.add(Role.USER);
    user = new User("7e10fe51-772e-441f-874d-1c03dee79ad9", 
    		        "user@Dto.es", "1234", roles);
    userDto = new UserDto();
    
    when(helperDto.convertToDto(user)).thenReturn(userDto);
    BeanUtils.copyProperties(user, userDto);
      
    assertEquals(userDto, helperDto.convertToDto(user));
    assertEquals("7e10fe51-772e-441f-874d-1c03dee79ad9", userDto.getUuid());
    assertEquals("user@Dto.es", userDto.getEmail());  
    }
    
    
    @Test
    public void test_convertToUserFromCreationDto() {
    rolesString = new ArrayList<String>();
    rolesString.add("ADMIN");
    rolesString.add("USER");
    userCreationDto = new UserCreationDto("user@Dto.es", "1234");
    user = new User();
    
    when(helperDto.convertToUserFromCreationDto(userCreationDto)).thenReturn(user);
    BeanUtils.copyProperties(userCreationDto, user);
      
    assertEquals(user, helperDto.convertToUserFromCreationDto(userCreationDto));
    assertEquals("user@Dto.es", user.getEmail());  
    assertEquals("1234", user.getPassword());    
    }
    
    @Test
    public void test_convertToUserRoles() {
    rolesString = new ArrayList<String>();
    rolesString.add("USER"); 
    rolesString.add("ADMIN");
    UserDto userDto = new UserDto("7e10fe51-772e-441f-874d-1c03dee79ad9", 
                                  "user@Dto.es", rolesString);
    User user = new User("7e10fe51-772e-441f-874d-1c03dee79ad9", 
                         "user@Dto.es", "1234", null);
    
    roles = new ArrayList<Role>();
    roles.add(Role.USER);
    roles.add(Role.ADMIN);   

    when(helperDto.convertToUserRoles(rolesString)).thenReturn(roles);
   
    assertEquals(roles, helperDto.convertToUserRoles(rolesString));   
	}
	
    @Test
	public void test_convertToUserDtoRoles() {   	
    roles = new ArrayList<Role>();
    roles.add(Role.USER); 
    roles.add(Role.ADMIN);
    
    User user = new User("7e10fe51-772e-441f-874d-1c03dee79ad9", 
                         "user@Dto.es", "1234", roles);
    
    UserDto userDto = new UserDto("7e10fe51-772e-441f-874d-1c03dee79ad9", 
                                  "user@Dto.es", null);
    
    rolesString = new ArrayList<String>();
    rolesString.add("USER");
    rolesString.add("ADMIN");   

    when(helperDto.convertToUserDtoRoles(roles)).thenReturn(rolesString);
   
    assertEquals(rolesString,  helperDto.convertToUserDtoRoles(roles));			
	}
}