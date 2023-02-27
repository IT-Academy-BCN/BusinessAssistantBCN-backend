package com.businessassistantbcn.gencat.dto.output;

public class ScopeDto {
    private String idScope;
    private String scope;

    public ScopeDto(String idScope, String scope) {
        this.idScope = idScope;
        this.scope = scope;
    }

    public String getIdScope() {
        return idScope;
    }

    public String getScope() {
        return scope;
    }

}
