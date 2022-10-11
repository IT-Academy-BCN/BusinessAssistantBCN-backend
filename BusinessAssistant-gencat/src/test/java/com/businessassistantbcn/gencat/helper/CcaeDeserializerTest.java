package com.businessassistantbcn.gencat.helper;

import com.businessassistantbcn.gencat.dto.io.CcaeDto;
import com.businessassistantbcn.gencat.dto.io.CodeInfoDto;
import com.businessassistantbcn.gencat.exception.ExpectedJSONFieldNotFoundException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CcaeDeserializerTest {

    private  static ObjectMapper mapper;
    private static CcaeDeserializer ccaeDeserializer;

    private static final String JSON_FILENAME_CCAE = "json/twoCcaeData.json";
    private static final String JSON_FILENAME_CCAE_ERROR_PROPERTIES = "json/twoCcaeDataErrorProperties.json";
    //randomDataForTesting.json ---> debe tener estructura idéntica a CCAE.json
    private static final String JSON_FILENAME_RANDOM_DATA_FOR_TESTING = "json/randomDataForTesting.json";
    private static final String JSON_FILENAME_CCAE_MISSING_FIELD = "json/twoCcaeDataMissingField.json";

    private static String ccaeAsString;
    private static String ccaeErrorPropertiesAsString;
    private static String ccaeErrorDataAsString;
    private static String ccaeMissingFieldString;

    private static List<CcaeDto> responseDto;

    @BeforeAll
    static void setUp() throws URISyntaxException, IOException {

        Path path = Paths.get(Objects.requireNonNull(CcaeDeserializerTest.class.getClassLoader().getResource(JSON_FILENAME_CCAE)).toURI());
        ccaeAsString = Files.readAllLines(path, StandardCharsets.UTF_8).get(0);

        Path path1 = Paths.get(Objects.requireNonNull(CcaeDeserializerTest.class.getClassLoader().getResource(JSON_FILENAME_CCAE_ERROR_PROPERTIES)).toURI());
        ccaeErrorPropertiesAsString = Files.readAllLines(path1, StandardCharsets.UTF_8).get(0);

        Path path2 = Paths.get(Objects.requireNonNull(CcaeDeserializerTest.class.getClassLoader().getResource(JSON_FILENAME_RANDOM_DATA_FOR_TESTING)).toURI());
        ccaeErrorDataAsString = Files.readAllLines(path2, StandardCharsets.UTF_8).get(0);

        Path path3 = Paths.get(Objects.requireNonNull(CcaeDeserializerTest.class.getClassLoader().getResource(JSON_FILENAME_CCAE_MISSING_FIELD)).toURI());
        ccaeMissingFieldString = Files.readAllLines(path3, StandardCharsets.UTF_8).get(0);

        mapper = new ObjectMapper();
        ccaeDeserializer = new CcaeDeserializer();

        responseDto = new ArrayList<>();

        CcaeDto ccaeDto1 = new CcaeDto();
        String id1 = "00000000-0000-0000-D7DC-CC770365D8FF";
        String type1 = "Secció";
        String idCodeInfo1 = "A";
        String codeDescription1 = "Agricultura, ramaderia, silvicultura i pesca";
        CodeInfoDto codeInfoDto1 = new CodeInfoDto(idCodeInfo1, codeDescription1);
        ccaeDto1.setId(id1);
        ccaeDto1.setType(type1);
        ccaeDto1.setCode(codeInfoDto1);
        responseDto.add(ccaeDto1);

        CcaeDto ccaeDto2 = new CcaeDto();
        String id2 = "00000000-0000-0000-2335-839767DDAEAB";
        String type2 = "Divisió";
        String idCodeInfo2 = "01";
        String codeDescription2 = "Agricultura, ramaderia, caça i activitats dels serveis que s'hi relacionen";
        CodeInfoDto codeInfoDto2 = new CodeInfoDto(idCodeInfo2, codeDescription2);
        ccaeDto2.setId(id2);
        ccaeDto2.setType(type2);
        ccaeDto2.setCode(codeInfoDto2);
        responseDto.add(ccaeDto2);
    }

    @Test
    void getCcaeDtoInputFromData() throws JsonProcessingException {

        Object data = mapper.readValue(ccaeAsString, Object.class);

        List<CcaeDto> ccaeDtos = ccaeDeserializer.deserialize(data);

        assertThat(ccaeDtos).hasSize(2);
        assertThat(ccaeDtos).usingRecursiveFieldByFieldElementComparator().containsExactlyElementsOf(responseDto);
    }

    @Test
    void getCcaeDtoInputFromDataWithoutDataProperty() throws JsonProcessingException {

        Object data = mapper.readValue(ccaeErrorPropertiesAsString, Object.class);

        assertThatExceptionOfType(ExpectedJSONFieldNotFoundException.class)
                .isThrownBy(() -> ccaeDeserializer.deserialize(data))
                .withMessage("Field 'data' was not found");
    }

    @Test
    void getCcaeDtoInputFromDataPropertiesType() throws JsonProcessingException {

        Object data = mapper.readValue(ccaeErrorDataAsString, Object.class);

        assertThatExceptionOfType(ExpectedJSONFieldNotFoundException.class)
                .isThrownBy(() -> ccaeDeserializer.deserialize(data))
                .withMessage("The object must be an instance of LinkedHashMap");
    }

    @Test
    void getCcaeDtoInputFromDataWithMissingFields() throws JsonProcessingException {

        Object data = mapper.readValue(ccaeMissingFieldString, Object.class);

        assertThatExceptionOfType(ExpectedJSONFieldNotFoundException.class)
                .isThrownBy(() -> ccaeDeserializer.deserialize(data))
                .withMessage("JSON field not found");
    }
}
