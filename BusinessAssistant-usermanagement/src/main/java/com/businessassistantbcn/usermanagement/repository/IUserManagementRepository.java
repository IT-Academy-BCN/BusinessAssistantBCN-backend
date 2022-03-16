package com.businessassistantbcn.usermanagement.repository;

import com.businessassistantbcn.usermanagement.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IUserManagementRepository extends MongoRepository<User,String> {
}
