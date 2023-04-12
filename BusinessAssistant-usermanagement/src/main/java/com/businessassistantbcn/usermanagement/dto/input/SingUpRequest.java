package com.businessassistantbcn.usermanagement.dto.input;

import com.businessassistantbcn.usermanagement.dto.input.EmailOnly;

public interface SingUpRequest extends EmailOnly {
    String getUserPassword();

}
