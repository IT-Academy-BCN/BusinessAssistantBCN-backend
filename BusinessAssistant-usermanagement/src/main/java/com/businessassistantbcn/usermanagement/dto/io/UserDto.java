package com.businessassistantbcn.usermanagement.dto.io;
import java.util.List;

import com.businessassistantbcn.usermanagement.dto.input.IdOnly;
import com.businessassistantbcn.usermanagement.dto.input.SingUpRequest;
import com.businessassistantbcn.usermanagement.dto.output.UserResponse;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserDto implements IdOnly, SingUpRequest, UserResponse {

	@JsonProperty(value = "user_uuid")
    private String userId;

	@JsonProperty(value = "user_email")
    private String userEmail;

	@JsonProperty(value = "user_role",access = JsonProperty.Access.READ_ONLY)
    private List<String> userRoles;

	@JsonProperty(value = "user_password",access = JsonProperty.Access.WRITE_ONLY)
	private String userPassword;
    
    public UserDto() {}

	public UserDto(String id, String email, List<String> roles, String password) {
		this.userId = id;
		this.userEmail = email;
		this.userRoles = roles;
		this.userPassword = password;
	}
}
