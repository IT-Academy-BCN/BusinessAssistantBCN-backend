package com.businessassistantbcn.opendata.controller;

import com.businessassistantbcn.opendata.dto.bcnzones.BcnZonesDto;
import com.businessassistantbcn.opendata.dto.bcnzones.BcnZonesResponseDto;
import com.businessassistantbcn.opendata.service.config.DataConfigService;
import com.businessassistantbcn.opendata.service.config.TestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;


@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = CommonController.class)
public class CommonControllerTest {

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	DataConfigService bcnZonesService0;

	private final String
			CONTROLLER_BASE_URL = "/businessassistantbcn/api/v1/common",
			RES0 = "$.results[0].";

	@MockBean
	private TestService testService;

	@MockBean
	private DataConfigService bcnZonesService;


	@Test
	@DisplayName(("get all zones (mock BcnZonesDto) - success"))
	public void JsonResponseTests1() {

		final String URI_TEST = "/bcn-zones";

		BcnZonesDto bcnZonesA=new BcnZonesDto();
		bcnZonesA.setId(1);
		bcnZonesA.setName("Iles Kerguelen");

		BcnZonesResponseDto bcnZonesRespDto=new BcnZonesResponseDto();
		bcnZonesRespDto.setCount(1);
		bcnZonesRespDto.setElements(new BcnZonesDto[]{bcnZonesA});

		Mockito
				.when(bcnZonesService.getBcnZones())
				.thenReturn(Mono.just(bcnZonesRespDto));

		webTestClient.get()
				.uri(CONTROLLER_BASE_URL + URI_TEST)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.count").isEqualTo(1)
				.jsonPath("$.elements[0]." + "idZone").isEqualTo(1)
				.jsonPath("$.elements[0]." + "zoneName").isNotEmpty()
				.jsonPath("$.elements[0]." + "zoneName").isEqualTo("Iles Kerguelen");

		Mockito.verify(bcnZonesService).getBcnZones();

	}


}


