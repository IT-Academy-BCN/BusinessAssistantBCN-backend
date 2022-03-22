package com.businessassistantbcn.mydata.controller;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.businessassistantbcn.mydata.dto.GenericResultDto;
import com.businessassistantbcn.mydata.dto.SaveSearchRequestDto;
import com.businessassistantbcn.mydata.dto.SaveSearchResponseDto;
import com.businessassistantbcn.mydata.entities.Search;
import com.businessassistantbcn.mydata.helper.DtoHelper;
import com.businessassistantbcn.mydata.helper.JsonHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;


import com.businessassistantbcn.mydata.service.UserSearchesService;

import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Profile("test")
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = MydataController.class)
@ActiveProfiles("test")

class MyDataControllerTest {

	@Autowired
	private WebTestClient webTestClient;
	
	@MockBean
	private UserSearchesService userService;

	
	private final String
	CONTROLLER_BASE_URL = "/businessassistantbcn/api/v1/mydata",
	RES0 = "$.results[0].";


	private Search search = new Search();
	private Date date = new Date();
	private SaveSearchRequestDto requestDto = new SaveSearchRequestDto();
	private SaveSearchResponseDto responseDto = new SaveSearchResponseDto();

	@BeforeEach
	public void setUp() {

		HashMap<String, String> searchResult = new HashMap<String, String>();
		searchResult.put("name", "Groupe Zannier Espanya");
		searchResult.put("web", "http://www.kidilizgroup.com");

		search.setSearchUuid("33b4c069-e907-45a9-8d49-2042044c56e0");
		search.setUserUuid("44c5c069-e907-45a9-8d49-2042044c56e0");
		search.setSearchDate(date);
		search.setSearchName("searchName");
		search.setSearchDetail("detail");
		ObjectMapper mapper = new ObjectMapper();
		search.setSearchResult(mapper.valueToTree(searchResult));

		requestDto.setSearchName("searchName");
		requestDto.setSearchDetail("detail");
		requestDto.setSearchResult(mapper.valueToTree(searchResult));

		responseDto.setSearchUuid("33b4c069-e907-45a9-8d49-2042044c56e0");
		responseDto.setUserUuid("44c5c069-e907-45a9-8d49-2042044c56e0");
		responseDto.setSearchDate(date);
		responseDto.setSearchName("searchName");
		responseDto.setSearchDetail("detail");
		responseDto.setSearchResult(mapper.valueToTree(searchResult));

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

	@Test
	public void getAllsearchesByUserTest() throws MalformedURLException, JsonProcessingException {

		final String URI_ALL_SEARCHES = "/mysearches/{user_uuid}";

		List<Search> searchList = new ArrayList<Search>();
		searchList.add(search);

		String jsonSearch = JsonHelper.entityToJsonString(search);
		JsonNode jsonNodeSearch = JsonHelper.deserializeToJsonNode(jsonSearch);
		JsonNode[] results = new JsonNode[] {jsonNodeSearch};
		for(JsonNode searchNode : results) {
			ObjectNode object = (ObjectNode) searchNode;
			object.remove("searchResult");
		}
		GenericResultDto<JsonNode> genericDto = new GenericResultDto<JsonNode>();
		genericDto.setCount(1);
		genericDto.setOffset(0);
		genericDto.setLimit(-1);
		genericDto.setResults(results);

		when(userService.getAllSearches("44c5c069-e907-45a9-8d49-2042044c56e0",0,-1)).thenReturn(Mono.just(genericDto));

		webTestClient.get()
				.uri(CONTROLLER_BASE_URL + URI_ALL_SEARCHES, "44c5c069-e907-45a9-8d49-2042044c56e0")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath(RES0 + "searchUuid").isNotEmpty()
				.jsonPath(RES0 + "searchUuid").isEqualTo("33b4c069-e907-45a9-8d49-2042044c56e0")
				.jsonPath(RES0 + "userUuid").isNotEmpty()
				.jsonPath(RES0 + "userUuid").isEqualTo("44c5c069-e907-45a9-8d49-2042044c56e0")
				.jsonPath(RES0 + "searchDate").isNotEmpty()
				.jsonPath(RES0 + "searchDate").isEqualTo(date)
				.jsonPath(RES0 + "searchName").isNotEmpty()
				.jsonPath(RES0 + "searchName").isEqualTo("searchName")
				.jsonPath(RES0 + "searchDetail").isNotEmpty()
				.jsonPath(RES0 + "searchDetail").isEqualTo("detail");

		verify(userService).getAllSearches("44c5c069-e907-45a9-8d49-2042044c56e0",0,-1);
	}
	}
