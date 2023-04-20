package com.businessassistantbcn.usermanagement.dto.io;
import java.util.List;

import com.businessassistantbcn.usermanagement.dto.input.IdOnly;
import com.businessassistantbcn.usermanagement.dto.input.SingUpRequest;
import com.businessassistantbcn.usermanagement.dto.output.UserResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
public class UserDto implements IdOnly, SingUpRequest, UserResponse {

	@JsonProperty(value = "user_uuid")
    private String userId;

	@JsonProperty(value = "user_email")
    private String userEmail;

	@JsonProperty(value = "user_role",access = JsonProperty.Access.READ_ONLY)
    private List<String> userRoles;

	@JsonProperty(value = "user_password",access = JsonProperty.Access.WRITE_ONLY)
	private String userPassword;

	//no constructor till is needed, use builder instead
}
