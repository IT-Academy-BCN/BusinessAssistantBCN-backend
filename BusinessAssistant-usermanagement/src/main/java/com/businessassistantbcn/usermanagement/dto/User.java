package com.businessassistantbcn.usermanagement.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.persistence.Id;
import javax.persistence.Transient;

@Getter
@Setter
@AllArgsConstructor
@Document(collection="users")
public class User {
    @Transient
    public static final String USER_SEQUENCE = "users_sequence";

    @Id
    private Long id;
    @Field(name="uuid")
    private String uuid;
    @Field(name="email")
    private String email;
    @Field(name="password")
    private String password;
    @Field(name="role")
    private Role role;



}
