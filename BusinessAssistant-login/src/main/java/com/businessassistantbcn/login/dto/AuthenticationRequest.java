package com.businessassistantbcn.login.dto;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor // default constructor needed for JSON Parsing
@AllArgsConstructor
@Getter @Setter
public class AuthenticationRequest {
	
	private String email;
	private String password;
	
	@Override
	public int hashCode() {
		return Objects.hash(email, password);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)	return true;
		if(obj == null)	return false;
		if(getClass() != obj.getClass()) return false;
		
		AuthenticationRequest other = (AuthenticationRequest) obj;
		return Objects.equals(email, other.email) && Objects.equals(password, other.password);
	}
	
}