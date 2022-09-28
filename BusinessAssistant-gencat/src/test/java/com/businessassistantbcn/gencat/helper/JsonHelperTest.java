package com.businessassistantbcn.gencat.helper;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JsonHelperTest {

    private final Integer[] intArray = new Integer[]{0, 1, 2, 3, 4};

    @ParameterizedTest
    @MethodSource("getFilterDtoProvider")
    public void filterDtoTest(int offset, int limit, Integer[] expectedArray) {
        assertArrayEquals(expectedArray, JsonHelper.filterDto(intArray, offset, limit));
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
