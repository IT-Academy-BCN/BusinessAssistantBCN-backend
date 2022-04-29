package com.businessassistantbcn.usermanagement.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class UserUuidDto {

    private String uuid;

    private String password;
    
    public UserUuidDto() {}
    
	public UserUuidDto(String uuid, String password) {
		this.uuid     = uuid;
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

    @JsonGetter("password")
    public String getPassword() {
        return password;
    }

    @JsonSetter("password")
    public void setPassword(String password) {
        this.password = password;
    }

}