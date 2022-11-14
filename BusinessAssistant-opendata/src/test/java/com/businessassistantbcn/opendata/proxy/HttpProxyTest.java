package com.businessassistantbcn.opendata.proxy;

import com.businessassistantbcn.opendata.dto.input.bigmalls.BigMallsDto;
import com.businessassistantbcn.opendata.dto.test.StarWarsVehiclesResultDto;
import io.netty.channel.ChannelOption;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Ignore;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class HttpProxyTest {
	
	@Autowired
	private Environment env;
	@Autowired
	private HttpProxy httpProxy;

	public static MockWebServer mockWebServer;
	private URL url;
	private URI uri;
	private static final String JSON_FILENAME_BIG_MALLS = "json/twoBigMallsForTesting.json";
	private static String bigMallsAsString;

	@BeforeAll
	static void setUp() throws IOException, URISyntaxException {
		mockWebServer = new MockWebServer();
		mockWebServer.start();

		bigMallsAsString = Files.readAllLines(
				Paths.get(HttpProxyTest.class.getClassLoader().getResource(JSON_FILENAME_BIG_MALLS).toURI()),
				StandardCharsets.UTF_8
		).get(0);
	}

	@AfterAll
	static void tearDown() throws IOException {
		mockWebServer.shutdown();
	}
	
	@DisplayName("Timeout verification")
	@Test
	void timeoutTest() {
		HttpClient client1 = HttpClient.create()
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1); // Absurd 1 ms connection timeout
		WebClient briefClient = httpProxy.client.mutate()
				.clientConnector(new ReactorClientHttpConnector(client1))
				.build();
		Assertions.assertThrows(WebClientRequestException.class, () ->
				briefClient.get()
						.uri(env.getProperty("ds_test"))
						.exchangeToMono(response -> 
							response.statusCode().equals(HttpStatus.OK) ? 
							response.bodyToMono(StarWarsVehiclesResultDto.class) :
							response.createException().flatMap(Mono::error))
						.block());
	}

	@Test
	void getRequestDataURLTest() throws MalformedURLException, URISyntaxException {
		mockWebServer.enqueue(new MockResponse().addHeader("Content-Type", "application/json").setBody(bigMallsAsString));
		url = new URL(String.format("http://localhost:%s", mockWebServer.getPort()));
		BigMallsDto[] bigMalls = httpProxy.getRequestData(url.toURI(), BigMallsDto[].class).block();

		assertEquals(2, bigMalls.length);
		assertEquals(43326349, bigMalls[0].getClassifications_data().get(0).getId());
	}

	@Test
	void getRequestDataBackupTest() {
		uri = URI.create("api/big-mallsData.json");
		BigMallsDto[] bigMalls = httpProxy.getRequestData(uri, BigMallsDto[].class).block();

		assertNotNull(bigMalls);
		assertEquals(27, bigMalls.length);
	}

	@Test
	void getRequestDataVoidTest() {
		uri = URI.create("some/wrong/uri");
		BigMallsDto[] bigMalls = httpProxy.getRequestData(uri, BigMallsDto[].class).block();

		assertNull(bigMalls);
	}

	@Test
	void getRequestDataServerIsDownTest() throws MalformedURLException {
		mockWebServer.enqueue(new MockResponse().setResponseCode(500));
		url = new URL(String.format("http://localhost:%s", mockWebServer.getPort()));
		assertThrows(WebClientResponseException.class, () -> httpProxy.getRequestData(url.toURI(), BigMallsDto[].class).block());
	}
}