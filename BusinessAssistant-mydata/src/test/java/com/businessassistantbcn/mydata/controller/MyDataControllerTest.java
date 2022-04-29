package com.businessassistantbcn.mydata.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.businessassistantbcn.mydata.dto.GenericSearchesResultDto;
import com.businessassistantbcn.mydata.dto.SaveSearchRequestDto;
import com.businessassistantbcn.mydata.dto.SaveSearchResponseDto;
import com.businessassistantbcn.mydata.dto.SearchResultsDto;
import com.businessassistantbcn.mydata.entities.UserSearch;
import com.businessassistantbcn.mydata.helper.JsonHelper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;


import com.businessassistantbcn.mydata.service.UserSearchesService;

import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = MydataController.class)
//@SpringBootTest()
//@Import(UserSearchesService.class)
class MyDataControllerTest {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private UserSearchesService userService;


	private final String
			CONTROLLER_BASE_URL = "/businessassistantbcn/api/v1/mydata",
			RES0 = "$.results[0].";


	private UserSearch search = new UserSearch();
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
	public void saveSearchTest(){

		final String URI_SAVE_SEARCH="/mysearches/{user_uuid}";

		when(userService.saveSearch(requestDto,"44c5c069-e907-45a9-8d49-2042044c56e0")).thenReturn(Mono.just(responseDto));

		webTestClient.post()
				.uri(CONTROLLER_BASE_URL + URI_SAVE_SEARCH, "44c5c069-e907-45a9-8d49-2042044c56e0")
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(requestDto),SaveSearchRequestDto.class)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.equals(Mono.just(responseDto));

	}

	@Test
	public void getAllsearchesByUserTest() {

		final String URI_ALL_SEARCHES = "/mysearches/{user_uuid}";

		List<UserSearch> searchList = new ArrayList<UserSearch>();
		searchList.add(search);

		String jsonSearch = JsonHelper.entityToJsonString(search);
		JsonNode jsonNodeSearch = JsonHelper.deserializeStringToJsonNode(jsonSearch);
		JsonNode[] results = new JsonNode[]{jsonNodeSearch};
		for (JsonNode searchNode : results) {
			ObjectNode object = (ObjectNode) searchNode;
			object.remove("searchResult");
		}
		GenericSearchesResultDto<JsonNode> genericDto = new GenericSearchesResultDto<JsonNode>();
		genericDto.setCount(1);
		genericDto.setOffset(0);
		genericDto.setLimit(-1);
		genericDto.setResults(results);

		when(userService.getAllUserSearches("44c5c069-e907-45a9-8d49-2042044c56e0", 0, -1)).thenReturn(Mono.just(genericDto));

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

		verify(userService).getAllUserSearches("44c5c069-e907-45a9-8d49-2042044c56e0", 0, -1);
	}

	@Test
	public void getSearchResultsTest() {
		final String URI_ONE_SEARCH = "/mysearches/{user_uuid}/search/{search_uuid}";

		JsonNode searchResult = search.getSearchResult();
		JsonNode[] results = new JsonNode[]{searchResult};
		SearchResultsDto searchResultsDto = new SearchResultsDto();
		searchResultsDto.setResults(results);

		when(userService.getSearchResults("44c5c069-e907-45a9-8d49-2042044c56e0", "33b4c069-e907-45a9-8d49-2042044c56e0")).thenReturn(Mono.just(searchResultsDto));

		webTestClient.get()
				.uri(CONTROLLER_BASE_URL + URI_ONE_SEARCH, "44c5c069-e907-45a9-8d49-2042044c56e0", "33b4c069-e907-45a9-8d49-2042044c56e0")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().valueEquals("Content-Type", "application/json")
				.expectBody()
				.jsonPath(RES0 + "name[0]",("Groupe Zannier Espanya"));

		verify(userService).getSearchResults("33b4c069-e907-45a9-8d49-2042044c56e0", "44c5c069-e907-45a9-8d49-2042044c56e0");
	}
}


