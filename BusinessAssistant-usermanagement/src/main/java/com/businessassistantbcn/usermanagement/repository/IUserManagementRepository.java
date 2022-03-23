package com.businessassistantbcn.usermanagement.repository;

import com.businessassistantbcn.usermanagement.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IUserManagementRepository extends MongoRepository<User,String> {
//    public User findByEmail(String mail);
//    public List<User> findAll();
}
