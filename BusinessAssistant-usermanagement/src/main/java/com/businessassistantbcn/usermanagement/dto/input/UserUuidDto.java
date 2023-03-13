package com.businessassistantbcn.usermanagement.dto.input;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class UserUuidDto {

    private String uuid;

    public UserUuidDto() {}

    public UserUuidDto(String uuid) {
        this.uuid     = uuid;
    }

    @JsonGetter("uuid")
    public String getUuid() {
        return uuid;
    }

    @JsonSetter("uuid")
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}