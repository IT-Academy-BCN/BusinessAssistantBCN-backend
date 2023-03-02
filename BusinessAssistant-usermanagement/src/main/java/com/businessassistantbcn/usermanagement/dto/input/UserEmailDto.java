package com.businessassistantbcn.usermanagement.dto.input;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class UserEmailDto {

    private String email;

    private String password;

    public UserEmailDto() {}

    public UserEmailDto(String email, String password) {
        super();
        this.email = email;
        this.password = password;
    }

    @JsonGetter("email")
    public String getEmail() {
        return email;
    }

    @JsonSetter("email")
    public void setEmail(String email) {
        this.email = email;
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
