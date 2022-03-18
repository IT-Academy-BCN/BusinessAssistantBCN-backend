package com.businessassistantbcn.mydata.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.businessassistantbcn.mydata.dto.SaveSearchResponseDto;
import com.businessassistantbcn.mydata.entities.Search;
import com.businessassistantbcn.mydata.helper.JsonHelper;
import com.businessassistantbcn.mydata.repository.MySearchesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	
	@InjectMocks
	UserService userService;
	
	@Mock
	MySearchesRepository mysearchesRepoMock;
	
	//TODO

//	@Test
//	void testSaveUserSearch_UserUuidOK_returnsSavedSearch() {
//		final Search search = new Search();
//		search.setSearchUuid("searchUuid");
//		search.setUserUuid("userUuid");
//		search.setSearchName("searchName");
//		search.setSearchDate(new Date());
//		search.setSearchDetail("detail");
//		HashMap<String,String> searchResult = new HashMap<String,String>();
//		searchResult.put("name","John Doe");
//		searchResult.put("email","john.doe@example.com");
//		ObjectMapper mapper = new ObjectMapper();
//		search.setSearchResult(mapper.valueToTree(searchResult));
//		
//		String jsonSearch = "{\"searchUuid\":\"searchUuid\",\"userUuid\":\"userUuid\",\"searchDate\":1647418908722,\"searchName\":\"searchName\",\"searchDetail\":\"detail\",\"searchResult\":{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\"}}";
//		
//		SearchDto savedSearchDto = userService.saveSearch(jsonSearch, "userUuid");
//    	
//		verify(mysearchesRepoMock).save(any(Search.class));
//		assertTrue("searchUuid".equals(savedSearchDto.getSearchUuid()));
//	}

//	@Test
//	void testGetAllSearches() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetSearchResults() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testFilterJsonNode() {
//		fail("Not yet implemented");
//	}

}
