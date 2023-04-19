package com.businessassistantbcn.gencat.service;

import com.businessassistantbcn.gencat.adapters.DataSourceAdapter;
import com.businessassistantbcn.gencat.config.PropertiesConfig;
import com.businessassistantbcn.gencat.dto.GenericResultDto;
import com.businessassistantbcn.gencat.dto.output.RaiscResponseDto;
import com.businessassistantbcn.gencat.dto.output.ResponseScopeDto;
import com.businessassistantbcn.gencat.proxy.HttpProxy;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RaiscServiceTest {

    @Autowired
    @InjectMocks
    private RaiscService raiscService;

    @Mock
    private HttpProxy httpProxy;

    @Mock
    private PropertiesConfig config;

    @MockBean
    private DataSourceAdapter dataAdapter;

    private static ObjectMapper mapper;

    private static final String JSON_ALL_RAISC_SCOPE_DATA = "json/allRaiscData.json";

    private static String raiscResponseDtosAllData;


    @BeforeAll
    static void beforeAll() throws URISyntaxException, IOException {
        MockitoAnnotations.openMocks(RaiscServiceTest.class);
        String allDataAsString = Files.readAllLines(Paths.get(ClassLoader.getSystemResource(JSON_ALL_RAISC_SCOPE_DATA).toURI()), StandardCharsets.UTF_8).get(0);
        mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        raiscResponseDtosAllData = allDataAsString;
    }

    @Test
    void getScopesTest() throws MalformedURLException {
        when(config.getDs_scopes()).thenReturn("https://analisi.transparenciacatalunya.cat/api/views/khxn-nv6a/rows.json");
        when(httpProxy.getRequestData(new URL(config.getDs_scopes()), String.class)).thenReturn(Mono.just(raiscResponseDtosAllData));
        List<ResponseScopeDto> scopes = raiscService.getScopes(0, 5).block();

        assertNotNull(scopes);

        assertEquals(5, scopes.size());
        assertEquals("Justícia", scopes.get(0).getScope());
        assertEquals("Cooperació internacional per al desenvolupament i cultural", scopes.get(1).getScope());
        assertEquals("Educació", scopes.get(2).getScope());
        assertEquals("Indústria i Energia", scopes.get(3).getScope());
        assertEquals("Cultura", scopes.get(4).getScope());
    }

    @DisplayName("Parametrized test for find pageable raisc by scope id")
    @ParameterizedTest
    @MethodSource("provideArgsForPageableRaisc")
    void getPageRaiscByScope(int sourceSize, int offset, int limit){
        ResponseScopeDto criteria = new ResponseScopeDto("10","Educació");
        Flux<RaiscResponseDto> source = Flux.fromIterable(initDataSourceMixingScope(sourceSize, criteria));
        when(dataAdapter.findAllRaisc()).thenReturn(source);


        int totalTargetInSource = sourceSize/2;
        int resultSize = getResultSize(totalTargetInSource, offset, limit);

        Mono<GenericResultDto<RaiscResponseDto>> raiscResults =
                raiscService.getPageRaiscByScope(offset, limit, criteria.getIdScope());

        StepVerifier.create(raiscResults)
                .assertNext(genericResult -> {
                    assertEquals(offset, genericResult.getOffset());
                    assertEquals(limit, genericResult.getLimit());
                    assertEquals(resultSize, genericResult.getCount());
                    assertEquals(resultSize, genericResult.getResults().length);
                    for(int i= 0; i<genericResult.getCount(); i++){
                        assertEquals(criteria.getIdScope(), genericResult.getResults()[i].getIdScope());
                        assertEquals(criteria.getScope(), genericResult.getResults()[i].getScope());
                    }
                })
                .verifyComplete();
    }

    private int getResultSize(int totalTargetInSource, int offset, int limit) {
        //System.out.println("total after filter: "+totalTargetInSource);
        int numOffsetted = Math.max(offset, 0);
        //System.out.println("N first elems excluded: "+numOffsetted);
        int numRemaining = totalTargetInSource > numOffsetted ? totalTargetInSource - numOffsetted : 0;
        //System.out.println("N elements after offset: "+numRemaining);
        int numInResponse;
        if(limit >= 0){
            numInResponse = Math.min(numRemaining, limit);
        }else {
            numInResponse = numRemaining;
        }
        //System.out.println("Expected result size: "+numInResponse);
        return numInResponse;
    }

    private static Stream<Arguments> provideArgsForPageableRaisc() {
        //remember: elems wich pass filter = source size / 2
        return Stream.of(
                Arguments.of(10, 0, -1), //no offset, neiter limit
                Arguments.of(10, -4, -5), //no offset, neiter limit with invalid values

                Arguments.of(10, 4, -1), //offset with only one remaining (no limit)
                Arguments.of(10, 5, -1), //offset excludes all (no limit)

                Arguments.of(10, 0, 1), //limit only allows one (no offset)
                Arguments.of(10, 0, 5), //limit allows exactly the founds (no offset)
                Arguments.of(10, 0, 6), //limit allows more than filtered: size of filtered  (no offset)

                Arguments.of(10, 1, 3) // offset excludes first, limit excludes last
        );
    }

    private List<RaiscResponseDto> initDataSourceMixingScope(int sourceSize,
                                                             ResponseScopeDto criteria){
        List<RaiscResponseDto> raiscs = new LinkedList<>();
        String raiscId;
        RaiscResponseDto raisc;
        for (int i = 0; i<sourceSize; i++){
            raiscId = "randomRaiscId_"+i;
            if(i%2 == 0){
                raisc = RaiscResponseDto.builder()
                        .idRaisc(raiscId)
                        .idScope("randomIdScope_"+i)
                        .scope("randomScope_"+i)
                        .build();
            }else {
                raisc =  RaiscResponseDto.builder()
                        .idRaisc(raiscId)
                        .idScope(criteria.getIdScope())
                        .scope(criteria.getScope())
                        .build();
            }
            raiscs.add(raisc);
        }
        return raiscs;
    }



}

