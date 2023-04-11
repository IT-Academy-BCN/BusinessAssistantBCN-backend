package com.businessassistantbcn.usermanagement.dto;

public interface SingUpRequest extends EmailOnly{
    String getPassword();

    void setPassword(String password); 
}
