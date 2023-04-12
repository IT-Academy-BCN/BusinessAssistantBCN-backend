package com.businessassistantbcn.usermanagement.dto.io;

import com.businessassistantbcn.usermanagement.document.Role;
import com.businessassistantbcn.usermanagement.dto.output.UserResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@PropertySource("classpath:test.properties")
@Log4j2
public class UserDtoTest {

    @Value("${test.json.uuidField}")
    private String uuidfield;

    @Value("${test.json.emailField}")
    private String emailField;

    @Value("${test.json.roleField}")
    private String roleField;

    @Value("${test.json.passwordField}")
    private String passwordField;

    @Autowired
    private ObjectMapper mapper;

    private UserDto user;

    @BeforeEach
    void setUp() {
        user = new UserDto(UUID.randomUUID().toString(),
                "user@user.com",
                List.of(Role.USER.toString(),Role.ADMIN.toString()),
                "whatever");
    }

    @Test
    @DisplayName("Test mapping input data from json")
    void mappingSingupDataTest(){
        String uuid = user.getUserId();
        String email = user.getUserEmail();
        String password = user.getUserPassword();
        String json =
                "{" +
                        "\""+ uuidfield +"\":\""+ uuid +"\"," +
                        "\""+ emailField +"\":\""+ email +"\"," +
                        "\""+ roleField +"\":[\""+ user.getUserRoles().get(0) +"\",\""+user.getUserRoles().get(1)+"\"]," +
                        "\""+ passwordField +"\":\""+ password +"\" " +
                  "}";
        UserDto userDto = null;
        try {
            userDto = mapper.readValue(json, UserDto.class);
        }catch (JsonProcessingException ex){
            log.error(ex.getMessage());
        }
        //System.out.println(userDto);
        assertEquals(uuid, userDto.getUserId());
        assertEquals(email, userDto.getUserEmail());
        assertEquals(password, userDto.getUserPassword());
        assertNull(userDto.getUserRoles()); //notice role NOT serialized meanwhile is not needed
    }

    @Test
    @DisplayName("Test mapping output user data")
    void mappingUserDataToResponseTest(){
        String json=null;
        try {
            json = mapper.writeValueAsString(user);
        }catch (JsonProcessingException ex){
            log.error(ex.getMessage());
        }
        //System.out.println(("output serialized: "+json));
        assertNotNull(json,"json when serialization is null");
        String expected =
                "{" +
                        "\""+ uuidfield +"\":\""+ user.getUserId() +"\"," +
                        "\""+ emailField +"\":\""+ user.getUserEmail() +"\"," +
                        "\""+ roleField +"\":[\""+ user.getUserRoles().get(0) +"\",\""+user.getUserRoles().get(1)+"\"]" +
                "}";
        //note password not serialized
        assertEquals(expected, json);
    }
}
