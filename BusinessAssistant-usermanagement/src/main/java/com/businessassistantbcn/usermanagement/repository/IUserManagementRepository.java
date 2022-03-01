package com.businessassistantbcn.usermanagement.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.businessassistantbcn.usermanagement.document.User;
@Repository
public interface IUserManagementRepository extends MongoRepository<User,String>{
        //this line is added, since the field uuid of the User class, is not annotated as an id.
        Optional<User> findByUuid(String uuid);

        //search by email
        Optional<User> findByEmail(String email);


}
