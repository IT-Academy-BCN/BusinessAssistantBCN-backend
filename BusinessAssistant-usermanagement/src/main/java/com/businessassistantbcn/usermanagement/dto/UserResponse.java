package com.businessassistantbcn.usermanagement.dto;

import java.util.List;

public interface UserResponse {
    String getUserId();

    String getUserEmail();

    List<String> getUserRoles();
}
