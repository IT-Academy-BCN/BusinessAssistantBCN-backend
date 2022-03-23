package com.businessassistantbcn.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticationResponse {
	
	private final String token;
	
}