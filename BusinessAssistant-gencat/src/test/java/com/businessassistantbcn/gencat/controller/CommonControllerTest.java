package com.businessassistantbcn.gencat.controller;

import com.businessassistantbcn.gencat.config.PropertiesConfig;
import com.businessassistantbcn.gencat.dto.input.TypeDataDto;
import com.businessassistantbcn.gencat.dto.output.TypeDataResponseDto;
import com.businessassistantbcn.gencat.service.config.DataConfigService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = CommonController.class, excludeAutoConfiguration = {ReactiveSecurityAutoConfiguration.class})
class CommonControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private DataConfigService typesDataService;

    @MockBean
    private PropertiesConfig config;

    private final String CONTROLLER_BASE_URL = "/businessassistantbcn/api/v1/common";

    private String[] elementsTypesData;

    @BeforeEach
    void setUp(){
        elementsTypesData = new String[] {"Seccio", "Divisio", "Group", "Classe"};
    }

    @Test
    void getTypesData() {
        final String URI_TEST = "/types-data";

        TypeDataDto[] typeDataDto = {new TypeDataDto(1, "Seccio"), new TypeDataDto(2, "Divisio"),
        new TypeDataDto(3, "Group"), new TypeDataDto(4, "Classe")};

        TypeDataResponseDto responseDto = new TypeDataResponseDto(1, typeDataDto);


        when(config.getTypesData()).thenReturn(elementsTypesData);
        when(typesDataService.getTypes()).thenReturn(Mono.just(responseDto));

        webTestClient.get().uri(CONTROLLER_BASE_URL + URI_TEST)
                .accept(MediaType.APPLICATION_JSON).exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .equals(Mono.just(String[].class));
        assertEquals(typesDataService.getTypes().block().getElements(), responseDto.getElements());
    }
}