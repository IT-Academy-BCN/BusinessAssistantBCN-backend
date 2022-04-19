package com.businessassistantbcn.usermanagement;

import com.businessassistantbcn.usermanagement.document.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserManagementRepository extends ReactiveMongoRepository<User,String> {

    //this line is added, since the field uuid of the User class, is not annotated as an id.
    Mono<User> findByUuid(String uuid);

    //search by email
    Mono<User> findByEmail(String email);
}


