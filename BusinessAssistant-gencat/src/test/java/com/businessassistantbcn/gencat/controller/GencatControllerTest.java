package com.businessassistantbcn.gencat.controller;

import com.businessassistantbcn.gencat.config.PropertiesConfig;
import com.businessassistantbcn.gencat.dto.GenericResultDto;
import com.businessassistantbcn.gencat.dto.io.CcaeDto;
import com.businessassistantbcn.gencat.dto.io.CodeInfoDto;
import com.businessassistantbcn.gencat.exception.ControllerAdvisor;
import com.businessassistantbcn.gencat.proxy.HttpProxy;
import com.businessassistantbcn.gencat.service.CcaeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = GencatController.class, excludeAutoConfiguration = {ReactiveSecurityAutoConfiguration.class})
class GencatControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    @Autowired
    private HttpProxy httpProxy;

    @MockBean
    @Autowired
    private PropertiesConfig config;

    @MockBean
    private CcaeService ccaeService;

    @MockBean
    private ControllerAdvisor controllerAdvisor;

    private final String CONTROLLER_BASE_URL = "/businessassistantbcn/api/v1/gencat";
    private static final String RES0 = "$.results[0].";
    private static String urlEconomicActivitiesString;
    private static final String JSON_FILENAME = "/get5-imi7.json";
    private static ObjectMapper mapper;
    private static Object[] economicActivities;

    private CcaeDto[] responseDto;
    private CcaeDto[] responseDtoById;

    @BeforeEach
    void setUp() {
        responseDto = new CcaeDto[2];

        CcaeDto ccaeDto1 = new CcaeDto();
        String id1 = "00000000-0000-0000-D7DC-CC770365D8FF";
        String type1 = "Secció";
        String idCodeInfo1 = "A";
        String codeDescription1 = "Agricultura, ramaderia, silvicultura i pesca";
        CodeInfoDto codeInfoDto1 = new CodeInfoDto(idCodeInfo1, codeDescription1);
        ccaeDto1.setId(id1);
        ccaeDto1.setType(type1);
        ccaeDto1.setCode(codeInfoDto1);
        responseDto[0] = ccaeDto1;

        CcaeDto ccaeDto2 = new CcaeDto();
        String id2 = "00000000-0000-0000-2335-839767DDAEAB";
        String type2 = "Divisió";
        String idCodeInfo2 = "01";
        String codeDescription2 = "Agricultura, ramaderia, caça i activitats dels serveis que s'hi relacionen";
        CodeInfoDto codeInfoDto2 = new CodeInfoDto(idCodeInfo2, codeDescription2);
        ccaeDto2.setId(id2);
        ccaeDto2.setType(type2);
        ccaeDto2.setCode(codeInfoDto2);
        responseDto[1] = ccaeDto2;

        responseDtoById = new CcaeDto[1];
        responseDtoById[0] = ccaeDto1;
    }

    @Test
    void testHello() {

        final String URI_TEST = "/test";
        webTestClient.get()
                .uri(CONTROLLER_BASE_URL + URI_TEST)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(String::toString, equalTo("Hello from GenCat Controller!!!"));
    }

    @Test
    void getEconomicActivities() throws MalformedURLException {

        final String URI_TEST = "/ccae";
        when(ccaeService.getPage(0,-1)).thenReturn(Mono.just(getGenericResultDto()));

        webTestClient.get()
                .uri(CONTROLLER_BASE_URL + URI_TEST)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.count").isEqualTo(2)
                .jsonPath("$.offset").isEqualTo(0)
                .jsonPath("$.limit").isEqualTo(-1)
                .jsonPath(RES0 + "id").isEqualTo("00000000-0000-0000-D7DC-CC770365D8FF")
                .jsonPath(RES0 + "type").isEqualTo("Secció")
                .consumeWith(System.out::println);
    }

    private GenericResultDto<CcaeDto> getGenericResultDto() {

        GenericResultDto<CcaeDto> genericResultDto = new GenericResultDto<>();
        genericResultDto.setInfo(0, -1, responseDto.length, responseDto);
        return genericResultDto;
    }

    private GenericResultDto<CcaeDto> getGenericResultDtoById(){

        GenericResultDto<CcaeDto> genericResultDto = new GenericResultDto<>();
        genericResultDto.setInfo(0, -1, responseDtoById.length, responseDtoById);
        return genericResultDto;
    }

    //Una vez implementado correctamente el método, el test se debe adecuar
    @Test
    void getEconomicActivityById() throws MalformedURLException {
        final String URI_TEST = "/ccae/A";
        when(ccaeService.getPageByCcaeId(0,-1, "A")).thenReturn(Mono.just(getGenericResultDtoById()));
        webTestClient.get()
                .uri(CONTROLLER_BASE_URL + URI_TEST)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.count").isEqualTo(1)
                .jsonPath("$.offset").isEqualTo(0)
                .jsonPath("$.limit").isEqualTo(-1)
                .jsonPath(RES0 + "id").isEqualTo("00000000-0000-0000-D7DC-CC770365D8FF")
                .jsonPath(RES0 + "type").isEqualTo("Secció")
                .consumeWith(System.out::println)
                .jsonPath("idCcae");
    }

    //Una vez implementado correctamente el método, el test se debe adecuar
    @Test
    void getEconomicActivityType() {
        final String URI_TEST = "/ccae/type/1";
        webTestClient.get()
                .uri(CONTROLLER_BASE_URL + URI_TEST)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_IMPLEMENTED);
    }

    //Una vez implementado correctamente el método, el test se debe adecuar
    @Test
    void getEconomicActivitiesTypes() {
        final String URI_TEST = "/ccae/types";
        webTestClient.get()
                .uri(CONTROLLER_BASE_URL + URI_TEST)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_IMPLEMENTED);

    }

}