package com.businessassistantbcn.opendata.contract;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.annotations.PactFolder;
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

        requestDataAndVerifyExactFirstElement("/commercial-galleries/activities")
                .jsonPath("$.count").isEqualTo(3)
                .jsonPath("$.results").isArray()
                .jsonPath(RES0 + "activityId").isEqualTo(29810738)
                .jsonPath(RES0 + "activityName").isEqualTo("Antiguitats i brocanteries");

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

        requestDataAndVerifyExactFirstElement("/big-malls/activities")
                .jsonPath("$.count").isEqualTo(49)
                .jsonPath("$.results").isArray()
                .jsonPath(RES0 + "activityId").isEqualTo(105001)
                .jsonPath(RES0 + "activityName").isEqualTo("Accessible per a persones amb discapacitat física");

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

        requestDataAndVerifyExactFirstElement("/large-establishments/activities")
                .jsonPath("$.count").isEqualTo(100)
                .jsonPath("$.results").isArray()
                .jsonPath(RES0 + "activityId").isEqualTo(107001)
                .jsonPath(RES0 + "activityName").isEqualTo("Alimentació i begudes");

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
                .jsonPath(RES0 + "activities").isArray()
                .jsonPath(RES0 + "activities[1].activityId").isEqualTo(1011011)
                .jsonPath(RES0 + "addresses").isArray()
                .jsonPath(RES0 + "addresses[0].street_name").isEqualTo("C Comte d'Urgell");

        //TODO once district endpoint implemented

//        requestDataAndVerifyExactFirstElement("/market-fairs/district/3")
//                .jsonPath("$.count").isEqualTo(2)
//                .jsonPath("$.results").isArray()
//                .jsonPath(RES0 + "name").isEqualTo("Botiga Bauhaus *Passeig Zona Franca")
//                .jsonPath(RES0 + "web").isEqualTo("http://www.bauhaus.es")
//                .jsonPath(RES0 + "email").isEqualTo("barcelona@bauhaus.es")
//                .jsonPath(RES0 + "phone").isEmpty()
//                .jsonPath(RES0 + "activities").isArray()
//                .jsonPath(RES0 + "activities[1].activityId").isEqualTo(32805726)
//                .jsonPath(RES0 + "addresses").isArray()
//                .jsonPath(RES0 + "addresses[0].street_name").isEqualTo("Passeig de la Zona Franca");
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

        requestDataAndVerifyExactFirstElement("/municipal-markets")
                .jsonPath("$.count").isEqualTo(43)
                .jsonPath("$.results").isArray()
                .jsonPath(RES0 + "name").isEqualTo("Mercat del Guinardó")
                .jsonPath(RES0 + "web").isArray()
                .jsonPath(RES0 + "web[0]").isEqualTo("http://www.barcelona.cat/mercats")
                .jsonPath(RES0 + "email").isEqualTo("mercatguinardo@bcn.cat")
                .jsonPath(RES0 + "phone").isEqualTo("934132332")
                .jsonPath(RES0 + "addresses").isArray()
                .jsonPath(RES0 + "addresses[0].street_name").isEqualTo("C Teodor Llorente");

        //TODO once district endpoint implemented

//        requestDataAndVerifyExactFirstElement("/municipal-markets/district/3")
//                .jsonPath("$.count").isEqualTo(2)
//                .jsonPath("$.results").isArray()
//                .jsonPath(RES0 + "name").isEqualTo("Botiga Bauhaus *Passeig Zona Franca")
//                .jsonPath(RES0 + "web").isEqualTo("http://www.bauhaus.es")
//                .jsonPath(RES0 + "email").isEqualTo("barcelona@bauhaus.es")
//                .jsonPath(RES0 + "phone").isEmpty()
//                .jsonPath(RES0 + "activities").isArray()
//                .jsonPath(RES0 + "activities[1].activityId").isEqualTo(32805726)
//                .jsonPath(RES0 + "addresses").isArray()
//                .jsonPath(RES0 + "addresses[0].street_name").isEqualTo("Passeig de la Zona Franca");
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
