package com.businessassistantbcn.mydata.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.businessassistantbcn.mydata.dto.GenericSearchesResultDto;
import com.businessassistantbcn.mydata.dto.SaveSearchRequestDto;
import com.businessassistantbcn.mydata.dto.SaveSearchResponseDto;
import com.businessassistantbcn.mydata.dto.SearchResultsDto;
import com.businessassistantbcn.mydata.entity.UserSearch;
import com.businessassistantbcn.mydata.helper.JsonHelper;
import com.businessassistantbcn.mydata.repository.UserSearchesRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class UserSearchesServiceTest {
	
	@InjectMocks
	private UserSearchesService userSearchesService;
	
	@Mock
	private UserSearchesRepository userSearchesRepoMock;
	
	@Mock
	private ObjectMapper mapper = new ObjectMapper();
	
	private UserSearch search = new UserSearch();
	private Date date = new Date();
	private SaveSearchRequestDto requestDto = new SaveSearchRequestDto();
	private SaveSearchResponseDto responseDto = new SaveSearchResponseDto();

	@BeforeAll
	public void setUp() {

		HashMap<String, String> searchResult = new HashMap<String, String>();
		searchResult.put("name", "John Doe");
		searchResult.put("email", "john.doe@example.com");

		search.setSearchUuid("33b4c069-e907-45a9-8d49-2042044c56e0");
		search.setUserUuid("44c5c069-e907-45a9-8d49-2042044c56e0");
		search.setSearchDate(date);
		search.setSearchName("searchName");
		search.setSearchDetail("detail");
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

	@Test
	public void whenSaveSearch_SearchIsSavedToDB_shouldSetUserUuid() {

		when(userSearchesRepoMock.save(any())).thenReturn(search);
		
		userSearchesService.saveSearch(requestDto, "44c5c069-e907-45a9-8d49-2042044c56e0");
		
		ArgumentCaptor<UserSearch> argument = ArgumentCaptor.forClass(UserSearch.class);
		verify(userSearchesRepoMock).save(argument.capture());
		assertEquals(search.getUserUuid(), argument.getValue().getUserUuid());
	}

	@Test
	public void whenSaveSearch_shouldReturnSaveSearchResponseDto() {

		when(userSearchesRepoMock.save(any())).thenReturn(search);
		
		Mono<SaveSearchResponseDto> savedSearch = userSearchesService.saveSearch(requestDto, "44c5c069-e907-45a9-8d49-2042044c56e0");

		assertThat(savedSearch.block().toString().equals(responseDto.toString()));
		assertEquals(requestDto.getSearchName(), savedSearch.block().getSearchName());
	}
	
	@Test	
	public void getAllSearchesOfAUserByUserUuid_shouldReturnAllSearchesWithThatUserUuid() {
		List<UserSearch> searchList = new ArrayList<UserSearch>();
		searchList.add(search);
		
		String jsonSearch = JsonHelper.entityToJsonString(search);
		JsonNode jsonNodeSearch = JsonHelper.deserializeStringToJsonNode(jsonSearch);
		JsonNode[] results = new JsonNode[] {jsonNodeSearch};
		for(JsonNode searchNode : results) {
	        ObjectNode object = (ObjectNode) searchNode;
	        object.remove("searchResult");
		}
		GenericSearchesResultDto<JsonNode> genericDto = new GenericSearchesResultDto<JsonNode>();
		genericDto.setCount(1);
		genericDto.setOffset(0);
		genericDto.setLimit(-1);
		genericDto.setResults(results);
		
		when(userSearchesRepoMock.findByUserUuid("44c5c069-e907-45a9-8d49-2042044c56e0")).thenReturn(searchList);
	
		Mono<GenericSearchesResultDto<JsonNode>> allUserSearches = userSearchesService.getAllUserSearches("44c5c069-e907-45a9-8d49-2042044c56e0", 0, -1);
		
		assertThat(genericDto.equals(allUserSearches.block()));
	}

	@Test
	public void testGetSearchResults() {
		String jsonSearch = JsonHelper.entityToJsonString(search);
		JsonNode jsonNodeSearch = JsonHelper.deserializeStringToJsonNode(jsonSearch);
		JsonNode[] results = new JsonNode[] {jsonNodeSearch};
		SearchResultsDto searchResultsDto = new SearchResultsDto();
		searchResultsDto.setResults(results);
		
		when(userSearchesRepoMock.findById("33b4c069-e907-45a9-8d49-2042044c56e0")).thenReturn(Optional.of(search));
		
		Mono<SearchResultsDto> searchResults = userSearchesService.getSearchResults("33b4c069-e907-45a9-8d49-2042044c56e0","44c5c069-e907-45a9-8d49-2042044c56e0");
		
		assertThat(searchResultsDto.equals(searchResults.block()));
	}

}
