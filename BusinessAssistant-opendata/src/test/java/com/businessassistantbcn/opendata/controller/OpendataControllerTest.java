package com.businessassistantbcn.opendata.controller;

import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.bcnzones.*;
import com.businessassistantbcn.opendata.dto.bigmalls.*;
import com.businessassistantbcn.opendata.dto.economicactivitiescensus.EconomicActivitiesCensusDto;
import com.businessassistantbcn.opendata.dto.marketfairs.MarketFairsDto;
import com.businessassistantbcn.opendata.dto.municipalmarkets.MunicipalMarketsDto;
import com.businessassistantbcn.opendata.dto.test.*;
import com.businessassistantbcn.opendata.service.config.*;
import com.businessassistantbcn.opendata.service.externaldata.*;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Mono;

import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = OpendataController.class)
public class OpendataControllerTest {
	
	@Autowired
	private WebTestClient webTestClient;
	
	private final String
		CONTROLLER_BASE_URL = "/v1/api/opendata",
		RES0 = "$.results[0].";
	
	@MockBean
	private TestService testService;
	@MockBean
	private BigMallsService bigMallsService;
	@MockBean
	private MarketFairsService marketFairsService;
	@MockBean
	private MunicipalMarketsService municipalMarketsService;
	@MockBean
	private LargeStablishmentsService largeStablishmentsService;
	@MockBean
	private CommercialGalleriesService commercialGalleriesService;
	@MockBean
	private EconomicActivitiesCensusService economicActivitiesCensusService;
	@MockBean
	private DataConfigService bcnZonesService;
	
	@DisplayName("Simple String response")
	@Test
	public void testHello(){
		
		final String URI_TEST = "/test";
		
		webTestClient.get()
				.uri(CONTROLLER_BASE_URL + URI_TEST)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody(String.class)
				.value(s -> s.toString(), equalTo("Hello from BusinessAssistant Barcelona!!!"));
		
	}
	
	@DisplayName("Reactive response -- Star Wars vehicles")
	@Test
	public void testReactive() { try {
		
		final String URI_TEST = "/test-reactive";
		
		StarWarsVehicleDto vehicleSW = new StarWarsVehicleDto();
		vehicleSW.setName("R18 GTD (familiar)");
		vehicleSW.setModel("Renault 18 GTD");
		vehicleSW.setManufacturer("Renault");
		vehicleSW.setLength(4.487F);
		vehicleSW.setMax_atmosphering_speed(156);
		vehicleSW.setCrew(1);
		vehicleSW.setPassengers(4);
		
		StarWarsVehiclesResultDto vehiclesResultSW = new StarWarsVehiclesResultDto();
		vehiclesResultSW.setCount(1);
		vehiclesResultSW.setNext(null);
		vehiclesResultSW.setPrevious(null);
		vehiclesResultSW.setResults(new StarWarsVehicleDto[]{vehicleSW});
		
		Mockito
			.when(testService.getTestData())
			.thenReturn(Mono.just(vehiclesResultSW));
		
		webTestClient.get()
				.uri(CONTROLLER_BASE_URL + URI_TEST)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.count").isEqualTo(1)
				.jsonPath(RES0 + "name").isNotEmpty()
				.jsonPath(RES0 + "name").isEqualTo("R18 GTD (familiar)")
				.jsonPath(RES0 + "model").isNotEmpty()
				.jsonPath(RES0 + "model").isEqualTo("Renault 18 GTD")
				.jsonPath(RES0 + "manufacturer").isNotEmpty()
				.jsonPath(RES0 + "manufacturer").isEqualTo("Renault")
				.jsonPath(RES0 + "length").isEqualTo(4.487F)
				.jsonPath(RES0 + "max_atmosphering_speed").isEqualTo(156)
				.jsonPath(RES0 + "crew").isEqualTo(1)
				.jsonPath(RES0 + "passengers").isEqualTo(4);
		
		// Verifica la llamada al método 'getTestData()'.
		Mockito.verify(testService).getTestData();
		
	} catch (MalformedURLException e) {
		throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", e);
	} }
	
