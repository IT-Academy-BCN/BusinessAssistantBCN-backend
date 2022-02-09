package com.businessassistantbcn.opendata.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import springfox.documentation.spring.web.json.Json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class JsonHelperTest {

    private final Integer[] intArray = new Integer[]{0, 1, 2, 3, 4};

    @Test
    public void deserializeToJsonNodeTest() throws IOException {
        String jsonString = new String(Files.readAllBytes(
            Paths.get("src/test/java/com/businessassistantbcn/opendata/helper/twoBigMallsForTesting.json")
        ));
        JsonNode jsonNode = JsonHelper.deserializeToJsonNode(jsonString);
        assertEquals("Centre Comercial El Corte Ingles", jsonNode.get(0).get("name").textValue());
        assertEquals("Eixample", jsonNode.get(1).get("addresses").get(0).get("district_name").textValue());
    }

    @Test
    public void serializeTest() throws IOException {
        String jsonString = new String(Files.readAllBytes(
            Paths.get("src/test/java/com/businessassistantbcn/opendata/helper/twoBigMallsForTesting.json")
        ));
        JsonNode jsonNode = JsonHelper.deserializeToJsonNode(jsonString);
        assertEquals(jsonString, JsonHelper.serialize(jsonNode));
    }

    @ParameterizedTest
    @MethodSource("getFilterDtoProvider")
    public void filterDtoTest(int offset, int limit, Integer[] expectedArray) {
        assertArrayEquals(expectedArray, JsonHelper.filterDto(this.intArray, offset, limit));
    }

    private static Stream<Arguments> getFilterDtoProvider() {
        return Stream.of(
            Arguments.of(0, -1, new Integer[]{0, 1, 2, 3, 4}), // size
            Arguments.of(10, -1, new Integer[]{}),
            Arguments.of(1, 2, new Integer[]{1, 2}),
            Arguments.of(1, 10, new Integer[]{1, 2, 3, 4})
        );
    }

}
