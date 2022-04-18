package com.businessassistantbcn.mydata.helper;

import com.businessassistantbcn.mydata.entities.UserSearch;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
public class JsonHelperTest {

    private JsonNode node;
    private ObjectMapper mapper = new ObjectMapper();
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @BeforeEach
    void init() throws Exception {

        log.info("Getting Json Node from Json");
        String json = "{\n" +
                "    \"searchUuid\": \"14ED11B5-AD19-C53D-E5E6-8B6E4CFD157A\",\n" +
                "    \"userUuid\": \"DB3C2A2A-D36E-38C7-8A0C-1B2D3CF2BE57\",\n" +
                "    \"searchDate\": \"2014-09-15T15:03:23\",\n" +
                "    \"searchName\": \"Vivamus Incorporated\",\n" +
                "    \"searchDetail\": \"Testing\",\n" +
                "    \"searchResult\": {\n" +
                "        \"name\": \"Integration test\",\n" +
                "        \"web\": \"http://www.testing.com\"\n" +
                "    }\n" +
                "}";

        node = mapper.readTree(json);
    }

    //method -> deserializeStringToJsonNode
    @Test
    @DisplayName("Validate Json to JsonNode transformation")
    void shouldGetJsonNodeFromJson() {
        log.info("Running: Validate json to json node transformation at {}", new Date());

        assertAll("node",
                () -> assertEquals("14ED11B5-AD19-C53D-E5E6-8B6E4CFD157A", node.get("searchUuid").textValue(), "Should get searchUuid"),
                () -> assertEquals("DB3C2A2A-D36E-38C7-8A0C-1B2D3CF2BE57", node.get("userUuid").textValue(), "Should get userUuid"),
                () -> assertEquals("2014-09-15T15:03:23", node.get("searchDate").textValue(), "Should get searchDate"),
                () -> assertEquals("Vivamus Incorporated", node.get("searchName").textValue(), "Should get searchName"),
                () -> assertEquals("Testing", node.get("searchDetail").textValue(), "should get searchDetail"),
                () -> assertEquals("Integration test", node.get("searchResult").get("name").textValue(), "should get name searchResult"),
                () -> assertEquals("http://www.testing.com", node.get("searchResult").get("web").textValue(), "should get web searchResult")
        );

    }

    //method -> entityToJsonString
    @Test
    @DisplayName("Validate String to UserSearch transformation")
    void shouldGetStringFromUserSearch() throws Exception {
        log.info("Running: Validate json to json node transformation at {}", new Date());

        UserSearch userSearch = mapper.treeToValue(node, UserSearch.class);
        String stringUserSearch = mapper.writeValueAsString(UserSearch.class);

        assertAll("stringUserSearch",
                () -> assertEquals("14ED11B5-AD19-C53D-E5E6-8B6E4CFD157A", userSearch.getSearchUuid(), "Should get searchUuid"),
                () -> assertEquals("DB3C2A2A-D36E-38C7-8A0C-1B2D3CF2BE57", userSearch.getUserUuid(), "Should get userUuid"),
                () -> assertEquals("2014-09-15T15:03:23Z", userSearch.getSearchDate().toInstant().toString(), "Should get searchDate"),
                () -> assertEquals("Vivamus Incorporated", userSearch.getSearchName(), "Should get searchName"),
                () -> assertEquals("Testing", userSearch.getSearchDetail(), "should get searchDetail"),
                () -> assertEquals("Integration test", userSearch.getSearchResult().get("name").textValue(), "should get name searchResult"),
                () -> assertEquals("http://www.testing.com", userSearch.getSearchResult().get("web").textValue(), "should get web searchResult")
        );

    }

    @Test
    @DisplayName("Validate JsonNode to UserSearch transformation")
    void shouldGetUserSearchFromJsonNode() throws Exception {
        log.info("Running: Validate UserSearch to json node transformation at {}", new Date());

        UserSearch userSearch = mapper.treeToValue(node, UserSearch.class);

        assertAll("userSearch",
                () -> assertEquals("14ED11B5-AD19-C53D-E5E6-8B6E4CFD157A", userSearch.getSearchUuid(), "Should get searchUuid"),
                () -> assertEquals("DB3C2A2A-D36E-38C7-8A0C-1B2D3CF2BE57", userSearch.getUserUuid(), "Should get userUuid"),
                () -> assertEquals("2014-09-15T15:03:23Z", userSearch.getSearchDate().toInstant().toString(), "Should get searchDate"),
                () -> assertEquals("Vivamus Incorporated", userSearch.getSearchName(), "Should get searchName"),
                () -> assertEquals("Testing", userSearch.getSearchDetail(), "should get searchDetail"),
                () -> assertEquals("Integration test", userSearch.getSearchResult().get("name").textValue(), "should get name searchResult"),
                () -> assertEquals("http://www.testing.com", userSearch.getSearchResult().get("web").textValue(), "should get web searchResult")
        );
    }

    //method -> serializeJsonNodeToString
    @Test
    @DisplayName("Validate String to JsonNode transformation")
    void shouldGetStringFromJsonNode() throws Exception {
        log.info("Running: Validate json to json node transformation at {}", new Date());

        String jsonString = mapper.writeValueAsString(node);
        //System.out.println(jsonString);

        assertAll("jsonString",
                () -> assertEquals("14ED11B5-AD19-C53D-E5E6-8B6E4CFD157A", node.get("searchUuid").textValue(), "Should get searchUuid"),
                () -> assertEquals("DB3C2A2A-D36E-38C7-8A0C-1B2D3CF2BE57", node.get("userUuid").textValue(), "Should get userUuid"),
                () -> assertEquals("2014-09-15T15:03:23", node.get("searchDate").textValue(), "Should get searchDate"),
                () -> assertEquals("Vivamus Incorporated", node.get("searchName").textValue(), "Should get searchName"),
                () -> assertEquals("Testing", node.get("searchDetail").textValue(), "should get searchDetail"),
                () -> assertEquals("Integration test", node.get("searchResult").get("name").textValue(), "should get name searchResult"),
                () -> assertEquals("http://www.testing.com", node.get("searchResult").get("web").textValue(), "should get web searchResult")
        );

    }

    @AfterEach
    void finish()  {
        log.info("Test execution finished");
    }
}
