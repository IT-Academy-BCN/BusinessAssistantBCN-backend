package com.businessassistantbcn.opendata.contract;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.annotations.PactFolder;
import com.businessassistantbcn.opendata.dto.input.SearchDTO;
import com.businessassistantbcn.opendata.dto.input.marketfairs.MarketFairsSearchDto;
import com.businessassistantbcn.opendata.dto.input.municipalmarkets.MunicipalMarketsSearchDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(port = "8762")
@PactFolder("src/test/resources/pacts")
class FrontendPactGeneratorTest {

    private ObjectMapper mapper;
    private final String CONTROLLER_BASE_URL = "/businessassistantbcn/api/v1/opendata";
    private static SearchDTO largeEstablishmentsSearchParams;
    private static SearchDTO bigMallsSearchParams;
    private static SearchDTO commercialGalleriesSearchParams;

    @Pact(provider = "large-establishments", consumer = "frontend_application")
    public RequestResponsePact largeEstablishments(PactDslWithProvider builder) throws URISyntaxException, IOException {

        return pactMaker(builder, "/large-establishments", "");
    }

    @Test
    @PactTestFor(pactMethod = "largeEstablishments")
    void largeEstablishmentsTest(MockServer mockServer) throws IOException {

        checkAndCreatePactFile(mockServer, "/large-establishments");
    }

    @Pact(provider = "large-establishments", consumer = "frontend_application")
    public RequestResponsePact largeEstablishmentsActivities(PactDslWithProvider builder) throws URISyntaxException, IOException {

        return pactMaker(builder, "/large-establishments", "/activities");
    }

    @Test
    @PactTestFor(pactMethod = "largeEstablishmentsActivities")
    void largeEstablishmentsActivitiesTest(MockServer mockServer) throws IOException {

        checkAndCreatePactFile(mockServer, "/large-establishments/activities");
    }

    @Pact(provider = "large-establishments", consumer = "frontend_application")
    public RequestResponsePact largeEstablishmentsSpecificActivity(PactDslWithProvider builder) throws URISyntaxException, IOException {

        return pactMaker(builder, "/large-establishments", "/activity/107001");
    }

    @Test
    @PactTestFor(pactMethod = "largeEstablishmentsSpecificActivity")
    void largeEstablishmentsSpecificActivityTest(MockServer mockServer) throws IOException {

        checkAndCreatePactFile(mockServer, "/large-establishments/activity/107001");
    }

    @Pact(provider = "large-establishments", consumer = "frontend_application")
    public RequestResponsePact largeEstablishmentsSpecificDistrict(PactDslWithProvider builder) throws URISyntaxException, IOException {

        return pactMaker(builder, "/large-establishments", "/district/2");
    }

    @Test
    @PactTestFor(pactMethod = "largeEstablishmentsSpecificDistrict")
    void largeEstablishmentsSpecificDistrictTest(MockServer mockServer) throws IOException {

        checkAndCreatePactFile(mockServer, "/large-establishments/district/2");
    }

    @Pact(provider = "large-establishments", consumer = "frontend_application")
    public RequestResponsePact largeEstablishmentsSpecificSearch(PactDslWithProvider builder) throws URISyntaxException, IOException {

        largeEstablishmentsSearchParams = new SearchDTO(new int[]{2, 3}, new int[]{107001});

        return searchPactMaker(builder, "/large-establishments", largeEstablishmentsSearchParams);
    }

    @Test
    @PactTestFor(pactMethod = "largeEstablishmentsSpecificSearch")
    void largeEstablishmentsSpecificSearchTest(MockServer mockServer) throws IOException {

        checkAndCreateSearchPactFile(mockServer, "/large-establishments/search", largeEstablishmentsSearchParams);
    }

    @Pact(provider = "big-malls", consumer = "frontend_application")
    public RequestResponsePact bigMalls(PactDslWithProvider builder) throws URISyntaxException, IOException {

        return pactMaker(builder, "/big-malls", "");
    }

    @Test
    @PactTestFor(pactMethod = "bigMalls")
    void bigMallsTest(MockServer mockServer) throws IOException {

        checkAndCreatePactFile(mockServer, "/big-malls");
    }