	@DisplayName("Opendata response -- JSON elements of commercial centers")
	@ParameterizedTest(name = "{index} -> URL=''{0}''")
	@MethodSource("argsProvider")
	public <T> void JsonResponseTests1(String URI_TEST, Class<T> dtoClass, String stringDtoService) { try {
		
		// Crear un DTO genérico…
		Constructor<T> constructor = dtoClass.getConstructor();		
		T genericDTO = constructor.newInstance();
		
		// … apuntar a sus Setters …
		Method
			mtd_1 = dtoClass.getDeclaredMethod("setRegister_id", Long.TYPE),
			mtd_2 = dtoClass.getDeclaredMethod("setName", String.class),
			mtd_3 = dtoClass.getDeclaredMethod("setCreated", String.class),
			mtd_4 = dtoClass.getDeclaredMethod("setGeo_epgs_4326", CoordinateDto.class);
		
		// … y rellenar sus campos con valores.
		mtd_1.invoke(genericDTO, 007L);	// Afortunadamente, el número también es válido en octal.
		mtd_2.invoke(genericDTO, "Secret Intelligent Service MI6");
		mtd_3.invoke(genericDTO, "1909-07-04T00:00:00+00:00");
		mtd_4.invoke(genericDTO, new CoordinateDto(51.487222, -0.124167));
		
		// Crear el empaquetamiento para el DTO anterior …
		GenericResultDto<T> genericResultDTO = new GenericResultDto<>();
		genericResultDTO.setCount(1);
		genericResultDTO.setOffset(0);
		genericResultDTO.setLimit(1);
		
		// … y guardarlo dentro.
		@SuppressWarnings("unchecked")
		T[] results = (T[])Array.newInstance(dtoClass, 1);
		results[0] = genericDTO;
		genericResultDTO.setResults(results);
		
		// Servicio DTO particular.
		Field dtoServiceField = this.getClass().getDeclaredField(stringDtoService);
		Object dtoService = dtoServiceField.get(this);
		
		// Método '.getPage(int,int)' dentro del servicio particular.
		Method getPage0m1 = dtoServiceField.getType().getDeclaredMethod("getPage", Integer.TYPE, Integer.TYPE);
		
		// Intercepta la petición de ensayo, devolviendo un 'Mono' con el 'GenericResultDto<genericDto>'
		// de un solo resultado fabricado anteriormente. 
		Mockito
			.when(getPage0m1.invoke(dtoService, 0, -1))
			.thenReturn(Mono.just(genericResultDTO));
		
		// Petición de prueba a la página web solicitando datos concretos; parámetros 'offset' y 'limit' sin
		// especificar -> equivalente a solicitar todos los resultados. Batería de ensayos sobre el objeto
		// JSON "retornado".
		webTestClient.get()
				.uri(uriBuilder -> uriBuilder.path(CONTROLLER_BASE_URL + URI_TEST)
//						.queryParam("offset", 0)
//						.queryParam("limit", -1)
						.build())
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.count").isEqualTo(1)
				.jsonPath("$.offset").isEqualTo(0)
				.jsonPath("$.limit").isEqualTo(1)
				.jsonPath(RES0 + "register_id").isEqualTo(007L)
				.jsonPath(RES0 + "name").isNotEmpty()
				.jsonPath(RES0 + "name").isEqualTo("Secret Intelligent Service MI6")
				.jsonPath(RES0 + "created").isNotEmpty()
				.jsonPath(RES0 + "created").isEqualTo("1909-07-04T00:00:00+00:00")
				.jsonPath(RES0 + "geo_epgs_4326.x").isEqualTo(51.487222)
				.jsonPath(RES0 + "geo_epgs_4326.y").isEqualTo(-0.124167);
		
		// Verificación de la llamada única al método 'getPage(0,-1)' del servicio
		getPage0m1.invoke(Mockito.verify(dtoService), 0, -1);
		
	} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException |
			IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
		
		throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Failed to return a DTO", e);
		
	} }
	
	// Generador de argumentos para los ensayos del controlador con los centros económicos
	private static Arguments[] argsProvider() {
		
		final Arguments[] args = {
			Arguments.of("/big-malls", BigMallsDto.class, "bigMallsService"),
			Arguments.of("/market-fairs", MarketFairsDto.class, "marketFairsService"),
			Arguments.of("/municipal-markets", MunicipalMarketsDto.class, "municipalMarketsService"),
//			Arguments.of("/large-stablishments", LargeStablishmentsDto.class, "largeStablishmentsService"),
//			Arguments.of("/commercial-galleries", CommercialGalleries.class, "commercialGalleriesService")
		};
		
		return args;
		
	}
	
	@DisplayName("Opendata response -- JSON elements of economic activity codes")
	@Test
	public void JsonResponseTests2() {
		
		final String URI_TEST = "/economic-activities-census";
		
		EconomicActivitiesCensusDto activitatEconomica = new EconomicActivitiesCensusDto();
		activitatEconomica.setCodi_Activitat_2016("314159265");
		activitatEconomica.setCodi_Activitat_2019("057721566");
		activitatEconomica.setNom_Activitat("Exercicis d'apnea");
		activitatEconomica.setNom_Sector_Activitat("Flamenco dancing");
		
		GenericResultDto<EconomicActivitiesCensusDto> genericResultDTO = new GenericResultDto<>();
		genericResultDTO.setCount(1);
		genericResultDTO.setOffset(0);
		genericResultDTO.setLimit(1);
		genericResultDTO.setResults(new EconomicActivitiesCensusDto[]{activitatEconomica});
		
		Mockito
			.when(economicActivitiesCensusService.getPage(0,-1))
			.thenReturn(Mono.just(genericResultDTO));
		
		webTestClient.get()
			.uri(uriBuilder -> uriBuilder.path(CONTROLLER_BASE_URL + URI_TEST)
//					.queryParam("offset", 0)
//					.queryParam("limit", -1)
					.build())
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBody()
			.jsonPath("$.count").isEqualTo(1)
			.jsonPath("$.offset").isEqualTo(0)
			.jsonPath("$.limit").isEqualTo(1)
			.jsonPath(RES0 + "Codi_Activitat_2016").isNotEmpty()
			.jsonPath(RES0 + "Codi_Activitat_2016").isEqualTo("314159265")
			.jsonPath(RES0 + "Codi_Activitat_2019").isNotEmpty()
			.jsonPath(RES0 + "Codi_Activitat_2019").isEqualTo("057721566")
			.jsonPath(RES0 + "Nom_Activitat").isNotEmpty()
			.jsonPath(RES0 + "Nom_Activitat").isEqualTo("Exercicis d'apnea")
			.jsonPath(RES0 + "Nom_Sector_Activitat").isNotEmpty()
			.jsonPath(RES0 + "Nom_Sector_Activitat").isEqualTo("Flamenco dancing");
		
		Mockito.verify(economicActivitiesCensusService).getPage(0,-1);
		
	}
	
	@DisplayName("Opendata response -- JSON elements of Barcelona's districts ")
	@Test
	public void JsonResponseTests3() {
		
		final String URI_TEST = "/bcn-zones";
		
		BcnZonesDto
			bcnZonesDto1 = new BcnZonesDto(1, "Îles Kerguelen"),
			bcnZonesDto2 = new BcnZonesDto(2, "Klendathu");
		
		BcnZonesResponseDto bcnZonesRespDto
				= new BcnZonesResponseDto(2, new BcnZonesDto[]{bcnZonesDto1, bcnZonesDto2}); 
		
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
				.jsonPath("$.count").isEqualTo(2)
				.jsonPath("$.elements[0]." + "idZone").isEqualTo(1)
				.jsonPath("$.elements[0]." + "zoneName").isNotEmpty()
				.jsonPath("$.elements[0]." + "zoneName").isEqualTo("Îles Kerguelen")
				.jsonPath("$.elements[1]." + "idZone").isEqualTo(2)
				.jsonPath("$.elements[1]." + "zoneName").isNotEmpty()
				.jsonPath("$.elements[1]." + "zoneName").isEqualTo("Klendathu");
		
		Mockito.verify(bcnZonesService).getBcnZones();
		
	}
	
}