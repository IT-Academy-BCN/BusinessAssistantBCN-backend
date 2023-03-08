package com.businessassistantbcn.opendata.contract;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.annotations.PactFolder;
import com.businessassistantbcn.opendata.dto.input.SearchDTO;
import com.businessassistantbcn.opendata.dto.input.marketfairs.MarketFairsSearchDto;
import com.businessassistantbcn.opendata.dto.input.municipalmarkets.MunicipalMarketsSearchDTO;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ExtendWith(PactConsumerTestExt.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@PactTestFor(port = "8760")
@PactFolder("src/test/resources/pacts/test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class EndToEndContractTest {

    @Autowired
    private WebTestClient webTestClient;

    private final String CONTROLLER_BASE_URL = "/businessassistantbcn/api/v1/opendata";
    private static final String RES0 = "$.results[0].";
    private static final String ALLRES = "$.results[*].";


    @Pact(provider = "mock-commercial-galleries", consumer = "opendata")
    public RequestResponsePact commercialGalleries(PactDslWithProvider builder) throws URISyntaxException, IOException {

        return pactMaker(builder, "/commercial-galleries");
    }

    @Test
    @PactTestFor(pactMethod = "commercialGalleries")
    void commercialGalleriesTest() {

        requestDataAndVerifyJSONStructure("/commercial-galleries");

        requestDataAndVerifyExactFirstElement("/commercial-galleries")
                .jsonPath("$.count").isEqualTo(4)
                .jsonPath("$.results").isArray()
                .jsonPath(RES0 + "name").isEqualTo("Galeria Comercial Les Galeries Maldà")
                .jsonPath(RES0 + "web").isEqualTo("http://www.galeriesmalda.cat")
                .jsonPath(RES0 + "email").isEqualTo("info@galeriesmalda.cat")
                .jsonPath(RES0 + "phone").isEmpty()
                .jsonPath(RES0 + "activities").isArray()
                .jsonPath(RES0 + "activities[0].activityId").isEqualTo(1008025)
                .jsonPath(RES0 + "addresses").isArray()
                .jsonPath(RES0 + "addresses[0].street_name").isEqualTo("C Portaferrissa");
    }

    @Test
    @PactTestFor(pactMethod = "commercialGalleries")
    void commercialGalleriesActivityTest() {

        requestDataAndVerifyExactFirstElement("/commercial-galleries/activity/1006051")
                .jsonPath("$.count").isEqualTo(1)
                .jsonPath("$.results").isArray()
                .jsonPath(RES0 + "name").isEqualTo("Galeria Comercial Bulevard dels Antiquaris")
                .jsonPath(RES0 + "web").isEqualTo("http://www.bulevarddelsantiquaris.com")
                .jsonPath(RES0 + "email").isEqualTo("info@bulevarddelsantiquaris.com")
                .jsonPath(RES0 + "phone").isEmpty()
                .jsonPath(RES0 + "activities").isArray()
                .jsonPath(RES0 + "activities[1].activityId").isEqualTo(1006051)
                .jsonPath(RES0 + "addresses").isArray()
                .jsonPath(RES0 + "addresses[0].street_name").isEqualTo("Pg Gràcia");
    }

    @Test
    @PactTestFor(pactMethod = "commercialGalleries")
    void commercialGalleriesActivitiesTest() {

        requestDataAndVerifyExactFirstElement("/commercial-galleries/activities")
                .jsonPath("$.count").isEqualTo(3)
                .jsonPath("$.results").isArray()
                .jsonPath(RES0 + "activityId").isEqualTo(29810738)
                .jsonPath(RES0 + "activityName").isEqualTo("Antiguitats i brocanteries");
    }

    @Test
    @PactTestFor(pactMethod = "commercialGalleries")
    void commercialGalleriesDistrictTest() {

        requestDataAndVerifyExactFirstElement("/commercial-galleries/district/2")
                .jsonPath("$.count").isEqualTo(2)
                .jsonPath("$.results").isArray()
                .jsonPath(RES0 + "name").isEqualTo("Galeria Comercial Bulevard dels Antiquaris")
                .jsonPath(RES0 + "web").isEqualTo("http://www.bulevarddelsantiquaris.com")
                .jsonPath(RES0 + "email").isEqualTo("info@bulevarddelsantiquaris.com")
                .jsonPath(RES0 + "phone").isEmpty()
                .jsonPath(RES0 + "activities").isArray()
                .jsonPath(RES0 + "activities[1].activityId").isEqualTo(1006051)
                .jsonPath(RES0 + "addresses").isArray()
                .jsonPath(RES0 + "addresses[0].street_name").isEqualTo("Pg Gràcia");
    }

    @Test
    @PactTestFor(pactMethod = "commercialGalleries")
    void commercialGalleriesSearchTest() {

       // SearchDTO searchDTO = new SearchDTO(new int[]{2, 3}, new int[]{1006051});

        webTestClient.method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.path(CONTROLLER_BASE_URL + "/commercial-galleries/search")
                        .queryParam("zones", 2,3)
                        .queryParam("activities", 1006051)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.offset").isEqualTo(0)
                .jsonPath("$.limit").isEqualTo(-1)
                .jsonPath("$.count").isEqualTo(1)
                .jsonPath("$.results").isArray()
                .jsonPath(RES0 + "name").isEqualTo("Galeria Comercial Bulevard dels Antiquaris")
                .jsonPath(RES0 + "web").isEqualTo("http://www.bulevarddelsantiquaris.com")
                .jsonPath(RES0 + "email").isEqualTo("info@bulevarddelsantiquaris.com")
                .jsonPath(RES0 + "phone").isEmpty()
                .jsonPath(RES0 + "activities").isArray()
                .jsonPath(RES0 + "activities[1].activityId").isEqualTo(1006051)
                .jsonPath(RES0 + "addresses").isArray()
                .jsonPath(RES0 + "addresses[0].street_name").isEqualTo("Pg Gràcia")
                .jsonPath(RES0 + "addresses[0].district_id").isEqualTo("02");
    }

    @Pact(provider = "mock-big-malls", consumer = "opendata")
    public RequestResponsePact bigMalls(PactDslWithProvider builder) throws URISyntaxException, IOException {

        return pactMaker(builder, "/big-malls");
    }

    @Test
    @PactTestFor(pactMethod = "bigMalls")
    void bigMallsTest() {

        requestDataAndVerifyJSONStructure("/big-malls");

        requestDataAndVerifyExactFirstElement("/big-malls")
                .jsonPath("$.count").isEqualTo(27)
                .jsonPath("$.results").isArray()
                .jsonPath(RES0 + "name").isEqualTo("Centre Comercial Arenas de Barcelona")
                .jsonPath(RES0 + "web").isEqualTo("http://www.arenasdebarcelona.com")
                .jsonPath(RES0 + "email").isEqualTo("arenas.informacion@arenasdebarcelona.com")
                .jsonPath(RES0 + "phone").isEmpty()
                .jsonPath(RES0 + "activities").isArray()
                .jsonPath(RES0 + "activities[1].activityId").isEqualTo(37810722)
                .jsonPath(RES0 + "addresses").isArray()
                .jsonPath(RES0 + "addresses[0].street_name").isEqualTo("G.V. Corts Catalanes");
    }

    @Test
    @PactTestFor(pactMethod = "bigMalls")
    void bigMallsActivitiesTest() {

        requestDataAndVerifyExactFirstElement("/big-malls/activities")
                .jsonPath("$.count").isEqualTo(49)
                .jsonPath("$.results").isArray()
                .jsonPath(RES0 + "activityId").isEqualTo(105001)
                .jsonPath(RES0 + "activityName").isEqualTo("Accessible per a persones amb discapacitat física");
    }

    @Test
    @PactTestFor(pactMethod = "bigMalls")
    void bigMallsActivityTest() {

        requestDataAndVerifyExactFirstElement("/big-malls/activity/107001")
                .jsonPath("$.count").isEqualTo(1)
                .jsonPath("$.results").isArray()
                .jsonPath(RES0 + "name").isEqualTo("Hipercor *Avinguda Meridiana")
                .jsonPath(RES0 + "web").isEqualTo("http://www.hipercor.es")
                .jsonPath(RES0 + "email").isEmpty()
                .jsonPath(RES0 + "phone").isEmpty()
                .jsonPath(RES0 + "activities").isArray()
                .jsonPath(RES0 + "activities[0].activityId").isEqualTo(43326348)
                .jsonPath(RES0 + "addresses").isArray()
                .jsonPath(RES0 + "addresses[0].street_name").isEqualTo("Av Meridiana");
    }

    @Test
    @PactTestFor(pactMethod = "bigMalls")
    void bigMallsDistrictTest() {

        requestDataAndVerifyExactFirstElement("/big-malls/district/3")
                .jsonPath("$.count").isEqualTo(2)
                .jsonPath("$.results").isArray()
                .jsonPath(RES0 + "name").isEqualTo("Botiga Bauhaus *Passeig Zona Franca")
                .jsonPath(RES0 + "web").isEqualTo("http://www.bauhaus.es")
                .jsonPath(RES0 + "email").isEqualTo("barcelona@bauhaus.es")
                .jsonPath(RES0 + "phone").isEmpty()
                .jsonPath(RES0 + "activities").isArray()
                .jsonPath(RES0 + "activities[1].activityId").isEqualTo(32805726)
                .jsonPath(RES0 + "addresses").isArray()
                .jsonPath(RES0 + "addresses[0].street_name").isEqualTo("Passeig de la Zona Franca");
    }

    @Test
    @PactTestFor(pactMethod = "bigMalls")
    void bigMallsSearchTest() {

        //SearchDTO searchDTO = new SearchDTO(new int[]{2, 3}, new int[]{37810722});

        webTestClient.method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.path(CONTROLLER_BASE_URL + "/big-malls/search")
                        .queryParam("zones", 2,3)
                        .queryParam("activities", 37810722)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.offset").isEqualTo(0)
                .jsonPath("$.limit").isEqualTo(-1)
                .jsonPath("$.count").isEqualTo(3)
                .jsonPath("$.results").isArray()
                .jsonPath(RES0 + "name").isEqualTo("Centre Comercial Arenas de Barcelona")
                .jsonPath(RES0 + "web").isEqualTo("http://www.arenasdebarcelona.com")
                .jsonPath(RES0 + "email").isEqualTo("arenas.informacion@arenasdebarcelona.com")
                .jsonPath(RES0 + "phone").isEmpty()
                .jsonPath(RES0 + "activities").isArray()
                .jsonPath(RES0 + "activities[1].activityId").isEqualTo(37810722)
                .jsonPath(RES0 + "addresses").isArray()
                .jsonPath(RES0 + "addresses[0].street_name").isEqualTo("G.V. Corts Catalanes")
                .jsonPath(RES0 + "addresses[0].district_id").isEqualTo("02");
    }

    @Pact(provider = "mock-large-establishments", consumer = "opendata")
    public RequestResponsePact largeEstablishments(PactDslWithProvider builder) throws URISyntaxException, IOException {

        return pactMaker(builder, "/large-establishments");
    }

    @Test
    @PactTestFor(pactMethod = "largeEstablishments")
    void largeEstablishmentsTest() {

        requestDataAndVerifyJSONStructure("/large-establishments");

        requestDataAndVerifyExactFirstElement("/large-establishments")
                .jsonPath("$.count").isEqualTo(44)
                .jsonPath("$.results").isArray()
                .jsonPath(RES0 + "name").isEqualTo("Groupe Zannier Espanya")
                .jsonPath(RES0 + "web").isEqualTo("http://www.kidilizgroup.com")
                .jsonPath(RES0 + "email").isEmpty()
                .jsonPath(RES0 + "phone").isEmpty()
                .jsonPath(RES0 + "activities").isArray()
                .jsonPath(RES0 + "activities[0].activityId").isEqualTo(1008031)
                .jsonPath(RES0 + "addresses").isArray()
                .jsonPath(RES0 + "addresses[0].street_name").isEqualTo("C Mallorca");
    }

    @Test
    @PactTestFor(pactMethod = "largeEstablishments")
    void largeEstablishmentsActivitiesTest() {

        requestDataAndVerifyExactFirstElement("/large-establishments/activities")
                .jsonPath("$.count").isEqualTo(100)
                .jsonPath("$.results").isArray()
                .jsonPath(RES0 + "activityId").isEqualTo(107001)
                .jsonPath(RES0 + "activityName").isEqualTo("Alimentació i begudes");
    }

    @Test
    @PactTestFor(pactMethod = "largeEstablishments")
    void largeEstablishmentsActivityTest() {

        requestDataAndVerifyExactFirstElement("/large-establishments/activity/107007")
                .jsonPath("$.count").isEqualTo(3)
                .jsonPath("$.results").isArray()
                .jsonPath(RES0 + "name").isEqualTo("Oracle Ibérica")
                .jsonPath(RES0 + "web").isEqualTo("http://www.oracle.es")
                .jsonPath(RES0 + "email").isEmpty()
                .jsonPath(RES0 + "phone").isEmpty()
                .jsonPath(RES0 + "activities").isArray()
                .jsonPath(RES0 + "activities[1].activityId").isEqualTo(107007)
                .jsonPath(RES0 + "addresses").isArray()
                .jsonPath(RES0 + "addresses[0].street_name").isEqualTo("Av Diagonal");
    }

    @Test
    @PactTestFor(pactMethod = "largeEstablishments")
    void largeEstablishmentsDistrictTest() {

        requestDataAndVerifyExactFirstElement("/large-establishments/district/3")
                .jsonPath("$.count").isEqualTo(4)
                .jsonPath("$.results").isArray()
                .jsonPath(RES0 + "name").isEqualTo("Empresa Konica Minolta")
                .jsonPath(RES0 + "web").isEqualTo("http://www.konicaminolta.es")
                .jsonPath(RES0 + "email").isEqualTo("barcelona@konicaminolta.es")
                .jsonPath(RES0 + "phone").isEmpty()
                .jsonPath(RES0 + "activities").isArray()
                .jsonPath(RES0 + "activities[1].activityId").isEqualTo(1026004)
                .jsonPath(RES0 + "addresses").isArray()
                .jsonPath(RES0 + "addresses[0].street_name").isEqualTo("Rbla Brasil");
    }

    @Test
    @PactTestFor(pactMethod = "largeEstablishments")
    void largeEstablishmentsSearchTest() {

        //SearchDTO searchDTO = new SearchDTO(new int[]{2, 3}, new int[]{107001});

        webTestClient.method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.path(CONTROLLER_BASE_URL + "/large-establishments/search")
                        .queryParam("zones", 2,3)
                        .queryParam("activities", 107001)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.offset").isEqualTo(0)
                .jsonPath("$.limit").isEqualTo(-1)
                .jsonPath("$.count").isEqualTo(6)
                .jsonPath("$.results").isArray()
                .jsonPath(RES0 + "name").isEqualTo("Novartis Farmacéutica S.A.")
                .jsonPath(RES0 + "web").isEqualTo("http://www.novartis.es")
                .jsonPath(RES0 + "email").isEmpty()
                .jsonPath(RES0 + "phone").isEmpty()
                .jsonPath(RES0 + "activities").isArray()
                .jsonPath(RES0 + "activities[1].activityId").isEqualTo(107001)
                .jsonPath(RES0 + "addresses").isArray()
                .jsonPath(RES0 + "addresses[0].street_name").isEqualTo("G.V. Corts Catalanes")
                .jsonPath(RES0 + "addresses[0].district_id").isEqualTo("02");
    }

    @Pact(provider = "mock-market-fairs", consumer = "opendata")
    public RequestResponsePact marketFairs(PactDslWithProvider builder) throws URISyntaxException, IOException {

        return pactMaker(builder, "/market-fairs");
    }

    @Test
    @PactTestFor(pactMethod = "marketFairs")
    void marketFairsTest() {

        requestDataAndVerifyJSONStructure("/market-fairs");

        requestDataAndVerifyExactFirstElement("/market-fairs")
                .jsonPath("$.count").isEqualTo(41)
                .jsonPath("$.results").isArray()
                .jsonPath(RES0 + "name").isEqualTo("Mercat Encants de Sant Antoni")
                .jsonPath(RES0 + "web").isEqualTo("http://www.mercatdesantantoni.com")
                .jsonPath(RES0 + "email").isEmpty()
                .jsonPath(RES0 + "phone").isEmpty()
                .jsonPath(RES0 + "addresses").isArray()
                .jsonPath(RES0 + "addresses[0].street_name").isEqualTo("C Comte d'Urgell");
    }

    @Test
    @PactTestFor(pactMethod = "marketFairs")
    void marketFairsDistrictTest() {

        requestDataAndVerifyExactFirstElement("/market-fairs/district/2")
                .jsonPath("$.count").isEqualTo(5)
                .jsonPath("$.results").isArray()
                .jsonPath(RES0 + "name").isEqualTo("Mercat Encants de Sant Antoni")
                .jsonPath(RES0 + "web").isEqualTo("http://www.mercatdesantantoni.com")
                .jsonPath(RES0 + "email").isEmpty()
                .jsonPath(RES0 + "phone").isEmpty()
                .jsonPath(RES0 + "addresses").isArray()
                .jsonPath(RES0 + "addresses[0].street_name").isEqualTo("C Comte d'Urgell");
    }

    @Test
    @PactTestFor(pactMethod = "marketFairs")
    void marketFairsSearchTest() {

        //MarketFairsSearchDto searchDTO = new MarketFairsSearchDto(new int[]{2, 3});

        webTestClient.method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.path(CONTROLLER_BASE_URL + "/market-fairs/search")
                        .queryParam("zones", 2,3)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.offset").isEqualTo(0)
                .jsonPath("$.limit").isEqualTo(-1)
                .jsonPath("$.count").isEqualTo(7)
                .jsonPath("$.results").isArray()
                .jsonPath(RES0 + "name").isEqualTo("Mercat Encants de Sant Antoni")
                .jsonPath(RES0 + "web").isEqualTo("http://www.mercatdesantantoni.com")
                .jsonPath(RES0 + "email").isEmpty()
                .jsonPath(RES0 + "phone").isEmpty()
                .jsonPath(RES0 + "addresses").isArray()
                .jsonPath(RES0 + "addresses[0].street_name").isEqualTo("C Comte d'Urgell")
                .jsonPath(RES0 + "addresses[0].district_id").isEqualTo("02");
    }

    @Pact(provider = "mock-municipal-markets", consumer = "opendata")
    public RequestResponsePact municipalMarkets(PactDslWithProvider builder) throws URISyntaxException, IOException {

        return pactMaker(builder, "/municipal-markets");
    }

    @Test
    @PactTestFor(pactMethod = "municipalMarkets")
    void municipalMarketsTest() {

        String endpointURL = "/municipal-markets";

        //Does not return an activities field
        webTestClient.get()
                .uri(CONTROLLER_BASE_URL + endpointURL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.offset").isEqualTo(0)
                .jsonPath("$.limit").isEqualTo(-1)
                .jsonPath("$.count").isNumber()
                .jsonPath("$.results").isArray()
                .jsonPath(ALLRES + "name").hasJsonPath()
                .jsonPath(ALLRES + "web").hasJsonPath()
                .jsonPath(ALLRES + "email").hasJsonPath()
                .jsonPath(ALLRES + "phone").hasJsonPath()
                .jsonPath(ALLRES + "activities").doesNotHaveJsonPath()
                .jsonPath(ALLRES + "addresses").isArray();

        requestDataAndVerifyExactFirstElement(endpointURL)
                .jsonPath("$.count").isEqualTo(43)
                .jsonPath("$.results").isArray()
                .jsonPath(RES0 + "name").isEqualTo("Mercat del Guinardó")
                .jsonPath(RES0 + "web").isArray()
                .jsonPath(RES0 + "web[0]").isEqualTo("http://www.barcelona.cat/mercats")
                .jsonPath(RES0 + "email").isEqualTo("mercatguinardo@bcn.cat")
                .jsonPath(RES0 + "phone").isEqualTo("934132332")
                .jsonPath(RES0 + "addresses").isArray()
                .jsonPath(RES0 + "addresses[0].street_name").isEqualTo("C Teodor Llorente");
    }

    @Test
    @PactTestFor(pactMethod = "municipalMarkets")
    void municipalMarketsDistrictTest() {

        requestDataAndVerifyExactFirstElement("/municipal-markets/district/2")
                .jsonPath("$.count").isEqualTo(8)
                .jsonPath("$.results").isArray()
                .jsonPath(RES0 + "name").isEqualTo("Mercat de La Concepció")
                .jsonPath(RES0 + "web[0]").isEqualTo("https://www.facebook.com/MercatConcepcio")
                .jsonPath(RES0 + "email").isEqualTo("mercatconcepcio@bcn.cat")
                .jsonPath(RES0 + "phone").isEqualTo("934132314,934764870")
                .jsonPath(RES0 + "addresses").isArray()
                .jsonPath(RES0 + "addresses[0].street_name").isEqualTo("Carrer d'Aragó")
                .jsonPath(RES0 + "addresses[0].district_id").isEqualTo("02");
    }

    @Test
    @PactTestFor(pactMethod = "municipalMarkets")
    void municipalMarketsSearchTest() {

        //MunicipalMarketsSearchDTO searchDTO = new MunicipalMarketsSearchDTO(new int[]{2, 3});

        webTestClient.method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.path(CONTROLLER_BASE_URL + "/municipal-markets/search")
                        .queryParam("zones", 2,3)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.offset").isEqualTo(0)
                .jsonPath("$.limit").isEqualTo(-1)
                .jsonPath("$.count").isEqualTo(11)
                .jsonPath("$.results").isArray()
                .jsonPath(RES0 + "name").isEqualTo("Mercat de La Concepció")
                .jsonPath(RES0 + "web[0]").isEqualTo("https://www.facebook.com/MercatConcepcio")
                .jsonPath(RES0 + "email").isEqualTo("mercatconcepcio@bcn.cat")
                .jsonPath(RES0 + "phone").isEqualTo("934132314,934764870")
                .jsonPath(RES0 + "addresses").isArray()
                .jsonPath(RES0 + "addresses[0].street_name").isEqualTo("Carrer d'Aragó")
                .jsonPath(RES0 + "addresses[0].district_id").isEqualTo("02");
    }

    @Pact(provider = "mock-server-down", consumer = "opendata")
    public RequestResponsePact serverDown(PactDslWithProvider builder) {

        return builder.given("Server down")
                .uponReceiving("Test interaction - Server down")
                .path("/test/large-establishments")
                .method(HttpMethod.GET.name())
                .willRespondWith()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value()) //500
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "serverDown")
    void serverDownTest() {

        final String URI_TEST = "/large-establishments";

        webTestClient.get()
                .uri(CONTROLLER_BASE_URL + URI_TEST)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.offset").isEqualTo(0)
                .jsonPath("$.limit").isEqualTo(0)
                .jsonPath("$.count").isEqualTo(0)
                .jsonPath("$.results").isEmpty();
    }

    @Pact(provider = "mock-economic-activities-census", consumer = "opendata")
    public RequestResponsePact economicActivitiesCensus(PactDslWithProvider builder) throws URISyntaxException, IOException {

        return pactMaker(builder, "/economic-activities-census");
    }

    @Test
    @PactTestFor(pactMethod = "economicActivitiesCensus")
    void economicActivitiesCensusTest() {

        webTestClient.method(HttpMethod.GET)
                .uri(CONTROLLER_BASE_URL + "/economic-activities-census")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.offset").isEqualTo(0)
                .jsonPath("$.limit").isEqualTo(-1)
                .jsonPath("$.count").isEqualTo(96)
                .jsonPath("$.results").isArray()
                .jsonPath(RES0 + "json_featuretype").isEqualTo("class_act")
                .jsonPath(RES0 + "Codi_Activitat_2019").isEqualTo("1000000")
                .jsonPath(RES0 + "Nom_Activitat").isEqualTo("Resta alimentació")
                .jsonPath(RES0 + "Codi_Activitat_2016").isEqualTo("1000")
                .jsonPath(RES0 + "Codi_Principal_Activitat").isEqualTo("1")
                .jsonPath(RES0 + "Nom_Principal_Activitat").isEqualTo("Actiu")
                .jsonPath(RES0 + "Codi_Sector_Activitat").isEqualTo("1")
                .jsonPath(RES0 + "Nom_Sector_Activitat").isEqualTo("Comerç al detall")
                .jsonPath(RES0 + "Codi_Grup_Activitat").isEqualTo("1")
                .jsonPath(RES0 + "Nom_Grup_Activitat").isEqualTo("Quotidià alimentari")
                .jsonPath(RES0 + "Comentari_Activitat").isEmpty()
                .consumeWith(System.out::println);
    }

    private RequestResponsePact pactMaker(PactDslWithProvider builder, String endpointURL) throws URISyntaxException, IOException {

        String bodyString = jsonLoader("json/apitest" + endpointURL + "Data.json");

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");

        return builder.given("Server up - " + endpointURL)
                .uponReceiving("Test interaction - " + endpointURL + " - Server up")
                .path("/test" + endpointURL)
                .method(HttpMethod.GET.name())
                .willRespondWith()
                .headers(headers)
                .status(HttpStatus.OK.value()) //200
                .body(bodyString, ContentType.APPLICATION_JSON.withCharset(StandardCharsets.UTF_8))
                .toPact();
    }

    private void requestDataAndVerifyJSONStructure(String endpointURL) {

        webTestClient.get()
                .uri(CONTROLLER_BASE_URL + endpointURL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.offset").isEqualTo(0)
                .jsonPath("$.limit").isEqualTo(-1)
                .jsonPath("$.count").isNumber()
                .jsonPath("$.results").isArray()
                .jsonPath(ALLRES + "name").hasJsonPath()
                .jsonPath(ALLRES + "web").hasJsonPath()
                .jsonPath(ALLRES + "email").hasJsonPath()
                .jsonPath(ALLRES + "phone").hasJsonPath()
                .jsonPath(ALLRES + "activities").isArray()
                .jsonPath(ALLRES + "addresses").isArray();
    }

    private WebTestClient.BodyContentSpec requestDataAndVerifyExactFirstElement(String endpointURL) {

        return webTestClient.get()
                .uri(CONTROLLER_BASE_URL + endpointURL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.offset").isEqualTo(0)
                .jsonPath("$.limit").isEqualTo(-1);
    }

    private String jsonLoader(String resourceURI) throws URISyntaxException, IOException {

        Path path = Paths.get(Objects.requireNonNull(EndToEndContractTest.class.getClassLoader().getResource(resourceURI)).toURI());
        return Files.readAllLines(path, StandardCharsets.UTF_8).get(0);
    }
}
