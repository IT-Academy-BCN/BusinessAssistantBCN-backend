package com.businessassistantbcn.usermanagement.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class UserCreationDto {
	
	private String email;
	
	private String password;
	
	public UserCreationDto() {}

	public UserCreationDto(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	@JsonGetter("email")
	public String getEmail() {
		return email;
	}

	@JsonSetter("email")
	public void setEmail(String email) {
		this.email = email;
	}

	@JsonGetter("password")
	public String getPassword() {
		return password;
	}

	@JsonSetter("password")
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
