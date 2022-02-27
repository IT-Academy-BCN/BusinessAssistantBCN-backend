package com.businessassistantbcn.usermanagement.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.businessassistantbcn.usermanagement.dto.User;

@Component
@Repository
public interface UserManagementRepository extends MongoRepository<User,String>{

}
 
