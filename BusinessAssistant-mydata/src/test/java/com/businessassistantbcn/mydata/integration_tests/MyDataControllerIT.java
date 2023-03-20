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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;

import javax.sql.DataSource;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@Import({DBTestConfiguration.class})
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
    static MySQLContainer mySQLContainer = new MySQLContainer()
            .withDatabaseName("businessassistantbcndbTest")
            .withUsername("admin")
            .withPassword("admin");

    @BeforeEach
    public void beforeEach() {
        mySQLContainer.start();
    }

    @AfterEach
    public void afterEach() {
        mySQLContainer.stop();
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

    @Test
    @Order(1)
    void testSaveUserSearch() {

        webTestClient.post()
                .uri(CONTROLLER_BASE_URL + USER_UUID_PATH, USER_UUID)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(requestDto),SaveSearchRequestDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserSearch.class)
                .consumeWith(response -> {
                    UserSearch newUserSearch = response.getResponseBody();
                    assertNotNull(newUserSearch);
                    userSearchesRepository.findOneBySearchUuid(newUserSearch.getSearchUuid())
                        .ifPresent(userSearch -> {
                            assertEquals(newUserSearch.getSearchUuid(), userSearch.getSearchUuid());
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
                .jsonPath("$.results[3].searchUuid").isNotEmpty()
                .jsonPath("$.results[3].userUuid").isEqualTo("DB3C2A2A-D36E-38C7-8A0C-1B2D3CF2BE57")
                .jsonPath("$.results[3].searchDetail").isEqualTo("newSearchDetail")
                .jsonPath("$.results[3].searchName").isEqualTo("newSearchName");

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
                .jsonPath("$.results[2].name[2]", ("IBM Client Center Barcelona"));
    }

    @Test
    @Order(4)
    void deleteUserSearchBySearchUuidTest(){

        final String URI_DELETE_SEARCH = "/mysearches/{user_uuid}/search/{search_uuid}";

        webTestClient.delete()
                .uri(CONTROLLER_BASE_URL + URI_DELETE_SEARCH, USER_UUID, "0602900D-3DE2-257D-54D9-46106EDC0837")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> {
                    UserSearch userSearchDeleted = userSearchesRepository.findOneBySearchUuid("0602900D-3DE2-257D-54D9-46106EDC0837")
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
