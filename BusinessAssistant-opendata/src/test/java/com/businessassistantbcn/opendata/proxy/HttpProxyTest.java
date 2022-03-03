package com.businessassistantbcn.opendata.proxy;

import com.businessassistantbcn.opendata.dto.bigmalls.BigMallsDto;
import com.businessassistantbcn.opendata.dto.test.StarWarsVehiclesResultDto;

import io.netty.channel.ChannelOption;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
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
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class HttpProxyTest {
	
	@Autowired
	private Environment env;
	@Autowired
	private HttpProxy httpProxy;

	public static MockWebServer mockWebServer;
	private URL url;

	@BeforeAll
	static void setUp() throws IOException {
		mockWebServer = new MockWebServer();
		mockWebServer.start();
	}

	@BeforeEach
	void initialize() throws MalformedURLException {
		this.url = new URL(String.format("http://localhost:%s", mockWebServer.getPort()));
	}

	@AfterAll
	static void tearDown() throws IOException {
		mockWebServer.shutdown();
	}
	
	@DisplayName("Timeout verification")
	@Test
	public void timeoutTest() {
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
	void getRequestDataServerIsDownTest() throws MalformedURLException {
		mockWebServer.enqueue(new MockResponse().setResponseCode(500));

		assertThrows(WebClientResponseException.class, () -> httpProxy.getRequestData(url, BigMallsDto[].class).block());

	}

//	Delete?
//	@Test
//	void getRequestDataTest() throws MalformedURLException {
//		URL url = new URL("http://www.bcn.cat/tercerlloc/files/mercats-centrescomercials/" +
//				"opendatabcn_mercats-centrescomercials_grans-centres-comercials-js.json");
//
//		BigMallsDto[] bigMalls = httpProxy.getRequestData(url, BigMallsDto[].class).block();
//
//		assertEquals(28, bigMalls.length);
//		assertEquals(43326348, bigMalls[0].getClassifications_data().get(0).getId());
//	}

//	public <T> Mono<T> getRequestData(URL url, Class<T> clazz){
//		WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.method(HttpMethod.GET);
//		WebClient.RequestBodySpec bodySpec = uriSpec.uri(URI.create(url.toString()));
//		return bodySpec.retrieve().bodyToMono(clazz);
//	}

}