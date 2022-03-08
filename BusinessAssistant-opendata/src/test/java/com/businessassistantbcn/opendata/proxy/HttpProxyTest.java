package com.businessassistantbcn.opendata.proxy;

import com.businessassistantbcn.opendata.dto.bigmalls.BigMallsDto;
import com.businessassistantbcn.opendata.dto.test.StarWarsVehiclesResultDto;
import com.businessassistantbcn.opendata.proxy.HttpProxy;

import io.netty.channel.ChannelOption;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import reactor.core.publisher.Mono;

import reactor.netty.http.client.HttpClient;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class HttpProxyTest {
	
	@Autowired
	private Environment env;
	@Autowired
	private HttpProxy httpProxy;
	
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
	void getRequestDataTest() throws MalformedURLException {
		URL url = new URL("http://www.bcn.cat/tercerlloc/files/mercats-centrescomercials/" +
				"opendatabcn_mercats-centrescomercials_grans-centres-comercials-js.json");

		BigMallsDto[] bigMalls = httpProxy.getRequestData(url, BigMallsDto[].class).block();

		assertEquals(28, bigMalls.length);
		assertEquals(43326348, bigMalls[0].getClassifications_data().get(0).getId());
	}

}