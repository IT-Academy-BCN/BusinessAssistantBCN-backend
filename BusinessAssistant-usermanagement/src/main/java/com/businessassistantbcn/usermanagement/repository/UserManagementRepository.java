package com.businessassistantbcn.usermanagement.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.businessassistantbcn.usermanagement.model.UserManagement;



public interface UserManagementRepository extends MongoRepository<UserManagement,Long>{

}
 