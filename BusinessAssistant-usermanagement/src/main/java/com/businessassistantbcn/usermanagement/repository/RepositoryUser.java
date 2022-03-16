package com.businessassistantbcn.usermanagement.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.businessassistantbcn.usermanagement.document.User;

@Repository
public interface RepositoryUser extends MongoRepository<User,String>  {
	
	Optional<User> findByuuid(String uuid);
	Optional<User> findByEmail(String email);
	
}