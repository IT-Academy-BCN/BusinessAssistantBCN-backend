package com.businessassistantbcn.opendata.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.businessassistantbcn.opendata.dto.input.bcnzones.BcnZonesDto;
import com.businessassistantbcn.opendata.dto.output.BcnZonesResponseDto;
import com.businessassistantbcn.opendata.service.config.DataConfigService;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = CommonController.class)
public class CommonControllerTest {
	
	@Autowired
	private WebTestClient webTestClient;
	
	@MockBean
	private DataConfigService bcnZonesService;

	private final String CONTROLLER_BASE_URL = "/businessassistantbcn/api/v1/common";

	@DisplayName("Opendata response -- JSON elements of Barcelona's districts ")
	@Test
	public void JsonResponseTests3() {

		final String URI_TEST = "/bcn-zones";

		BcnZonesDto bcnZonesDto1 = new BcnZonesDto(1, "Îles Kerguelen"), bcnZonesDto2 = new BcnZonesDto(2, "Klendathu");

		BcnZonesResponseDto bcnZonesRespDto = new BcnZonesResponseDto(2,
				new BcnZonesDto[] { bcnZonesDto1, bcnZonesDto2 });

		when(bcnZonesService.getBcnZones()).thenReturn(Mono.just(bcnZonesRespDto));

		webTestClient.get().uri(CONTROLLER_BASE_URL + URI_TEST).accept(MediaType.APPLICATION_JSON).exchange()
				.expectStatus().isOk().expectHeader().contentType(MediaType.APPLICATION_JSON).expectBody()
				.jsonPath("$.count").isEqualTo(2).jsonPath("$.elements[0]." + "idZone").isEqualTo(1)
				.jsonPath("$.elements[0]." + "zoneName").isNotEmpty().jsonPath("$.elements[0]." + "zoneName")
				.isEqualTo("Îles Kerguelen").jsonPath("$.elements[1]." + "idZone").isEqualTo(2)
				.jsonPath("$.elements[1]." + "zoneName").isNotEmpty().jsonPath("$.elements[1]." + "zoneName")
				.isEqualTo("Klendathu");

		verify(bcnZonesService).getBcnZones();

	}
}
