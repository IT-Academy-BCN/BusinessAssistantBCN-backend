package com.businessassistantbcn.login.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor //need default constructor for JSON Parsing
@AllArgsConstructor
@Getter @Setter
public class AuthenticationRequest implements Serializable {
	
	private String email;
	private String password;
	
}