    @Pact(provider = "big-malls", consumer = "frontend_application")
    public RequestResponsePact bigMallsActivities(PactDslWithProvider builder) throws URISyntaxException, IOException {

        return pactMaker(builder, "/big-malls", "/activities");
    }

    @Test
    @PactTestFor(pactMethod = "bigMallsActivities")
    void bigMallsActivitiesTest(MockServer mockServer) throws IOException {

        checkAndCreatePactFile(mockServer, "/big-malls/activities");
    }

    @Pact(provider = "big-malls", consumer = "frontend_application")
    public RequestResponsePact bigMallsSpecificActivity(PactDslWithProvider builder) throws URISyntaxException, IOException {

        return pactMaker(builder, "/big-malls", "/activity/32796754");
    }

    @Test
    @PactTestFor(pactMethod = "bigMallsSpecificActivity")
    void bigMallsSpecificActivityTest(MockServer mockServer) throws IOException {

        checkAndCreatePactFile(mockServer, "/big-malls/activity/32796754");
    }

    @Pact(provider = "big-malls", consumer = "frontend_application")
    public RequestResponsePact bigMallsSpecificDistrict(PactDslWithProvider builder) throws URISyntaxException, IOException {

        return pactMaker(builder, "/big-malls", "/district/2");
    }

    @Test
    @PactTestFor(pactMethod = "bigMallsSpecificDistrict")
    void bigMallsSpecificDistrictTest(MockServer mockServer) throws IOException {

        checkAndCreatePactFile(mockServer, "/big-malls/district/2");
    }

    @Pact(provider = "big-malls", consumer = "frontend_application")
    public RequestResponsePact bigMallsSpecificSearch(PactDslWithProvider builder) throws URISyntaxException, IOException {

        bigMallsSearchParams = new SearchDTO(new int[]{2, 3}, new int[]{37810722});

        return searchPactMaker(builder, "/big-malls", bigMallsSearchParams);
    }

    @Test
    @PactTestFor(pactMethod = "bigMallsSpecificSearch")
    void bigMallsSpecificSearchTest(MockServer mockServer) throws IOException {

        checkAndCreateSearchPactFile(mockServer, "/big-malls/search", bigMallsSearchParams);
    }

    @Pact(provider = "commercial-galleries", consumer = "frontend_application")
    public RequestResponsePact commercialGalleries(PactDslWithProvider builder) throws URISyntaxException, IOException {

        return pactMaker(builder, "/commercial-galleries", "");
    }

    @Test
    @PactTestFor(pactMethod = "commercialGalleries")
    void commercialGalleriesTest(MockServer mockServer) throws IOException {

        checkAndCreatePactFile(mockServer, "/commercial-galleries");
    }

    @Pact(provider = "commercial-galleries", consumer = "frontend_application")
    public RequestResponsePact commercialGalleriesActivities(PactDslWithProvider builder) throws URISyntaxException, IOException {

        return pactMaker(builder, "/commercial-galleries", "/activities");
    }

    @Test
    @PactTestFor(pactMethod = "commercialGalleriesActivities")
    void commercialGalleriesActivitiesTest(MockServer mockServer) throws IOException {

        checkAndCreatePactFile(mockServer, "/commercial-galleries/activities");
    }

    @Pact(provider = "commercial-galleries", consumer = "frontend_application")
    public RequestResponsePact commercialGalleriesSpecificActivity(PactDslWithProvider builder) throws URISyntaxException, IOException {

        return pactMaker(builder, "/commercial-galleries", "/activity/29810738");
    }

    @Test
    @PactTestFor(pactMethod = "commercialGalleriesSpecificActivity")
    void commercialGalleriesSpecificActivityTest(MockServer mockServer) throws IOException {

        checkAndCreatePactFile(mockServer, "/commercial-galleries/activity/29810738");
    }

    @Pact(provider = "commercial-galleries", consumer = "frontend_application")
    public RequestResponsePact commercialGalleriesSpecificDistrict(PactDslWithProvider builder) throws URISyntaxException, IOException {

        return pactMaker(builder, "/commercial-galleries", "/district/2");
    }

