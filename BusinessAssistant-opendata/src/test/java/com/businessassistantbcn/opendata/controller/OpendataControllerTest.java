package com.businessassistantbcn.opendata.controller;

import com.businessassistantbcn.opendata.dto.ActivityInfoDto;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.bigmalls.BigMallsDto;
import com.businessassistantbcn.opendata.dto.economicactivitiescensus.EconomicActivitiesCensusDto;
import com.businessassistantbcn.opendata.dto.largeestablishments.LargeEstablishmentsDto;
import com.businessassistantbcn.opendata.dto.marketfairs.MarketFairsDto;
import com.businessassistantbcn.opendata.dto.test.StarWarsVehicleDto;
import com.businessassistantbcn.opendata.dto.test.StarWarsVehiclesResultDto;
import com.businessassistantbcn.opendata.service.config.TestService;
import com.businessassistantbcn.opendata.service.externaldata.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = OpendataController.class)
public class OpendataControllerTest {
	
	@Autowired
	private WebTestClient webTestClient;
	
	private final String
		CONTROLLER_BASE_URL = "/businessassistantbcn/api/v1/opendata",
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
	private LargeEstablishmentsService largeEstablishmentsService;
	@MockBean
	private CommercialGalleriesService commercialGalleriesService;
	@MockBean
	private EconomicActivitiesCensusService economicActivitiesCensusService;
	
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
		
