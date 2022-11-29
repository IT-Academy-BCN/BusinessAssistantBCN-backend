package com.businessassistantbcn.mydata.integration_tests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/*@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")*/
public class MyDataControllerIT {

   /* @Autowired
    private WebTestClient webTestClient;
    private JsonNode[] results;

    //TEST DUMMY
    *//*@Test
    void test() {
        Assertions.assertThat(1).isEqualTo(1);
    }*//*

   @Test
    @Order(1)
    void testSaveUserSearch() {

        webTestClient.post()
                .uri("/businessassistantbcn/api/v1/mydata/mysearches/{user_uuid}","DB3C2A2A-D36E-38C7-8A0C-1B2D3CF2BE57")
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
     void testGetAllSearchesByUser() {

       webTestClient.get()
               .uri("/businessassistantbcn/api/v1/mydata/mysearches/{user_uuid}", "DB3C2A2A-D36E-38C7-8A0C-1B2D3CF2BE57")
               .exchange()
               .expectStatus().isOk()
               .expectBodyList(UserSearch.class);
    }

    @Test
    @Order(3)
    void testGetSearchesResults(){
        webTestClient.get()
                .uri("businessassistantbcn/api/v1/mydata/mysearches/{user_uuid}/search/{search_uuid}","DB3C2A2A-D36E-38C7-8A0C-1B2D3CF2BE57", "9591899E-2ED1-146E-43E8-35095DCB9726")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("results")
                .isArray();
    }*/
}
