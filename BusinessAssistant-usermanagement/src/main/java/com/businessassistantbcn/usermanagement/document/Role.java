package com.businessassistantbcn.usermanagement.document;

public enum Role {
    USER("USER"),
    ADMIN("ADMIN");

    private String name;

    Role(String name) {
        this.name = name;
    }

    public String toString(){
        return this.name;
    }
}
