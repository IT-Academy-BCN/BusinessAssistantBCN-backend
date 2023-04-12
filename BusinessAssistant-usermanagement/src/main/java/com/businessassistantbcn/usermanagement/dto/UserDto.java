package com.businessassistantbcn.usermanagement.dto;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ToString
@EqualsAndHashCode
public class UserDto implements IdOnly, SingUpRequest, UserResponse {
	
    
    private String userId;
    
    private String userEmail;
    
    private List<String> userRoles;
	private String userPassword;
    
    public UserDto() {}

	public UserDto(String id, String email, List<String> roles, String password) {
		this.userId = id;
		this.userEmail = email;
		this.userRoles = roles;
		this.userPassword = password;
	}

	@Override
	public void encodePassword() {
		userPassword = new BCryptPasswordEncoder(12).encode(userPassword); // Strength set as 12;
	}
    

	@JsonGetter("uuid")
    public String getUserId() {
		return userId;
	}
    
    @JsonSetter("uuid")
	public void setUserId(String userId) {
		this.userId = userId;
	}
    
    @JsonGetter("email")
	public String getUserEmail() {
		return userEmail;
	}

    @JsonSetter("email")
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

    
    @JsonGetter("role")
	public List<String> getUserRoles() {
		return userRoles;
	}

    @JsonSetter("role")
	public void setUserRoles(List<String> role) {
		this.userRoles = role;
	}
	@JsonGetter("password")
	public String getUserPassword(){return userPassword;}



	@JsonSetter("password")
	public void setUserPassword(String userPassword){this.userPassword = userPassword;}

}
