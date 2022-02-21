package com.businessassistantbcn.usermanagement.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.businessassistantbcn.usermanagement.dto.User;


public interface UserManagementRepository extends MongoRepository<User,Long>{

}
 