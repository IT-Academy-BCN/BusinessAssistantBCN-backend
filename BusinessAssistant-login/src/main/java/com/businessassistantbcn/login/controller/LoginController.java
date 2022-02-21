package com.businessassistantbcn.login.controller;

import com.businessassistantbcn.login.dto.AuthenticationRequest;
import com.businessassistantbcn.login.dto.AuthenticationResponse;
import com.businessassistantbcn.login.security.DevelopAuthenticationProvider;
import com.businessassistantbcn.login.service.LoginService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/businessassistantbcn/api/v1")
public class LoginController {
	
	@Autowired
	private DevelopAuthenticationProvider provider;
	
	@Autowired
	private LoginService loginService;
	
	@GetMapping("/test")
	public String test() {
		log.info("** Saludos desde el logger **");
		
		return "Hello from BusinessAssistant Barcelona!!!";
	}
	

	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(
			@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try {
			provider.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getEmail(),
					authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		
		final UserDetails userDetails = loginService.loadUserByUsername(authenticationRequest.getEmail());
		final String jwt = loginService.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
}
