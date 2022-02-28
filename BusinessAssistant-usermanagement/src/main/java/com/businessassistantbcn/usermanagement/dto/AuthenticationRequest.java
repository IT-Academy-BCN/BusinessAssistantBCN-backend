package com.businessassistantbcn.usermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor //need default constructor for JSON Parsing
@AllArgsConstructor
@Getter @Setter
public class AuthenticationRequest implements Serializable {

	private String email;
	private String password;

}