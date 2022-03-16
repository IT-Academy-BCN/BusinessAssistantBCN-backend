package com.businessassistantbcn.usermanagement.document;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Document(collection="users")
public class User {
    @Transient
    public static final String USER_SEQUENCE = "users_sequence";
    //@Id
    //private Long id;
    @Field(name="uuid")
    private String uuid;
    @Field(name="email")
    private String email;
    @Field(name="password")
    private String password;
    @Field(name="role")
    private List<Role> roles;



}
