package com.businessassistantbcn.gencat.dto;

import com.businessassistantbcn.gencat.dto.output.AllCcaeDto;
import com.businessassistantbcn.gencat.helper.CcaeDeserializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
public class CcaeDtoOutputTest {


    private  static ObjectMapper mapper;

    private  static SimpleModule module;

    private static final String JSON_FILENAME_CCAE = "json/twoCcaeData.json";

    private static final String JSON_FILENAME_CCAE_ERROR_PROPERTIES = "json/twoCcaeDataErrorProperties.json";

    private static String ccaeAsString;
    private static String ccaeErrorAsString;


    @Before
    public void beforeAll() {



    }

    @BeforeAll
    static void setUp() throws URISyntaxException, IOException {

        Path path = Paths.get(CcaeDtoOutputTest.class.getClassLoader().getResource(JSON_FILENAME_CCAE).toURI());

        ccaeAsString = Files.readAllLines(path, StandardCharsets.UTF_8).get(0);

        Path path1 = Paths.get(CcaeDtoOutputTest.class.getClassLoader().getResource(JSON_FILENAME_CCAE_ERROR_PROPERTIES).toURI());

        ccaeErrorAsString = Files.readAllLines(path1, StandardCharsets.UTF_8).get(0);

        mapper = new ObjectMapper();

        module = new SimpleModule();

        module.addDeserializer(AllCcaeDto.class, new CcaeDeserializer());

        mapper.registerModule(module);

    }

    @Test
    void getCcaeDtoInputFromData() throws JsonProcessingException {

        AllCcaeDto allCcaeDto = mapper.readValue(ccaeAsString, AllCcaeDto.class);

        assertEquals("00000000-0000-0000-D7DC-CC770365D8FF", allCcaeDto.getAllCcae().get(0).getId());
        assertEquals(2, allCcaeDto.getAllCcae().size());

    }

    @Test
    void getCcaeDtoInputFromDataWithoutDataProperty() throws JsonProcessingException {

        AllCcaeDto allCcaeDto = mapper.readValue(ccaeErrorAsString, AllCcaeDto.class);
        assertNull(allCcaeDto.getAllCcae());
    }

    /*@Test
    void getCcaeDtoInputFromDataPropertiesType() throws JsonProcessingException {

        CcaeDto ccaeDto = mapper.readValue(ccaeAsString, CcaeDto.class);

        assertEquals(String.class, ccaeDto.getData().get(0).get(1).getClass());
        assertEquals(String.class, ccaeDto.getData().get(0).get(8).getClass());
        assertEquals(String.class, ccaeDto.getData().get(0).get(9).getClass());
        assertEquals(String.class, ccaeDto.getData().get(0).get(10).getClass());

    }*/

}
