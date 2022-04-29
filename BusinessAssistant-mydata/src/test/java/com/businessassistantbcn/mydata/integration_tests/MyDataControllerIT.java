package com.businessassistantbcn.mydata.integration_tests;

import com.businessassistantbcn.mydata.dto.GenericSearchesResultDto;
import com.businessassistantbcn.mydata.dto.SaveSearchRequestDto;
import com.businessassistantbcn.mydata.dto.SearchResultsDto;
import com.businessassistantbcn.mydata.entities.UserSearch;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
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
