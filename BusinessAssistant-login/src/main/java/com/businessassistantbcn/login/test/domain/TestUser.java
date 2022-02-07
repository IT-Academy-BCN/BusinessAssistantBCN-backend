package com.businessassistantbcn.login.test.domain;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class TestUser {
	
	private static int COUNTER = 0;
	
	private int id;
	private String email;
	private String password;
	private List<String> authorities;
	
	public TestUser(String email, String password, List<String> authorities) {
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		id = COUNTER++;
	}
	
}