package com.businessassistantbcn.mydata.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.*;

import com.businessassistantbcn.mydata.config.PropertiesConfig;
import com.businessassistantbcn.mydata.handleError.ErrorDetailsResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;


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
		assertEquals(requestDto.getSearchName(), ((Mono<SaveSearchResponseDto)savedSearch.block()).getSearchName());
	}

	@Test
	public void whenLimitSearchesIsExceded_ShouldReturnMonoErrorDetailsResponse() {

		List<UserSearch> searchList = new ArrayList<>();

		//simulamos un usuario que ha realizado + 10 búsquedas
		for (int i = 0; i < 11; i++) {
			searchList.add(search);
		}

		when(userSearchesRepoMock.findByUserUuid(search.getUserUuid())).thenReturn(searchList);

		when(propertiesConfig.getLimitValue()).thenReturn(LIMIT_SEARCHES_VALUE);
		when(propertiesConfig.getIsLimitEnabled()).thenReturn(IS_LIMIT_ENABLED);
		when(propertiesConfig.getErrorMessage()).thenReturn(LIMIT_SEARCHES_MESSAGE);

		boolean limitExceded = userSearchesService.checkLimitExceededUserSearches(search.getUserUuid());
		assertTrue(limitExceded);

		Mono<ErrorDetailsResponse> errorDetailsResponseMono = userSearchesService.saveSearch(requestDto, search.getUserUuid())
						.thenReturn(new ErrorDetailsResponse("User" + search.getUserUuid() + LIMIT_SEARCHES_MESSAGE,
								HttpStatus.OK,
								new Date()));

		assertNotNull(errorDetailsResponseMono.block());
		assertEquals("User" + search.getUserUuid() + LIMIT_SEARCHES_MESSAGE, errorDetailsResponseMono.block().getErrorMessage());
		assertEquals(HttpStatus.OK, errorDetailsResponseMono.block().getHttpStatus());

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

		when(userSearchesRepoMock.existsBySearchUuid(search.getSearchUuid())).thenReturn((true));
		when(userSearchesRepoMock.existsByUserUuid(search.getUserUuid())).thenReturn((true));
		when(userSearchesRepoMock.findOneBySearchUuid(search.getSearchUuid())).thenReturn(Optional.ofNullable((search)));

		Optional<UserSearch> searchToDelete = userSearchesRepoMock.findOneBySearchUuid(search.getSearchUuid());
		assertNotNull(searchToDelete);
		assertEquals("33b4c069-e907-45a9-8d49-2042044c56e0", searchToDelete.get().getSearchUuid());

		Mono<Void> monoEmpty = userSearchesService.deleteUserSearchBySearchUuid(searchToDelete.get().getUserUuid(), searchToDelete.get().getSearchUuid());
		monoEmpty.block(); //para que bloquee el flujo asincrono y se complete la operación de eliminación de la bdd

		when(userSearchesRepoMock.findOneBySearchUuid(search.getSearchUuid())).thenReturn((null));

		Optional<UserSearch> searchDeleted = userSearchesRepoMock.findOneBySearchUuid(search.getSearchUuid());
		assertNull(searchDeleted);

		verify(userSearchesRepoMock, times(1)).deleteById(searchToDelete.get().getSearchUuid());
	}

	@Test
	void whenUserTryToDeleteSearch_UserUuidNotFound() {

		when(userSearchesRepoMock.existsBySearchUuid(search.getSearchUuid())).thenReturn((true));
		when(userSearchesRepoMock.existsByUserUuid("UserUuidNotExists")).thenReturn((false));
		when(userSearchesRepoMock.findOneBySearchUuid(search.getSearchUuid())).thenReturn(Optional.ofNullable((search)));

		Optional<UserSearch> searchToBeDeleted = userSearchesRepoMock.findOneBySearchUuid(search.getSearchUuid());
		assertNotNull(searchToBeDeleted);
		assertEquals("33b4c069-e907-45a9-8d49-2042044c56e0", searchToBeDeleted.get().getSearchUuid());

		assertThrows(ResponseStatusException.class, () -> {
			userSearchesService.deleteUserSearchBySearchUuid("UserUuidNotExists", searchToBeDeleted.get().getSearchUuid()).block();
		});

		verify(userSearchesRepoMock, times(1)).existsByUserUuid("UserUuidNotExists");
		verify(userSearchesRepoMock, never()).deleteById(searchToBeDeleted.get().getSearchUuid());
	}

	@Test
	void whenUserTryToDeleteSearch_SearchUuidNotFound() {

		when(userSearchesRepoMock.existsBySearchUuid("SearchUuidNotExists")).thenReturn((false));
		when(userSearchesRepoMock.existsByUserUuid(search.getUserUuid())).thenReturn((true));

		boolean userUuidExists = userSearchesRepoMock.existsByUserUuid(search.getUserUuid());
		assertTrue(userUuidExists);

		Optional<UserSearch> searchToBeDeleted = userSearchesRepoMock.findOneBySearchUuid("SearchUuidNotExists");
		assertTrue(searchToBeDeleted.isEmpty());

		assertThrows(ResponseStatusException.class, () -> {
			userSearchesService.deleteUserSearchBySearchUuid(search.getUserUuid(),"SearchUuidNotExists").block();
		});

		verify(userSearchesRepoMock, times(1)).existsBySearchUuid("SearchUuidNotExists");
		verify(userSearchesRepoMock, never()).deleteById("SearchUuidNotExists");

	}

	@Test
	void whenUserTryToDeleteSearch_searchDoesNotBelongToUser() {

		UserSearch userSearch1 = new UserSearch();
		userSearch1.setUserUuid("userUuid1");
		userSearch1.setSearchUuid("searchUuid1");

		UserSearch userSearch2 = new UserSearch();
		userSearch2.setUserUuid("userUuid2");
		userSearch2.setSearchUuid("searchUuid2");

		when(userSearchesRepoMock.existsBySearchUuid(userSearch2.getSearchUuid())).thenReturn((true));
		when(userSearchesRepoMock.existsByUserUuid(userSearch1.getUserUuid())).thenReturn((true));

		when(userSearchesRepoMock.findOneBySearchUuid(userSearch1.getSearchUuid())).thenReturn(Optional.of((userSearch1)));
		when(userSearchesRepoMock.findOneBySearchUuid(userSearch2.getSearchUuid())).thenReturn(Optional.of((userSearch2)));

		Optional<UserSearch> searchFromUser1 = userSearchesRepoMock.findOneBySearchUuid(userSearch1.getSearchUuid());
		assertNotNull(searchFromUser1);

		Optional<UserSearch> searchFromUser2 = userSearchesRepoMock.findOneBySearchUuid(userSearch2.getSearchUuid());
		assertNotNull(searchFromUser2);

		assertThrows(ResponseStatusException.class, () -> {
			userSearchesService.deleteUserSearchBySearchUuid("userUuid1", "searchUuid2").block();
		});

		Optional<UserSearch> searchFromUser2NotDeleted = userSearchesRepoMock.findOneBySearchUuid(userSearch2.getSearchUuid());
		assertNotNull(searchFromUser2NotDeleted);
		assertEquals("searchUuid2", searchFromUser2NotDeleted.get().getSearchUuid());

		verify(userSearchesRepoMock, never()).deleteById(userSearch2.getSearchUuid());
	}
}
