package com.businessassistantbcn.mydata.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.*;

import com.businessassistantbcn.mydata.config.PropertiesConfig;
import com.businessassistantbcn.mydata.dto.DetailsResponseDTO;
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
import com.businessassistantbcn.mydata.entity.UserSearch;
import com.businessassistantbcn.mydata.helper.JsonHelper;
import com.businessassistantbcn.mydata.repository.IUserSearchesRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@ExtendWith({MockitoExtension.class, SpringExtension.class})
@TestInstance(Lifecycle.PER_CLASS)
class UserSearchesServiceTest {
	
	@InjectMocks
	private UserSearchesService userSearchesService;

	@MockBean
	private PropertiesConfig propertiesConfig;
	
	@Mock
	private IUserSearchesRepository userSearchesRepoMock;
	
	@Mock
	private ObjectMapper mapper = new ObjectMapper();
	
	private UserSearch search = new UserSearch();
	private Date date = new Date();
	private SaveSearchRequestDto requestDto = new SaveSearchRequestDto();
	private SaveSearchResponseDto responseDto = new SaveSearchResponseDto();

	private static final Integer LIMIT_SEARCHES_VALUE = 10;
	private static final Boolean IS_LIMIT_ENABLED = true;
	private static final String LIMIT_SEARCHES_MESSAGE = "Has reached the limit of searches";

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

		propertiesConfig.setLimitValue(LIMIT_SEARCHES_VALUE);
		propertiesConfig.setIsLimitEnabled(IS_LIMIT_ENABLED);
		propertiesConfig.setErrorMessage(LIMIT_SEARCHES_MESSAGE);

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
		
		Mono<?> savedSearch = userSearchesService.saveSearch(requestDto, "44c5c069-e907-45a9-8d49-2042044c56e0");

		assertThat(savedSearch.block().toString().equals(responseDto.toString()));
		assertEquals(requestDto.getSearchName(), ((Mono<SaveSearchResponseDto>)savedSearch).block().getSearchName());
	}

	@Test
	public void whenLimitSearchesIsExceded_ShouldReturnMonoErrorDetailsResponse() {

		List<UserSearch> searchList = new ArrayList<>();

		//simulamos un usuario que ha realizado + 10 búsquedas
		for (int i = 0; i < 11; i++) {
			searchList.add(search);
		}

		when(userSearchesRepoMock.findByUserUuid(search.getUserUuid())).thenReturn(Optional.of(searchList));

		when(propertiesConfig.getLimitValue()).thenReturn(LIMIT_SEARCHES_VALUE);
		when(propertiesConfig.getIsLimitEnabled()).thenReturn(IS_LIMIT_ENABLED);
		when(propertiesConfig.getErrorMessage()).thenReturn(LIMIT_SEARCHES_MESSAGE);

		boolean limitExceded = userSearchesService.checkLimitExceededUserSearches(search.getUserUuid());
		assertTrue(limitExceded);

		Mono<DetailsResponseDTO> detailsResponseMono = userSearchesService.saveSearch(requestDto, search.getUserUuid())
						.thenReturn(new DetailsResponseDTO("User" + search.getUserUuid() + LIMIT_SEARCHES_MESSAGE,
								new Date()));

		assertNotNull(detailsResponseMono.block());
		assertEquals("User" + search.getUserUuid() + LIMIT_SEARCHES_MESSAGE, detailsResponseMono.block().getErrorMessage());

		verify(userSearchesRepoMock, never()).save(any());
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
		
		//when(userSearchesRepoMock.findByUserUuid("44c5c069-e907-45a9-8d49-2042044c56e0")).thenReturn(searchList);
	
		Mono<GenericSearchesResultDto<JsonNode>> allUserSearches = userSearchesService.getAllUserSearches("44c5c069-e907-45a9-8d49-2042044c56e0", 0, -1);
		
		assertThat(genericDto.equals(allUserSearches.block()));
	}

//	@Test
//	public void testGetSearchResults() {
//		String jsonSearch = JsonHelper.entityToJsonString(search);
//		JsonNode jsonNodeSearch = JsonHelper.deserializeStringToJsonNode(jsonSearch);
//		JsonNode[] results = new JsonNode[] {jsonNodeSearch};
//		SearchResultsDto searchResultsDto = new SearchResultsDto();
//		searchResultsDto.setResults(results);
//
//		when(userSearchesRepoMock.findById("33b4c069-e907-45a9-8d49-2042044c56e0")).thenReturn(Optional.of(search));
//
//		Mono<SearchResultsDto> searchResults = userSearchesService.getSearchResults("33b4c069-e907-45a9-8d49-2042044c56e0","44c5c069-e907-45a9-8d49-2042044c56e0");
//
//		assertThat(searchResultsDto.equals(searchResults.block()));
//	}


	@Test
	void whenSearchIsDeleted_ShouldReturnMonoVoid() {

		doNothing().when(userSearchesRepoMock).deleteById(anyString());

		when(userSearchesRepoMock.findOneBySearchUuidAndUserUuid(search.getSearchUuid(),search.getUserUuid())).thenReturn(Optional.ofNullable((search)));

		Optional<UserSearch> searchToDelete = userSearchesRepoMock.findOneBySearchUuidAndUserUuid(search.getSearchUuid(),search.getUserUuid());
		assertNotNull(searchToDelete);
		assertEquals("33b4c069-e907-45a9-8d49-2042044c56e0", searchToDelete.get().getSearchUuid());

		Mono<?> monoEmpty = userSearchesService.deleteUserSearchBySearchUuid(searchToDelete.get().getUserUuid(), searchToDelete.get().getSearchUuid());

		StepVerifier.create(monoEmpty)
		.expectComplete()
		.verify();

		when(userSearchesRepoMock.findOneBySearchUuidAndUserUuid(search.getSearchUuid(),search.getUserUuid())).thenReturn((Optional.empty()));

		Optional<UserSearch> searchDeleted = userSearchesRepoMock.findOneBySearchUuidAndUserUuid(search.getSearchUuid(),search.getUserUuid());
		assertThat(searchDeleted.isEmpty());

		verify(userSearchesRepoMock, times(1)).deleteById(anyString());
	}

	@Test
	void whenUserTryToDeleteSearchAndUserSearchNotExists_ShouldReturnDetailsResponse() {

		when(userSearchesRepoMock.findOneBySearchUuidAndUserUuid("searchUuidNotExists","userUuidNotExists")).thenReturn(Optional.empty());

		Optional<UserSearch> searchDoesntExists = userSearchesRepoMock.findOneBySearchUuidAndUserUuid("searchUuidNotExists","userUuidNotExists");
		assertNotNull(searchDoesntExists);
		assertThat(searchDoesntExists.isEmpty());

		Mono<?> result = userSearchesService.deleteUserSearchBySearchUuid("userUuidNotExists","searchUuidNotExists");
		Object response = result.block();
		assertThat(response).isInstanceOf(DetailsResponseDTO.class);

		verify(userSearchesRepoMock, never()).deleteById(anyString());

	}
}
