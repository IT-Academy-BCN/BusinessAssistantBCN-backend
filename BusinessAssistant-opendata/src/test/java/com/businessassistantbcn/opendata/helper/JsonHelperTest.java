package com.businessassistantbcn.opendata.helper;

import com.businessassistantbcn.opendata.service.externaldata.BigMallsServiceTest;
import com.fasterxml.jackson.databind.JsonNode;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class JsonHelperTest {

    private final Integer[] intArray = new Integer[]{0, 1, 2, 3, 4};
    private static final Logger log = LoggerFactory.getLogger(JsonHelperTest.class);
    static final String JSON_FILENAME = "json/twoBigMallsForJsonHelper.json";
    static String JSON_TEST_FILE;

    @BeforeAll
    public static void initialize() throws URISyntaxException, IOException{
        JSON_TEST_FILE = Files.readAllLines(
                Paths.get(BigMallsServiceTest.class.getClassLoader().getResource(JSON_FILENAME).toURI()),
                StandardCharsets.UTF_8
        ).get(0);
    }


    @Test
    public void deserializeToJsonNodeTest() {
        JsonNode jsonNode = JsonHelper.deserializeToJsonNode(JSON_TEST_FILE);
        assertEquals("Botiga FNAC *El Triangle", jsonNode.get(0).get("name").textValue());
        assertEquals("Sant Andreu", jsonNode.get(1).get("addresses").get(0).get("district_name").textValue());
    }

    @Test
    public void serializeTest() throws IOException {
        JsonNode jsonNode = JsonHelper.deserializeToJsonNode(JSON_TEST_FILE);
        assertEquals(JSON_TEST_FILE, JsonHelper.serialize(jsonNode));
    }

    @ParameterizedTest
    @MethodSource("getFilterDtoProvider")
    public void filterDtoTest(int offset, int limit, Integer[] expectedArray) {
        assertArrayEquals(expectedArray, JsonHelper.filterDto(this.intArray, offset, limit));
    }

    private static Stream<Arguments> getFilterDtoProvider() {
        return Stream.of(
                Arguments.of(0, -1, new Integer[]{0, 1, 2, 3, 4}),
                Arguments.of(10, -1, new Integer[]{}),
                Arguments.of(1, 2, new Integer[]{1, 2}),
                Arguments.of(1, 10, new Integer[]{1, 2, 3, 4})
        );
    }

}
