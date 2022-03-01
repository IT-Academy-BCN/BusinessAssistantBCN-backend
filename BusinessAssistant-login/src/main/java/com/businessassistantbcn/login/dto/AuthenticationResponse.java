package com.businessassistantbcn.login.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticationResponse implements Serializable {
	
	private static final long serialVersionUID = 7765417573086420681L;
	
	private final String token;
	
}