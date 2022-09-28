package com.businessassistantbcn.gencat.helper;

import com.businessassistantbcn.gencat.dto.io.CcaeDto;
import com.businessassistantbcn.gencat.exception.IncorrectJsonFormatException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CcaeDeserializerTest {


    private  static ObjectMapper mapper;


    private static CcaeDeserializer ccaeDeserializer;

    private static final String JSON_FILENAME_CCAE = "json/twoCcaeData.json";

    private static final String JSON_FILENAME_CCAE_ERROR_PROPERTIES = "json/twoCcaeDataErrorProperties.json";

    private static final String JSON_FILENAME_RANDOM_DATA_FOR_TESTING = "json/randomDataForTesting.json";

    private static String ccaeAsString;

    private static String ccaeErrorProperiesAsString;

    private static String ccaeErrorDataAsString;


    @BeforeAll
    static void setUp() throws URISyntaxException, IOException {

        Path path = Paths.get(CcaeDeserializerTest.class.getClassLoader().getResource(JSON_FILENAME_CCAE).toURI());

        ccaeAsString = Files.readAllLines(path, StandardCharsets.UTF_8).get(0);

        Path path1 = Paths.get(CcaeDeserializerTest.class.getClassLoader().getResource(JSON_FILENAME_CCAE_ERROR_PROPERTIES).toURI());

        ccaeErrorProperiesAsString = Files.readAllLines(path1, StandardCharsets.UTF_8).get(0);

        Path path2 = Paths.get(CcaeDeserializerTest.class.getClassLoader().getResource(JSON_FILENAME_RANDOM_DATA_FOR_TESTING).toURI());

        ccaeErrorDataAsString = Files.readAllLines(path2, StandardCharsets.UTF_8).get(0);

        mapper = new ObjectMapper();

        ccaeDeserializer = new CcaeDeserializer();
    }

    @Test
    void getCcaeDtoInputFromData() throws JsonProcessingException {

        Object data = mapper.readValue(ccaeAsString, Object.class);

        List<CcaeDto> ccaeDtos = ccaeDeserializer.deserialize(data);

        //CcaeDto[] deserialize = ccaeDeserializer.deserialize(data);

        assertEquals("00000000-0000-0000-D7DC-CC770365D8FF", ccaeDtos.get(0).getId());
        assertEquals(2, ccaeDtos.size());

        /*assertEquals("00000000-0000-0000-D7DC-CC770365D8FF", deserialize[0].getId());
        assertEquals(2, deserialize.length);*/

    }

    @Test
    void getCcaeDtoInputFromDataWithoutDataProperty() throws JsonProcessingException {

        Object data = mapper.readValue(ccaeErrorProperiesAsString, Object.class);
        IncorrectJsonFormatException exception = assertThrows(IncorrectJsonFormatException.class, () -> {
            ccaeDeserializer.deserialize(data);
        });
        assertThat("Field 'data' does not found").isEqualTo(exception.getMessage());
    }

    @Test
    void getCcaeDtoInputFromDataPropertiesType() throws JsonProcessingException {

        Object data = mapper.readValue(ccaeErrorDataAsString, Object.class);
        IncorrectJsonFormatException exception = assertThrows(IncorrectJsonFormatException.class, () -> {
            ccaeDeserializer.deserialize(data);
        });
        assertThat("The object must be a instance of LinkedHashMap").isEqualTo(exception.getMessage());

    }

}
