package com.businessassistantbcn.mydata.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;


import com.businessassistantbcn.mydata.service.UserService;

@Profile("test")
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = MydataController.class)
@ActiveProfiles("test")

class MyDataControllerTest {

	@Autowired
	private WebTestClient webTestClient;
	
	@MockBean
	private UserService userService;	
	
	private final String
	CONTROLLER_BASE_URL = "/businessassistantbcn/api/v1/mydata",
	RES0 = "$.results[0].";
	
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Test response Hello")
	void test() {
		final String URI_TEST = "/test";
		webTestClient.get()
					.uri(CONTROLLER_BASE_URL + URI_TEST)
					.accept(MediaType.APPLICATION_JSON)
					.exchange()
					.expectStatus().isOk()
					.expectBody(String.class)
					.value(s -> s.toString(), equalTo("Hello from BusinessAssistant MyData!!!"));
	}	
	
}
