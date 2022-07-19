package com.businessassistantbcn.gencat.controller;

import com.businessassistantbcn.gencat.dto.input.TypeDataDto;
import com.businessassistantbcn.gencat.dto.output.TypeDataResponseDto;
import com.businessassistantbcn.gencat.service.config.DataConfigService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
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
    @Autowired
    private DataConfigService typesDataService;

    private final String CONTROLLER_BASE_URL = "/businessassistantbcn/api/v1/common";

    @Test
    void getTypesData() {
        final String URI_TEST = "/types-data";

        TypeDataDto[] typeDataDtos = {new TypeDataDto(1, "Seccio"), new TypeDataDto(2, "Divisio"),
        new TypeDataDto(3, "Group"), new TypeDataDto(4, "Classe")};

        int count = 4;

        webTestClient.get().uri(CONTROLLER_BASE_URL + URI_TEST)
                .accept(MediaType.APPLICATION_JSON).exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().equals(Mono.just(String[].class));
        assertEquals(typesDataService.getTypes().block().getCount(), count);
        //assertEquals(typesDataService.getTypes().block().getElements(), typeDataDtos);

    }
}