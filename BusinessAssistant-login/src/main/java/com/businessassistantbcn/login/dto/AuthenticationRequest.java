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
	
	private static final long serialVersionUID = 4465572141191414501L;
	
	private String email;
	private String password;
	
}