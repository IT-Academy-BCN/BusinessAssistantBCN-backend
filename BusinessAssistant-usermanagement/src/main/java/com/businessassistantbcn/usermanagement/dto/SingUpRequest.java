package com.businessassistantbcn.usermanagement.dto;

public interface SingUpRequest extends EmailOnly{
    String getUserPassword();

    void encodePassword();
}
