package com.businessassistantbcn.mydata.integration_tests;

import com.businessassistantbcn.mydata.config.DBTestConfiguration;
import com.businessassistantbcn.mydata.config.PropertiesConfig;
//import com.businessassistantbcn.mydata.config.WebSecurityTestConfig;
import com.businessassistantbcn.mydata.dto.SaveSearchRequestDto;
import com.businessassistantbcn.mydata.dto.SaveSearchResponseDto;
import com.businessassistantbcn.mydata.entity.UserSearch;
import com.businessassistantbcn.mydata.repository.IUserSearchesRepository;
import com.businessassistantbcn.mydata.service.UserSearchesService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.core.publisher.Mono;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import java.util.*;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@PropertySource("classpath:application-test.properties")
@Import({DBTestConfiguration.class})
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MyDataControllerIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserSearchesService userSearchesService;

    @Autowired
    private PropertiesConfig propertiesConfig;

    @Autowired
    private IUserSearchesRepository userSearchesRepository;

    @Autowired
    private DataSource dataSource;

    private final String CONTROLLER_BASE_URL = "/businessassistantbcn/api/v1/mydata";
    private final String USER_UUID_PATH = "/mysearches/{user_uuid}";
    private final String USER_UUID = "DB3C2A2A-D36E-38C7-8A0C-1B2D3CF2BE57";

    private SaveSearchRequestDto requestDto = new SaveSearchRequestDto();
    private SaveSearchResponseDto responseDto = new SaveSearchResponseDto();
    private ObjectMapper mapper = new ObjectMapper();


    @Container
    private static MySQLContainer<?> mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.27"))
        .withDatabaseName("babcn-myDataTest")
        .withUsername("testuser")
        .withPassword("testpass")
        .withExposedPorts(3306)
        .withEnv("MYSQL_INIT_SCRIPT", "db/schema.sql")
        .withEnv("MYSQL_INIT_SCRIPT", "db/data.sql")
        .withStartupTimeout(Duration.ofSeconds(60));

    @DynamicPropertySource
    private static void setupProperties(DynamicPropertyRegistry registry) {
        mySQLContainer.start();
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @BeforeAll
    void setUp(){

        HashMap<String, String> searchResult = new HashMap<>();
        searchResult.put("name", "Groupe Zannier Espanya");
        searchResult.put("web", "http://www.kidilizgroup.com");

        requestDto.setSearchName("newSearchName");
        requestDto.setSearchDetail("newSearchDetail");
        requestDto.setSearchResult(mapper.valueToTree(searchResult));

    }
    @AfterAll
    void tearDown(){

        String deleteStatement = "DELETE FROM my_searches";
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(deleteStatement);
            statement.execute();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        mySQLContainer.stop();
    }

    @Test
    void testMySQLContainerIsRunning() {
        assertThat(mySQLContainer.isRunning()).isTrue();
    }

    @Test
    @Order(1)
    void testSaveUserSearch() {

        webTestClient.post()
                .uri(CONTROLLER_BASE_URL + USER_UUID_PATH, USER_UUID)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(requestDto),SaveSearchRequestDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(SaveSearchResponseDto.class)
                .consumeWith(response -> {
                    responseDto = response.getResponseBody();
                    assertNotNull(responseDto);
                    userSearchesRepository.findOneBySearchUuid(responseDto.getSearchUuid())
                        .ifPresent(userSearch -> {
                            assertNotNull(userSearch);
                            assertEquals(responseDto.getSearchUuid(), userSearch.getSearchUuid());
                            assertEquals("DB3C2A2A-D36E-38C7-8A0C-1B2D3CF2BE57", userSearch.getUserUuid());
                            assertEquals("newSearchName", userSearch.getSearchName());
                            assertEquals("newSearchDetail", userSearch.getSearchDetail());
                        });
                });

    }

    @Test
    @Order(2)
    void testGetSearchByUserUuid() {

        webTestClient.get()
                .uri(CONTROLLER_BASE_URL + USER_UUID_PATH, USER_UUID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.results").isArray()
                .jsonPath("$.results[2].searchUuid").isNotEmpty()
                .jsonPath("$.results[2].userUuid").isEqualTo("DB3C2A2A-D36E-38C7-8A0C-1B2D3CF2BE57")
                .jsonPath("$.results[2].searchDetail").isEqualTo("searchdetail2")
                .jsonPath("$.results[2].searchName").isEqualTo("searchname2");

    }

    @Test
    @Order(3)
    void testGetSearchesResults() {

        final String URI_ONE_SEARCH = "/mysearches/{user_uuid}/search/{search_uuid}";

        webTestClient.get()
                .uri(CONTROLLER_BASE_URL + URI_ONE_SEARCH, USER_UUID, "0602900D-3DE2-257D-54D9-46106EDC0837")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type, application/json")
                .expectBody()
                .jsonPath("$.results").isArray();
    }

    @Test
    @Order(4)
    void deleteUserSearchBySearchUuidTest(){

        final String URI_DELETE_SEARCH = "/mysearches/{user_uuid}/search/{search_uuid}";

        webTestClient.delete()
                .uri(CONTROLLER_BASE_URL + URI_DELETE_SEARCH, USER_UUID, "8480788D-1FE0-035D-32D7-24984EBA8615")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> {
                    UserSearch userSearchDeleted = userSearchesRepository.findOneBySearchUuid("8480788D-1FE0-035D-32D7-24984EBA8615")
                            .orElse(null);
                    assertNull(userSearchDeleted);
                });
    }

    @Test
    @Order(5)
    void testSaveUserSearch_WhenExceedsLimit(){

        for (int i = 0; i < propertiesConfig.getLimitValue(); i++) {

            testSaveUserSearch();

            if (i == propertiesConfig.getLimitValue()){
                assertNull(responseDto);
            } else {
                assertNotNull(responseDto);
            }
        }
    }

}