		when(testService.getTestData())
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
		verify(testService).getTestData();
		
	} catch(MalformedURLException e) {
		throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", e);
	} }


	@DisplayName("Opendata response -- JSON elements of commercial centers")
	//@ParameterizedTest(name = "{index} -> URL=''{0}''")
	@MethodSource("argsProvider")
	public <T> void JsonResponseTests1(String URI_TEST, Class<T> dtoClass, String stringDtoService) { try {

		// Crear un DTO genérico…
		String packageName = dtoClass.getPackageName();
		Class<?> clazzA = Class.forName(packageName + ".ContactDto");
		Class<?> clazzB = Class.forName(packageName + ".ClassificationDataDto");
		Constructor<T> constructor = dtoClass.getConstructor(String.class, List.class, List.class, List.class);
		Constructor<?> constructorA = clazzA.getConstructor(String.class, String.class, String.class);
		Constructor<?> constructorB = clazzB.getConstructor(Long.class, String.class);
		T genericDTO = constructor.newInstance(
				"Secret Intelligent Service MI6",
				List.of(constructorA.newInstance(
						"https://www.sis.gov.uk/contact-us.html",
						"jamesbond@verysecretplace.co.uk",
						"020 7008 1500")),
				List.of(constructorB.newInstance(
						007L, // Afortunadamente, el número también es válido en octal.
						"LicenceToKill")),
				List.of());

		// Crear el empaquetamiento para el DTO anterior …
		GenericResultDto<T> genericResultDTO = new GenericResultDto<>();
		genericResultDTO.setCount(1);
		genericResultDTO.setOffset(0);
		genericResultDTO.setLimit(1);

		// … y guardarlo dentro.
		@SuppressWarnings("unchecked")
		T[] results = (T[]) Array.newInstance(dtoClass, 1);
		results[0] = genericDTO;
		genericResultDTO.setResults(results);

		// Servicio DTO particular.
		Field dtoServiceField = this.getClass().getDeclaredField(stringDtoService);
		Object dtoService = dtoServiceField.get(this);

		// Método '.getPage(int,int)' dentro del servicio particular.
		Method getPage0m1 = dtoServiceField.getType().getDeclaredMethod("getPage", Integer.TYPE, Integer.TYPE);

		// Intercepta la petición de ensayo, devolviendo un 'Mono' con el 'GenericResultDto<genericDto>'
		// de un solo resultado fabricado anteriormente.
		when(getPage0m1.invoke(dtoService, 0, -1))
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
				.jsonPath(RES0 + "name").isNotEmpty()
				.jsonPath(RES0 + "name").isEqualTo("Secret Intelligent Service MI6")
				.jsonPath(RES0 + "web").isNotEmpty()
				.jsonPath(RES0 + "web").isEqualTo("https://www.sis.gov.uk/contact-us.html")
				.jsonPath(RES0 + "email").isNotEmpty()
				.jsonPath(RES0 + "email").isEqualTo("jamesbond@verysecretplace.co.uk")
				.jsonPath(RES0 + "phone").isNotEmpty()
				.jsonPath(RES0 + "phone").isEqualTo("020 7008 1500")
				.jsonPath(RES0 + "activities[0]." + "id").isEqualTo(7)
				.jsonPath(RES0 + "activities[0]." + "name").isNotEmpty()
				.jsonPath(RES0 + "activities[0]." + "name").isEqualTo("LicenceToKill");

		// Verificación de la llamada única al método 'getPage(0,-1)' del servicio
		getPage0m1.invoke(verify(dtoService), 0, -1);

	} catch(ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
			InvocationTargetException | NoSuchFieldException e) {

		throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Failed to return a DTO", e);

	} }

	// Generador de argumentos para los ensayos del controlador con los centros económicos
	private static Arguments[] argsProvider() {
		
		final Arguments[] args = {
			Arguments.of("/big-malls", BigMallsDto.class, "bigMallsService"),
			Arguments.of("/large-establishments", LargeEstablishmentsDto.class, "largeEstablishmentsService"),
			Arguments.of("/market-fairs", MarketFairsDto.class, "marketFairsService"),
//			Arguments.of("/municipal-markets", MunicipalMarketsDto.class, "municipalMarketsService"),
//			Arguments.of("/commercial-galleries", CommercialGalleriesDto.class, "commercialGalleriesService")
		};
		
		return args;
		
	}
	
	@DisplayName("Opendata response -- JSON elements of economic activity codes")
	@Test
	public void JsonResponseTests2() throws MalformedURLException {
		
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
		
		when(economicActivitiesCensusService.getPage(0,-1))
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
		
		verify(economicActivitiesCensusService).getPage(0,-1);
		
	}

	@Test
	public void getBigMallsActivitiesTest() throws MalformedURLException {
		when(bigMallsService.getBigMallsActivities(0,-1)).thenReturn(Mono.just(this.getGenericResultDto()));
		this.requestActivities("/big-malls/activities");
		verify(bigMallsService).getBigMallsActivities(0,-1);

	}

	@Test
	public void getCommercialGalleriesActivitiesTest() throws MalformedURLException {
		when(commercialGalleriesService.getCommercialGalleriesActivities(0,-1))
			.thenReturn(Mono.just(this.getGenericResultDto()));
		this.requestActivities("/commercial-galleries/activities");
		verify(commercialGalleriesService).getCommercialGalleriesActivities(0,-1);
	}

	@Test
	public void getLargeEstablishmentsActivitiesTest() throws MalformedURLException {
		when(largeEstablishmentsService.getLargeEstablishmentsActivities(0,-1))
				.thenReturn(Mono.just(this.getGenericResultDto()));
		this.requestActivities("/large-establishments/activities");
		verify(largeEstablishmentsService).getLargeEstablishmentsActivities(0,-1);

	}

	private GenericResultDto<ActivityInfoDto> getGenericResultDto() {
		ActivityInfoDto[] results = {new ActivityInfoDto(1L, "Activitat 1")};
		GenericResultDto<ActivityInfoDto> genericResultDto = new GenericResultDto<>();
		genericResultDto.setInfo(0, -1, 1, results);
		return genericResultDto;
	}

	private void requestActivities(String uriTest) {
		webTestClient.get()
				.uri(uriBuilder -> uriBuilder.path(CONTROLLER_BASE_URL + uriTest).build())
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.count").isEqualTo(1)
				.jsonPath("$.offset").isEqualTo(0)
				.jsonPath("$.limit").isEqualTo(-1)
				.jsonPath(RES0 + "activityId").isEqualTo(1)
				.jsonPath(RES0 + "activityName").isEqualTo("Activitat 1");
	}
}