package com.businessassistantbcn.gencat.dto;

import com.businessassistantbcn.gencat.dto.input.CcaeDto;
import com.businessassistantbcn.gencat.service.CcaeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CcaeDtoInputTest {


    @Autowired
    private ObjectMapper objectMapper;

    private static final String JSON_FILENAME_CCAE = "json/twoCcaeData.json";

    private static final String JSON_FILENAME_CCAE_ERROR_PROPERTIES = "json/twoCcaeDataErrorProperties.json";

    private static String ccaeAsString;
    private static String ccaeErrorAsString;



    @BeforeAll
    static void setUp() throws URISyntaxException, IOException {

        Path path = Paths.get(CcaeDtoInputTest.class.getClassLoader().getResource(JSON_FILENAME_CCAE).toURI());

        ccaeAsString = Files.readAllLines(path, StandardCharsets.UTF_8).get(0);

        Path path1 = Paths.get(CcaeDtoInputTest.class.getClassLoader().getResource(JSON_FILENAME_CCAE_ERROR_PROPERTIES).toURI());

        ccaeErrorAsString = Files.readAllLines(path1, StandardCharsets.UTF_8).get(0);

    }

    @Test
    void getCcaeDtoInputFromData() throws JsonProcessingException {

        CcaeDto ccaeDto = objectMapper.readValue(ccaeAsString, CcaeDto.class);

        assertEquals("00000000-0000-0000-D7DC-CC770365D8FF", ccaeDto.getData().get(0).get(1));
        assertEquals(12, ccaeDto.getData().get(0).size());
        assertEquals(2, ccaeDto.getData().size());

    }

    @Test
    void getCcaeDtoInputFromDataWithoutDataProperty() throws JsonProcessingException {

        CcaeDto ccaeDto = objectMapper.readValue(ccaeErrorAsString, CcaeDto.class);
        assertNull(ccaeDto.getData());
    }

    @Test
    void getCcaeDtoInputFromDataPropertiesType() throws JsonProcessingException {

        CcaeDto ccaeDto = objectMapper.readValue(ccaeAsString, CcaeDto.class);

        assertEquals(String.class, ccaeDto.getData().get(0).get(1).getClass());
        assertEquals(String.class, ccaeDto.getData().get(0).get(8).getClass());
        assertEquals(String.class, ccaeDto.getData().get(0).get(9).getClass());
        assertEquals(String.class, ccaeDto.getData().get(0).get(10).getClass());

    }

}