    @Test
    @PactTestFor(pactMethod = "commercialGalleriesSpecificDistrict")
    void commercialGalleriesSpecificDistrictTest(MockServer mockServer) throws IOException {

        checkAndCreatePactFile(mockServer, "/commercial-galleries/district/2");
    }

    @Pact(provider = "commercial-galleries", consumer = "frontend_application")
    public RequestResponsePact commercialGalleriesSpecificSearch(PactDslWithProvider builder) throws URISyntaxException, IOException {

        commercialGalleriesSearchParams = new SearchDTO(new int[]{2, 3}, new int[]{1006051});

        return searchPactMaker(builder, "/commercial-galleries", commercialGalleriesSearchParams);
    }

    @Test
    @PactTestFor(pactMethod = "commercialGalleriesSpecificSearch")
    void commercialGalleriesSpecificSearchTest(MockServer mockServer) throws IOException {

        checkAndCreateSearchPactFile(mockServer, "/commercial-galleries/search", commercialGalleriesSearchParams);
    }

    @Pact(provider = "municipal-markets", consumer = "frontend_application")
    public RequestResponsePact municipalMarkets(PactDslWithProvider builder) throws URISyntaxException, IOException {

        return pactMaker(builder, "/municipal-markets", "");
    }

    @Test
    @PactTestFor(pactMethod = "municipalMarkets")
    void municipalMarketsTest(MockServer mockServer) throws IOException {

        checkAndCreatePactFile(mockServer, "/municipal-markets");
    }

    @Pact(provider = "municipal-markets", consumer = "frontend_application")
    public RequestResponsePact municipalMarketsSpecificDistrict(PactDslWithProvider builder) throws URISyntaxException, IOException {

        return pactMaker(builder, "/municipal-markets", "/district/2");
    }

    @Test
    @PactTestFor(pactMethod = "municipalMarketsSpecificDistrict")
    void municipalMarketsSpecificDistrictTest(MockServer mockServer) throws IOException {

        checkAndCreatePactFile(mockServer, "/municipal-markets/district/2");
    }

