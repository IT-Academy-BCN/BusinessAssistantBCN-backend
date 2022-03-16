package com.businessassistantbcn.usermanagement.dto;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "users")
@Builder
public class UserDto {

	@Field(name="uuid")
	private String uuid;
	@Field(name="email")
	private String email;
	@Field(name="role")
	private String role;
    
    public UserDto() {}

	public UserDto(String uuid, String email, String role) {
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
	public String getRole() {
		return role;
	}

    @JsonSetter("role")
	public void setRole(String role) {
		this.role = role;
	}

}
