package com.businessassistantbcn.gencat.helper;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class JsonHelperTest {

    private final Integer[] intArray = new Integer[]{0, 1, 2, 3, 4};

    @ParameterizedTest
    @MethodSource("getFilterDtoProvider")
    void filterDtoTest(int offset, int limit, Integer[] expectedArray) {
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
