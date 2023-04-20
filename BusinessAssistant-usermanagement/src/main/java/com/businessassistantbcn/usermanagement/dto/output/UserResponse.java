package com.businessassistantbcn.usermanagement.dto.output;

import java.util.List;

public interface UserResponse {
    String getUserId();

    String getUserEmail();

    List<String> getUserRoles();
}
