package com.businessassistantbcn.gencat.controller;

import com.businessassistantbcn.gencat.config.PropertiesConfig;
import com.businessassistantbcn.gencat.dto.GenericResultDto;
import com.businessassistantbcn.gencat.dto.io.CcaeDto;
import com.businessassistantbcn.gencat.dto.io.CodeInfoDto;
import com.businessassistantbcn.gencat.dto.output.RaiscResponseDto;
import com.businessassistantbcn.gencat.exception.ControllerAdvisor;
import com.businessassistantbcn.gencat.proxy.HttpProxy;
import com.businessassistantbcn.gencat.service.CcaeService;
import com.businessassistantbcn.gencat.service.RaiscService;
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
    private RaiscService raiscService;

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
    private RaiscResponseDto[] raiscResponseDto;

    @BeforeEach
    void setUp() {
        responseDto = new CcaeDto[2];
        responseDtoById = new CcaeDto[1];
        raiscResponseDto = new RaiscResponseDto[1];

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
        responseDtoById[0]=ccaeDto1;

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

        responseDtoById[0]=ccaeDto1;

        RaiscResponseDto raiscResponseDto1 = new RaiscResponseDto();
        raiscResponseDto1.setIdRaisc("00000000-0000-0000-3BDF-CA7CD820BDD5");
        raiscResponseDto1.setEntity("AGÈNCIA DE GESTIÓ D'AJUTS UNIVERSITARIS I DE RECERCA (AGAUR)");
        raiscResponseDto1.setRaiscType("Subvenció amb concurrència");
        raiscResponseDto1.setAnyo("2022");
        raiscResponseDto1.setTitleCA("Ajuts a les associacions d'estudiantat universitari per al curs 2022-2023 (AEU)");
        raiscResponseDto1.setTitleES("Ayudas a las asociaciones de estudiantado universitario para el curso 2022-2023 (AEU)");
        raiscResponseDto1.setBasesCA("https://dogc.gencat.cat/ca/document-del-dogc/?documentId=908267");
        raiscResponseDto1.setBasesES("https://dogc.gencat.cat/es/document-del-dogc/index.html?documentId=908267");
        raiscResponseDto1.setSubventionType("Subvenció i lliurament dinerari sense contraprest.");
        raiscResponseDto1.setIdRegion("ES51");
        raiscResponseDto1.setRegion("CATALUNYA");
        raiscResponseDto1.setIdScope("10");
        raiscResponseDto1.setScope("Educació");
        raiscResponseDto1.setIdSector("85.4");
        raiscResponseDto1.setSector("Educació postsecundària");
        raiscResponseDto1.setOrigin("Pressupostos Generals de les Comunitats Autònomes");
        raiscResponseDto1.setMaxBudgetPublish("26636.4");
        raiscResponseDto1.setMaxBudgetUE("0");
        raiscResponseDto1.setMaxBudge("26636.4");
        raiscResponseDto1.setStartDate("Des de l’endemà de la publicació en el diari oficial de la convocatòria inicial");
        raiscResponseDto1.setEndDate("03/11/2022");
        raiscResponseDto1.setUrlRequest("null");
        raiscResponseDto1.setDescription("Ajuts a les associacions d'estudiantat universitari per al curs 2022-2023 (AEU)");
        raiscResponseDto[0] = raiscResponseDto1;
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
                .jsonPath(RES0 + "code.idCcae").isEqualTo("A")
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

    @Test
    void getRaiscByYearTest(){
        final String URI_TEST = "/raisc/year/2022";
        when(raiscService.getPageByRaiscYear(0,-1, "2022")).thenReturn(Mono.just(getGenericResultDtoRaiscByYear()));
        webTestClient.get()
                .uri(CONTROLLER_BASE_URL + URI_TEST)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.count").isEqualTo(1)
                .jsonPath("$.offset").isEqualTo(1)
                .jsonPath("$.limit").isEqualTo(1)
                .jsonPath(RES0 + "anyo").isEqualTo("2022")
                .consumeWith(System.out::println)
                .jsonPath("year");
    }

    private GenericResultDto<RaiscResponseDto> getGenericResultDtoRaiscByYear(){

        GenericResultDto<RaiscResponseDto> genericResultDto = new GenericResultDto<>();
        genericResultDto.setInfo(1, 1, raiscResponseDto.length, raiscResponseDto);
        return genericResultDto;
    }

}