package com.businessassistantbcn.usermanagement.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(value = "/businessassistantbcn/api/v1/usermanagement")
public class UserManagementController {
	  
	@Autowired
	private ServiceUser serviceUser;

	/*
    @GetMapping(value="/test")
    @ApiOperation("Get test")
    @ApiResponse(code = 200, message = "OK")
    public String test() {
        return "Hello from BusinessAssistant User!!!";
    }*/
  
    
    @GetMapping(value="/user/{uuid}")
    public ResponseEntity<User> getUser(@PathVariable ("uuid") String uuid) {
    	Optional<User> findingUser = serviceUser.getUserbyuuid(uuid);
    	if (!findingUser.isEmpty()) {
    		User foundUser = new User(); 
    		return ResponseEntity.ok(foundUser); 
    	} else {
    		System.out.println("Usuario no encontrado");
    		return ResponseEntity.notFound().build();
    	} 	
    }
    
    @PostMapping(value="/user/newuser", consumes="application/json")
    public ResponseEntity<User> newUser(@RequestBody User user){
    	Optional<User> findingUser = serviceUser.getUserbyEmail(user.getEmail());
    	if (findingUser.isPresent()) {
    		System.out.println("Usuario ya existente");
    		return ResponseEntity.badRequest().build();
    	} else {
    		System.out.println("Usuario creado correctamente");
    		return ResponseEntity.ok(serviceUser.newUser(user));
    		
    	}
    }
    
    @PutMapping(value="/user/{uuid}", consumes="application/json")
    public ResponseEntity<User> updateUser(@PathVariable("uuid") String uuid, @RequestBody User user){
    	Optional<User> findingUser = serviceUser.getUserbyuuid(uuid);
    	if (findingUser.isPresent()) {
    		return ResponseEntity.ok(serviceUser.updateUser(user));
    	} else {
    		System.out.println("Usuario no encontrado");
    		return ResponseEntity.badRequest().build();
    	}
    	    
    }
    
    @DeleteMapping(value="/user/{uuid}")
    public ResponseEntity<User> deleteUser(@PathVariable("uuid") String uuid){
    	Optional<User> findingUser = serviceUser.getUserbyuuid(uuid);
    	if (findingUser.isPresent()) {
    		Scanner escaner = new Scanner(System.in);
    		System.out.println("Está seguro que desea borrar el usuario " + uuid + " ? (Aprete y para aceptar)");
    		String resp = escaner.next(); 
    		escaner.close();
    		if (resp == "y") {
    			User deletableUser = findingUser.get();
    			serviceUser.deleteUser(deletableUser);
    			System.out.println("Usuario eliminado correctamente");
    			return ResponseEntity.ok().build();
    		} else {
    			System.out.println("Usuario no eliminado");
    			return ResponseEntity.badRequest().build();
    		}
    		
    	} else {
			System.out.println("Usuario no encontrado");
    		return ResponseEntity.badRequest().build();
    	}
    }
    
}