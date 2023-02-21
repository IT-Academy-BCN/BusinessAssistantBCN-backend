package com.businessassistantbcn.mydata.integration_tests;

import com.businessassistantbcn.mydata.config.DBTestConfiguration;
import com.businessassistantbcn.mydata.config.PropertiesConfig;
//import com.businessassistantbcn.mydata.config.WebSecurityTestConfig;
import com.businessassistantbcn.mydata.controller.MydataController;
import com.businessassistantbcn.mydata.entity.UserSearch;
import com.businessassistantbcn.mydata.repository.IUserSearchesRepository;
//import com.businessassistantbcn.mydata.repository.MyDataRepository;
import com.businessassistantbcn.mydata.service.UserSearchesService;
import com.fasterxml.jackson.databind.JsonNode;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
//import org.h2.engine.User;
//import org.springframework.security.test.context.support.WithMockUser;

import javax.sql.DataSource;
import java.util.Base64;
import java.util.Collections;
import java.util.logging.Logger;


@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Import({DBTestConfiguration.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@EnableAutoConfiguration(exclude={SqlInitializationAutoConfiguration.class})
//@AutoConfigureMockMvc
//@ContextConfiguration(classes = {WebSecurityTestConfig.class})
//@Sql({ "classpath:schema.sql", "classpath:data.sql" })
class MyDataControllerIT {

    Logger logger = Logger.getLogger(MyDataControllerIT.class.getName());

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserSearchesService userSearchesService;

    @Autowired
    private PropertiesConfig propertiesConfig;

    @Autowired
    private IUserSearchesRepository userSearchesRepository;

    @LocalServerPort
    private int port;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Autowired
    private DataSource dataSource;


    private JsonNode[] results;

    private String getBasicAuthHeader(String username, String password) {
        String auth = username + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        String headerValue = "Basic " + encodedAuth;
        return headerValue;
    }


//    @BeforeAll
//    void setUp() throws Exception {
//
//        User userLogin = new User(username, password, Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
//        dataSource.getConnection().createStatement().execute("CREATE USER IF NOT EXISTS " + username + " PASSWORD '" + password + "' ADMIN");
//
//
//    }


    @Test
    void testCredentials(){

        Assertions.assertThat(password).isEqualTo("sa");

        String headerValue = getBasicAuthHeader(username, password);
        logger.info("HEADER VALUE: " + headerValue);

    }


    //TEST DUMMY
//    @Test
//    void test() {
//
//        webTestClient.get().uri("/api/mydata").header(HttpHeaders.AUTHORIZATION, getBasicAuthHeader(username, password))
//                .exchange().expectStatus().isOk();
//
//    }



    @Test
    @Order(1)
//    @WithUserDetails(value= "sa", userDetailsServiceBeanName = "userDetailsService")
    void testSaveUserSearch() {

        webTestClient.post()
                .uri("/businessassistantbcn/api/v1/mydata/mysearches/{user_uuid}", "DB3C2A2A-D36E-38C7-8A0C-1B2D3CF2BE57")
//                .header(HttpHeaders.AUTHORIZATION,getBasicAuthHeader(username,password))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\n" +
                        "    \"searchName\": \"Vivamus Incorporated\",\n" +
                        "    \"searchDetail\": \"Testing\",\n" +
                        "    \"searchResult\": {\n" +
                        "        \"name\": \"Integration test\",\n" +
                        "        \"web\": \"http://www.testing.com\"\n" +
                        "    }\n" +
                        "}")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(2)
//    @WithUserDetails(value= "sa", userDetailsServiceBeanName = "userDetailsService")
    void testGetAllSearchesByUser() {

        webTestClient.get()
                .uri("/businessassistantbcn/api/v1/mydata/mysearches/{user_uuid}", "DB3C2A2A-D36E-38C7-8A0C-1B2D3CF2BE57")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserSearch.class);
    }

    @Test
    @Order(3)
    void testGetSearchesResults() {
        webTestClient.get()
                .uri("businessassistantbcn/api/v1/mydata/mysearches/{user_uuid}/search/{search_uuid}", "DB3C2A2A-D36E-38C7-8A0C-1B2D3CF2BE57", "9591899E-2ED1-146E-43E8-35095DCB9726")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("results")
                .isArray();
    }


}
