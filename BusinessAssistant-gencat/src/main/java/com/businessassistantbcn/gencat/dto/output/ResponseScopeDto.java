package com.businessassistantbcn.gencat.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseScopeDto {
    private String idScope;
    private String scope;

    // constructor, getters, setters, etc.

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ResponseScopeDto)) {
            return false;
        }
        ResponseScopeDto other = (ResponseScopeDto) o;
        return Objects.equals(scope, other.scope);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scope);
    }
}

