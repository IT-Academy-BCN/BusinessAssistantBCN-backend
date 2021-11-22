package com.businessassistantbcn.login.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/v1")
public class LoginController {

	@Autowired
    public LoginController(){
    }
	
	/**
	 * -Post que retorna un toke
	 * @param email
	 * @param password
	 * @return
	 */
	@PostMapping (value="/api/login")
    public String login(@RequestParam("email") String email,
    					@RequestParam("password") String password) {
    	String token = getJWTToken(email,password);
    	return "token:" + token;   	    	
    }
    
    
    /*
     * Dejo comentado  GrantedAuthorithy por si mas adelante 
     * se implementa algo sobre permisos o roles
     */
    private String getJWTToken(String email, String password) {
    	String secretKey = "56589pp05s";
    	String userMail = "jvicente@gmail.com";    	
    	if(email.equals(userMail) && password.equals(secretKey)) {    		
//    		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
//    				.commaSeparatedStringToAuthorityList("ROLE_USER");		
    		String token = Jwts
    				.builder()
    				.setId("softtekJWT")
    				.setSubject(email)
//    				.claim("authorities", grantedAuthorities.stream()
//    									.map(GrantedAuthority::getAuthority)
//    									.collect(Collectors.toList()))
    				.setIssuedAt(new Date(System.currentTimeMillis() + 60000))
    				//.signWith(SignatureAlgorithm.HS512,
    				.signWith(SignatureAlgorithm.HS256,
    						secretKey.getBytes()).compact();		
    		return token;
    		
    	}else {
    		return "Error: Auth Error, Token Unavailable.";
    	}
	}
    

	@GetMapping("/test")
    public String test() {
        return "Hello from BusinessAssistant Barcelona!!!";
    }

	
	
}
