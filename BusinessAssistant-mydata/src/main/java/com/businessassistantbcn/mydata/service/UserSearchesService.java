package com.businessassistantbcn.mydata.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.businessassistantbcn.mydata.dto.GenericSearchesResultDto;
import com.businessassistantbcn.mydata.dto.SaveSearchRequestDto;
import com.businessassistantbcn.mydata.dto.SaveSearchResponseDto;
import com.businessassistantbcn.mydata.dto.SearchResultsDto;
import com.businessassistantbcn.mydata.entity.UserSearch;
import com.businessassistantbcn.mydata.helper.DtoHelper;
import com.businessassistantbcn.mydata.helper.JsonHelper;
import com.businessassistantbcn.mydata.repository.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Mono;

@Service
public class UserSearchesService {
	private static final Logger log = LoggerFactory.getLogger(UserSearchesService.class);
	
	@Autowired
    IUserSearchesRepository userSearchesRepo;

    @Autowired
    MyDataRepository myDataRepo;


    public String getTest(){
        return myDataRepo.getMyData();
    }




	public UserSearchesService(IUserSearchesRepository userSearchesRepo) {
		this.userSearchesRepo = userSearchesRepo;
	}

	public Mono<SaveSearchResponseDto> saveSearch(SaveSearchRequestDto searchToSave, String user_uuid) {
		UserSearch search = new UserSearch();
		search = DtoHelper.mapSaveSearchRequestDtoToSearch(searchToSave, user_uuid);
		
		UserSearch savedSearch = userSearchesRepo.save(search);
		
		return Mono.just(DtoHelper.mapSearchToSaveSearchResponseDto(savedSearch));
	}
	
	public Mono<GenericSearchesResultDto<JsonNode>> getAllUserSearches(String user_uuid, int offset, int limit) {
		List<JsonNode> allUserSearches = userSearchesRepo.findByUserUuid(user_uuid).stream()
												.map(search -> JsonHelper.entityToJsonString(search))
												.map(string -> JsonHelper.deserializeStringToJsonNode(string))
												.collect(Collectors.toList());
		for(JsonNode searchNode : allUserSearches) {
			        ObjectNode object = (ObjectNode) searchNode;
			        object.remove("searchResult");
		}
		
		JsonNode[] pageFilteredResults = filterJsonNodeResultsPagination(mapListToJsonNodeArray(allUserSearches), offset, limit);
		
		GenericSearchesResultDto<JsonNode> result = createUserSearchesResultDto(pageFilteredResults, offset, limit);
		
		return Mono.just(result);
	}
	
	private JsonNode[] filterJsonNodeResultsPagination(JsonNode[] allResults, int offset, int limit) {
		JsonNode[] toReturn = null;
		if (offset > allResults.length - 1)
			toReturn = Arrays.copyOfRange(allResults, 0, 0); 
		// if limit == -1 it means that we should get all Data
		else if (limit == -1)
			toReturn = Arrays.copyOfRange(allResults,offset,allResults.length);
		else {
			// If the ending point is out of bounce, we set the ending point in the last
			// point of the array +1
			int end = offset + limit; // the ending index +1
			if (end > allResults.length)
				end = allResults.length;
			// Makes the subarray. The end point is excluded thats why we do +1.
			toReturn = Arrays.copyOfRange(allResults,offset,end);
		}
		return toReturn;
	}
	
	private JsonNode[] mapListToJsonNodeArray(List<JsonNode> pageFilteredResults) {
		JsonNode[] resultsForDto = {};
		if (pageFilteredResults.size() > 0) {
			resultsForDto = new JsonNode[pageFilteredResults.size()];
			pageFilteredResults.toArray(resultsForDto);
		}
		return resultsForDto;
	}
	
	
	private GenericSearchesResultDto<JsonNode> createUserSearchesResultDto(JsonNode[] pageFilteredResults,int offset, int limit){
		GenericSearchesResultDto<JsonNode> result = new GenericSearchesResultDto<JsonNode>();
		result.setOffset(offset);
		result.setLimit(limit);
		if(pageFilteredResults!=null) {
			result.setCount(pageFilteredResults.length);
			result.setResults(pageFilteredResults);
		}else {
			result.setCount(0);
			JsonNode[] resultsForDto = new JsonNode[1];
			JsonNode results = JsonHelper.deserializeStringToJsonNode("{\"NOT FOUND\":\"The required search does not exist\"}");
			resultsForDto[0]=results;
			result.setResults(resultsForDto);
		}
		return result;
	}
	
	public Mono<SearchResultsDto> getSearchResults(String search_uuid, String user_uuid){
		SearchResultsDto result = null;
		if (!userSearchesRepo.findById(search_uuid).isPresent()) {
			result = createSearchResultsDto(null);
			log.info("No Search found with UUID="+search_uuid);
		} else if (!userSearchesRepo.findById(search_uuid).get().getUserUuid().equals(user_uuid)) {
			result = createSearchResultsDto(null);
			log.info("User with UUID="+user_uuid+"does not have a search with UUID="+search_uuid);
		}else {
			UserSearch search = userSearchesRepo.findById(search_uuid).get();

			JsonNode searchResult = search.getSearchResult();
			
			result = createSearchResultsDto(mapJsonNodeToList(searchResult));
		}
		return Mono.just(result);
	}
	
	private SearchResultsDto createSearchResultsDto(List<JsonNode> searchResults){
		SearchResultsDto result = new SearchResultsDto();
		if(searchResults!=null) {
			result.setResults(mapListToJsonNodeArray(searchResults));
		}else {
			JsonNode[] resultsForDto = new JsonNode[1];
			
			JsonNode results = JsonHelper.deserializeStringToJsonNode("{\"NOT FOUND\":\"The required search does not exist\"}");
			resultsForDto[0]=results;
			
			result.setResults(resultsForDto);
		}
		
		return result;
	}
	
	private List<JsonNode> mapJsonNodeToList(JsonNode searchResult){
		List<JsonNode> allResults = new ArrayList<JsonNode>();
		for (int i = 0; i < searchResult.size(); i++) {
			allResults.add(searchResult.get(i));
		}
		return allResults;
	}
}
