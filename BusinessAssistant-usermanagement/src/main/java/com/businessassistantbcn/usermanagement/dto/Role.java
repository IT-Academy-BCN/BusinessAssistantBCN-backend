package com.businessassistantbcn.usermanagement.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

//@JsonFormat(shape = JsonFormat.Shape.OBJECT);
public enum Role {
    USER("User"),
    ADMIN ("Admin");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    @JsonCreator
    public static Role getRoleFromCode(String value){
        for(Role r: Role.values()){
            if(r.getRole().equals(value)){ //TODO instanceOf ? valueOf ?
                return r;
            }
        }
        return null;
    }


}
