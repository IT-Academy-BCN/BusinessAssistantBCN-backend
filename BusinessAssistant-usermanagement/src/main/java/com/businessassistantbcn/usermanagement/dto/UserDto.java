package com.businessassistantbcn.usermanagement.dto;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class UserDto {
    
    private String uuid;
    
    private String email;
    
    private List<String> roles;
    
    public UserDto() {}

	public UserDto(String uuid, String email, List<String> role) {
		super();
		this.uuid = uuid;
		this.email = email;
		this.roles = roles;
	}
    

	@JsonGetter("uuid")
    public String getUuid() {
		return uuid;
	}
    
    @JsonSetter("uuid")
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
    
    @JsonGetter("email")
	public String getEmail() {
		return email;
	}

    @JsonSetter("email")
	public void setEmail(String email) {
		this.email = email;
	}

    
    @JsonGetter("roles")
	public List<String> getRoles() {
		return roles;
	}

    @JsonSetter("roles")
	public void setRoles(List<String> role) {
		this.roles = roles;
	}

}
