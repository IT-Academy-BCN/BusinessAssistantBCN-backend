package com.businessassistantbcn.usermanagement.dto.output;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class UserDto {
	
    
    private String uuid;
    
    private String email;
    
    private List<String> roles;
	private String password;
    
    public UserDto() {}

	public UserDto(String uuid, String email, List<String> roles, String password) {
		super();
		this.uuid  = uuid;
		this.email = email;
		this.roles = roles;
		this.password = password;
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

    
    @JsonGetter("role")
	public List<String> getRoles() {
		return roles;
	}

    @JsonSetter("role")
	public void setRoles(List<String> role) {
		this.roles = role;
	}
	@JsonGetter("password")
	public String getPassword(){return password;}
	@JsonSetter("password")
	public void setPassword(String password){this.password = password;}

}
