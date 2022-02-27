package com.businessassistantbcn.usermanagement.repository;

import com.businessassistantbcn.usermanagement.dto.UserDto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserManagementRepository extends MongoRepository<UserDto,String> {

}