    @Pact(provider = "municipal-markets", consumer = "frontend_application")
    public RequestResponsePact municipalMarketsSpecificSearch(PactDslWithProvider builder) throws URISyntaxException, IOException {

        MunicipalMarketsSearchDTO searchDTO = new MunicipalMarketsSearchDTO(new int[]{2, 3});

        String endpointBaseURL = "/municipal-markets";
        String responseType = "/search";
        String bodyString = jsonLoader("json/response" + endpointBaseURL + "/" + responseType + "Response.json");

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");

        mapper = new ObjectMapper();

        return builder.given("Server up - " + endpointBaseURL + "/search")
                .uponReceiving(endpointBaseURL + "/search" + " - Server up")
                .path(CONTROLLER_BASE_URL + endpointBaseURL + "/search")
                .method(HttpMethod.GET.name())
                .body(mapper.writeValueAsString(searchDTO))
                .willRespondWith()
                .headers(headers)
                .status(HttpStatus.OK.value()) //200
                .body(bodyString, ContentType.APPLICATION_JSON.withCharset(StandardCharsets.UTF_8))
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "municipalMarketsSpecificSearch")
    void municipalMarketsSpecificSearchTest(MockServer mockServer) throws IOException {

        MunicipalMarketsSearchDTO searchDTO = new MunicipalMarketsSearchDTO(new int[]{2, 3});

        HttpUriRequest request = RequestBuilder.create(HttpMethod.GET.name())
                .setUri(mockServer.getUrl() + CONTROLLER_BASE_URL + "/municipal-markets/search")
                .setEntity(new StringEntity(mapper.writeValueAsString(searchDTO), ContentType.APPLICATION_JSON))
                .build();
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200);
    }

    @Pact(provider = "market-fairs", consumer = "frontend_application")
    public RequestResponsePact marketFairs(PactDslWithProvider builder) throws URISyntaxException, IOException {

        return pactMaker(builder, "/market-fairs", "");
    }

    @Test
    @PactTestFor(pactMethod = "marketFairs")
    void marketFairsTest(MockServer mockServer) throws IOException {

        checkAndCreatePactFile(mockServer, "/market-fairs");
    }

    @Pact(provider = "market-fairs", consumer = "frontend_application")
    public RequestResponsePact marketFairsSpecificDistrict(PactDslWithProvider builder) throws URISyntaxException, IOException {

        return pactMaker(builder, "/market-fairs", "/district/2");
    }

    @Test
    @PactTestFor(pactMethod = "marketFairsSpecificDistrict")
    void marketFairsSpecificDistrictTest(MockServer mockServer) throws IOException {

        checkAndCreatePactFile(mockServer, "/market-fairs/district/2");
    }

    @Pact(provider = "market-fairs", consumer = "frontend_application")
    public RequestResponsePact marketFairsSpecificSearch(PactDslWithProvider builder) throws URISyntaxException, IOException {

        MarketFairsSearchDto searchDTO = new MarketFairsSearchDto(new int[]{2, 3});

        String endpointBaseURL = "/market-fairs";
        String responseType = "/search";
        String bodyString = jsonLoader("json/response" + endpointBaseURL + "/" + responseType + "Response.json");

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");

        mapper = new ObjectMapper();

        return builder.given("Server up - " + endpointBaseURL + "/search")
                .uponReceiving(endpointBaseURL + "/search" + " - Server up")
                .path(CONTROLLER_BASE_URL + endpointBaseURL + "/search")
                .method(HttpMethod.GET.name())
                .body(mapper.writeValueAsString(searchDTO))
                .willRespondWith()
                .headers(headers)
                .status(HttpStatus.OK.value()) //200
                .body(bodyString, ContentType.APPLICATION_JSON.withCharset(StandardCharsets.UTF_8))
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "marketFairsSpecificSearch")
    void marketFairsSpecificSearchTest(MockServer mockServer) throws IOException {

        MunicipalMarketsSearchDTO searchDTO = new MunicipalMarketsSearchDTO(new int[]{2, 3});

        HttpUriRequest request = RequestBuilder.create(HttpMethod.GET.name())
                .setUri(mockServer.getUrl() + CONTROLLER_BASE_URL + "/market-fairs/search")
                .setEntity(new StringEntity(mapper.writeValueAsString(searchDTO), ContentType.APPLICATION_JSON))
                .build();
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200);
    }

    @Pact(provider = "economic-activities-census", consumer = "frontend_application")
    public RequestResponsePact economicActivitiesCensus(PactDslWithProvider builder) throws URISyntaxException, IOException {

        String endpointBaseURL = "/economic-activities-census";
        String bodyString = jsonLoader("json/response" + endpointBaseURL + "Response.json");

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");

        return builder.given("Server up - " + endpointBaseURL)
                .uponReceiving(endpointBaseURL + " - Server up")
                .path(CONTROLLER_BASE_URL + endpointBaseURL)
                .method(HttpMethod.GET.name())
                .willRespondWith()
                .headers(headers)
                .status(HttpStatus.OK.value()) //200
                .body(bodyString, ContentType.APPLICATION_JSON.withCharset(StandardCharsets.UTF_8))
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "economicActivitiesCensus")
    void economicActivitiesCensusTest(MockServer mockServer) throws IOException {

        checkAndCreatePactFile(mockServer, "/economic-activities-census");
    }

    @Pact(provider = "bcn-zones", consumer = "frontend_application")
    public RequestResponsePact bcnZones(PactDslWithProvider builder) throws URISyntaxException, IOException {

        String endpointBaseURL = "/bcn-zones";
        String bodyString = jsonLoader("json/response" + endpointBaseURL + "Response.json");

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");

        return builder.given("Server up - " + endpointBaseURL)
                .uponReceiving(endpointBaseURL + " - Server up")
                .path("/businessassistantbcn/api/v1/common" + endpointBaseURL)
                .method(HttpMethod.GET.name())
                .willRespondWith()
                .headers(headers)
                .status(HttpStatus.OK.value()) //200
                .body(bodyString, ContentType.APPLICATION_JSON.withCharset(StandardCharsets.UTF_8))
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "bcnZones")
    void bcnZonesTest(MockServer mockServer) throws IOException {

        HttpResponse response = Request.Get(mockServer.getUrl() + "/businessassistantbcn/api/v1/common" + "/bcn-zones").execute().returnResponse();

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
    }

    @Pact(provider = "opendata_server-down", consumer = "frontend_application")
    public RequestResponsePact opendataAnyServerDown(PactDslWithProvider builder) throws URISyntaxException, IOException {

        //Could be any endpoint, mocks server down state
        String endpointURL = "/large-establishments";
        String bodyString = jsonLoader("json/response" + "/noData" + "Response.json");

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");

        return builder.given("Server down - " + endpointURL)
                .uponReceiving(endpointURL + " - Server down")
                .path(CONTROLLER_BASE_URL + endpointURL)
                .method(HttpMethod.GET.name())
                .willRespondWith()
                .headers(headers)
                .status(HttpStatus.OK.value()) //200
                .body(bodyString, ContentType.APPLICATION_JSON.withCharset(StandardCharsets.UTF_8))
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "opendataAnyServerDown")
    void serverDownTest(MockServer mockServer) throws IOException {

        //Could be any endpoint, mocks server down state
        checkAndCreatePactFile(mockServer, "/large-establishments");
    }

    private RequestResponsePact pactMaker(PactDslWithProvider builder, String endpointBaseURL, String endpointExtraURL) throws URISyntaxException, IOException {

        String responseType = endpointExtraURL.equals("") ? "all" : endpointExtraURL.replaceAll("/", "");
        String bodyString = jsonLoader("json/response" + endpointBaseURL + "/" + responseType + "Response.json");

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");

        return builder.given("Server up - " + endpointBaseURL + endpointExtraURL)
                .uponReceiving(endpointBaseURL + endpointExtraURL + " - Server up")
                .path(CONTROLLER_BASE_URL + endpointBaseURL + endpointExtraURL)
                .method(HttpMethod.GET.name())
                .willRespondWith()
                .headers(headers)
                .status(HttpStatus.OK.value()) //200
                .body(bodyString, ContentType.APPLICATION_JSON.withCharset(StandardCharsets.UTF_8))
                .toPact();
    }

    private RequestResponsePact searchPactMaker(PactDslWithProvider builder, String endpointBaseURL, SearchDTO searchParams) throws URISyntaxException, IOException {

        String responseType = "/search";
        String bodyString = jsonLoader("json/response" + endpointBaseURL + "/" + responseType + "Response.json");

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");

        mapper = new ObjectMapper();

        return builder.given("Server up - " + endpointBaseURL + "/search")
                .uponReceiving(endpointBaseURL + "/search" + " - Server up")
                .path(CONTROLLER_BASE_URL + endpointBaseURL + "/search")
                .method(HttpMethod.GET.name())
                .body(mapper.writeValueAsString(searchParams))
                .willRespondWith()
                .headers(headers)
                .status(HttpStatus.OK.value()) //200
                .body(bodyString, ContentType.APPLICATION_JSON.withCharset(StandardCharsets.UTF_8))
                .toPact();
    }

    private void checkAndCreatePactFile(MockServer mockServer, String URI_TEST) throws IOException {

        HttpResponse response = Request.Get(mockServer.getUrl() + CONTROLLER_BASE_URL + URI_TEST).execute().returnResponse();

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
    }

    private void checkAndCreateSearchPactFile(MockServer mockServer, String URI_TEST, SearchDTO searchParams) throws IOException {

        HttpUriRequest request = RequestBuilder.create(HttpMethod.GET.name())
                .setUri(mockServer.getUrl() + CONTROLLER_BASE_URL + URI_TEST)
                .setEntity(new StringEntity(mapper.writeValueAsString(searchParams), ContentType.APPLICATION_JSON))
                .build();
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200);
    }

    private String jsonLoader(String resourceURI) throws URISyntaxException, IOException {

        Path path = Paths.get(Objects.requireNonNull(EndToEndContractTest.class.getClassLoader().getResource(resourceURI)).toURI());
        return Files.readAllLines(path, StandardCharsets.UTF_8).get(0);
    }
}
