package com.businessassistantbcn.usermanagement.document;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Transient;

@Getter
@Setter
@AllArgsConstructor
@Document(collection="users")
public class User {
    
	@Transient
    public static final String USER_SEQUENCE = "users_sequence";
    
	// 		******** _ID NUMERAL AUTOMÁTICO DE MONGO ***************
    
	@Id
	@Field(name="uuid") //iD PROPIO EN FORMA DE STRING
    private String uuid; 
    
	@Field(name="email") 
    @Indexed(unique=true) //INDEXAMOS EL CORREO COMO VALOR ÚNICO Y QUE NO PERMITA REPETICIÓN
    private String email;
    
	@Field(name="password")
    private String password;
    
	@Field(name="role")
    private Role role;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public static String getUserSequence() {
		return USER_SEQUENCE;
	}



}