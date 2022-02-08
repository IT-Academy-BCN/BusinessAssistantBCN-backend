package com.businessassistantbcn.opendata.helper;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonHelperTest {

    private Integer[] intArray = new Integer[]{0, 1, 2, 3, 4};

    @Test
    public void deserializeToListTest(){
    }
    @Test
    public void deserializeToJsonNodeTest(){
    }

    @Test
    public void serializeTest(){
    }

    @ParameterizedTest
    @MethodSource("getFilterDtoProvider")
    public void filterDtoTest(int offset, int limit, Integer[] expectedArray) {
        assertTrue(Arrays.equals(expectedArray, JsonHelper.filterDto(this.intArray, offset, limit)));
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
