package com.businessassistantbcn.gencat.service;

import com.businessassistantbcn.gencat.config.PropertiesConfig;
import com.businessassistantbcn.gencat.dto.GenericResultDto;
import com.businessassistantbcn.gencat.dto.io.CcaeDto;
import com.businessassistantbcn.gencat.dto.output.RaiscResponseDto;
import com.businessassistantbcn.gencat.dto.output.ResponseScopeDto;
import com.businessassistantbcn.gencat.proxy.HttpProxy;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RaiscServiceTest {

    @MockBean
    private HttpProxy httpProxy;

    @MockBean
    private PropertiesConfig config;
    @Autowired
    @InjectMocks
    private RaiscService raiscService;
//    private static ObjectMapper mapper;

//    private static final String JSON_ALL_RAISC_SCOPE_DATA = "json/allRaiscData.json";
//    private static String raiscResponseDtosAllData;

//    @BeforeAll
//    static void beforeAll() throws URISyntaxException, IOException {
//        String allDataAsString = Files.readAllLines(Paths.get(String.class
//                .getClassLoader().getResource(JSON_ALL_RAISC_SCOPE_DATA).toURI()), StandardCharsets.UTF_8).get(0);
//        mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        raiscResponseDtosAllData = mapper.readValue(allDataAsString, String.class);
//    }
    @Test
    void getScopesTest() throws MalformedURLException {

        when(config.getDs_scopes()).thenReturn("https://analisi.transparenciacatalunya.cat/api/views/khxn-nv6a/rows.json");


       // when(httpProxy.getRequestData(new URL(config.getDs_scopes()), String.class)).thenReturn(Mono.just(raiscResponseDtosAllData));
       when(raiscService.getScopes(0,1)).thenReturn(getGenericResultDtoScope());


    }
    private <T> T getGenericResultDtoScope() {
        List<ResponseScopeDto> responseScopeDtos = new ArrayList<>();
        responseScopeDtos.add(new ResponseScopeDto("10", "EducaciÃ³"));
        return (T) Mono.just(responseScopeDtos);
    }

}
