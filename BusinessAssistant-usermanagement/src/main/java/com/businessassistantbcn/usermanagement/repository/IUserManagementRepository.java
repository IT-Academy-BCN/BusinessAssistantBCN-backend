package com.businessassistantbcn.usermanagement.repository;

import com.businessassistantbcn.usermanagement.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IUserManagementRepository extends MongoRepository<User,String> {
    public User findByEmail(String mail);
    public List<User> findAll();
}
