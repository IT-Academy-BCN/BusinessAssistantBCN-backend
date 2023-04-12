package com.businessassistantbcn.usermanagement.helper;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import com.businessassistantbcn.usermanagement.dto.SingUpRequest;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.businessassistantbcn.usermanagement.document.Role;
import com.businessassistantbcn.usermanagement.document.User;

import com.businessassistantbcn.usermanagement.dto.UserDto;

@ExtendWith(SpringExtension.class)
public class DtoHelperTest {

    User user;
    UserDto userDto;
    List<Role> roles;
    List<String> rolesString;

    @Test
    public void test_convertToDto() {
        roles = new ArrayList<Role>();

        roles.add(Role.ADMIN);
        roles.add(Role.USER);
        user = new User(new ObjectId("63d9666bbdf0196d2c766fa0"),"7e10fe51-772e-441f-874d-1c03dee79ad9",
                "user@Dto.es", "1234", roles, System.currentTimeMillis());

        userDto = DtoHelper.convertToUserDtoFromUser(user);

        assertEquals("7e10fe51-772e-441f-874d-1c03dee79ad9", userDto.getUserId());
        assertEquals("1234", userDto.getUserPassword());
        assertEquals("user@Dto.es", userDto.getUserEmail());
    }


    @Test
    public void convertToUserFromSingupTest() {
        SingUpRequest singup = new UserDto(null,"user@Dto.es",null, "1234");
        user = DtoHelper.convertToUserFromSingup(singup);
        assertEquals("user@Dto.es", user.getEmail());
        assertEquals("1234", user.getPassword());
        assertNotNull(user.getUuid());
    }

    @Test
    public void test_convertToUserRoles() {
        rolesString = new ArrayList<String>();
        rolesString.add("USER");
        rolesString.add("ADMIN");
        UserDto userDto = new UserDto("7e10fe51-772e-441f-874d-1c03dee79ad9",
                "user@Dto.es", rolesString, "12345");
        User user = new User(new ObjectId("63d9666bbdf0196d2c766fa0"),"7e10fe51-772e-441f-874d-1c03dee79ad9",
                "user@Dto.es", "1234", null, System.currentTimeMillis());

        roles = new ArrayList<Role>();
        roles.add(Role.USER);
        roles.add(Role.ADMIN);

        assertEquals(roles, DtoHelper.convertToUserRoles(rolesString));
    }

    @Test
    public void test_convertToUserDtoRoles() {
        roles = new ArrayList<Role>();
        roles.add(Role.USER);
        roles.add(Role.ADMIN);

        User user = new User(new ObjectId("63d9666bbdf0196d2c766fa0"),"7e10fe51-772e-441f-874d-1c03dee79ad9",
                "user@Dto.es", "1234", roles, System.currentTimeMillis());


        rolesString = new ArrayList<String>();
        rolesString.add("USER");
        rolesString.add("ADMIN");

        assertEquals(rolesString,  DtoHelper.convertToUserDtoRoles(roles));
    }
}