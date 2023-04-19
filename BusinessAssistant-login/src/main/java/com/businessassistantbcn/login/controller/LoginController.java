package com.businessassistantbcn.login.controller;

import com.businessassistantbcn.login.dto.AuthenticationRequest;
import com.businessassistantbcn.login.dto.AuthenticationResponse;
import com.businessassistantbcn.login.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/businessassistantbcn/api/v1")
public class LoginController {
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private LoginService loginService;
	
	@GetMapping("/test")
	public String test() {
		log.info("** Saludos desde el logger **");
		
		return "Hello from BusinessAssistant Barcelona!!!";
	}

	@PostMapping("/test-post")
	public String testPost() {
		log.info("** Saludos desde el logger --- POST**");

		return "Hello from BusinessAssistant Barcelona --- POST!!!";
	}

	@PostMapping("/login")
	//@PreAuthorize("hasAuthority('SUPERUSER')")
	public ResponseEntity<AuthenticationResponse> createAuthenticationToken(
		@RequestBody AuthenticationRequest authenticationRequest) {
		try {
			loginService.authenticate(authenticationRequest);
			String jwt = loginService.generateToken();
			return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);
		} catch(BadCredentialsException e) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}



}