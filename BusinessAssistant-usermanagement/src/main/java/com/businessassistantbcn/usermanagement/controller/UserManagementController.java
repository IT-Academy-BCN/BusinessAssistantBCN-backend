package com.businessassistantbcn.usermanagement.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.businessassistantbcn.usermanagement.document.User;
import com.businessassistantbcn.usermanagement.service.ServiceUser;

@RestController
@Slf4j
@RequestMapping(value = "/businessassistantbcn/api/v1/usermanagement")
public class UserManagementController {
	
	private static Logger log = LoggerFactory.getLogger(User.class);
	
	@Autowired
	private ServiceUser serviceUser;
	
    @GetMapping(value="/test")
    @ApiOperation("Get test")
    @ApiResponse(code = 200, message = "OK")
    public String test() {
        return "Hello from BusinessAssistant User!!!";
    }
  
    
    @GetMapping(value="/user/{uuid}")
    public void getUser(@PathVariable ("uuid") String uuid) {
     	Optional<User> findingUser = serviceUser.getUserbyuuid(uuid);
    	if (findingUser.isPresent())   	{
    		log.info("Usuario encontrado"); 
    	} else {
    		log.info("Usuario no encontrado");
    	} 	
    }
    
    @PostMapping(value="/user/new", consumes="application/json")
    public void newUser(@RequestBody User user){
    	Optional<User> findingUser = serviceUser.getUserbyEmail(user.getEmail());
    	if (!findingUser.isPresent()) {
    		serviceUser.newUser(user);
    		log.info("Usuario introducido correctamente");
    	} else {
    		log.info("Usuario ya existente");
    	}
    } 
    
    @PutMapping(value="/user/{uuid}", consumes="application/json")
    public void updateUser(@PathVariable("uuid") String uuid, @RequestBody User user){
    	Optional<User> findingUser = serviceUser.getUserbyuuid(uuid);
    	if (findingUser.isPresent()) {
    		@SuppressWarnings("unused")
			User updatedUser = findingUser.get();
    		updatedUser = serviceUser.newUser(user);
    		log.info("Usuario actualizado correctamente");
    	} else {
    		log.info("Usuario no encontrado");
    	}
    	    
    }
    
    @DeleteMapping(value="/user/{uuid}")
    public void deleteUser(@PathVariable("uuid") String uuid){
    	Optional<User> findingUser = serviceUser.getUserbyuuid(uuid);
    	if (findingUser.isPresent()) {
    		serviceUser.deleteUser(findingUser.get());
    		log.info("Usuario eliminado correctamente");
    	} else {
    		log.info("Usuario no encontrado");
    		}
    		
    }  
    
}