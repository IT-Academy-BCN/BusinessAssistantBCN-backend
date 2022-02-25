package com.businessassistantbcn.usermanagement.dto;
import com.businessassistantbcn.usermanagement.document.Role;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class UserDto {
	
    
    private String uuid;
    
    private String email;
    
    private Role role;
    
    

    public UserDto() {}


	public UserDto(String uuid, String email, Role role) {
		super();
		this.uuid = uuid;
		this.email = email;
		this.role = role;
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
	public Role getRole() {
		return role;
	}

    @JsonSetter("role")
	public void setRole(Role role) {
		this.role = role;
	}

}
