package com.businessassistantbcn.usermanagement.repository;



import org.springframework.data.mongodb.repository.MongoRepository;

import com.businessassistantbcn.usermanagement.document.User;





public interface UserManagementRepository extends MongoRepository<User,String>{

}
 