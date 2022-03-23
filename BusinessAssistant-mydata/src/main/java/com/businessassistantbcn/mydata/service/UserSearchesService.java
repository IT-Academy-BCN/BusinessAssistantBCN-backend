package com.businessassistantbcn.mydata.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.businessassistantbcn.mydata.dto.GenericResultDto;
import com.businessassistantbcn.mydata.dto.SaveSearchRequestDto;
import com.businessassistantbcn.mydata.dto.SaveSearchResponseDto;
import com.businessassistantbcn.mydata.entities.Search;
import com.businessassistantbcn.mydata.helper.DtoHelper;
import com.businessassistantbcn.mydata.helper.JsonHelper;
import com.businessassistantbcn.mydata.repository.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class UserSearchesService {
	
	@Autowired
	UserSearchesRepository userSearchesRepo;

	public UserSearchesService(UserSearchesRepository userSearchesRepo) {
		this.userSearchesRepo = userSearchesRepo;
	}

	public Mono<SaveSearchResponseDto> saveSearch(SaveSearchRequestDto searchToSave, String user_uuid) {
		Search search = new Search();
		search = DtoHelper.mapSaveSearchRequestDtoToSearch(searchToSave, user_uuid);
		
		Search savedSearch = userSearchesRepo.save(search);
		
		return Mono.just(DtoHelper.mapSearchToSaveSearchResponseDto(savedSearch));
	}
	
	public Mono<GenericResultDto<JsonNode>> getAllSearches(String user_uuid, int offset, int limit) {
		List<JsonNode> allUserSearches = userSearchesRepo.findByUserUuid(user_uuid).stream()
												.map(search -> JsonHelper.entityToJsonString(search))
												.map(string -> JsonHelper.deserializeToJsonNode(string))
												.collect(Collectors.toList());
		for(JsonNode searchNode : allUserSearches) {
			        ObjectNode object = (ObjectNode) searchNode;
			        object.remove("searchResult");
		}
		
		List<JsonNode> pageFilteredResults = filterJsonNodeResultsPagination(allUserSearches, offset, limit);
		
		GenericResultDto<JsonNode> result = createResultDto(pageFilteredResults, offset, limit);
		
		return Mono.just(result);
	}
	
	public Mono<GenericResultDto<JsonNode>> getSearchResults(String search_uuid, String user_uuid, int offset, int limit) {
		GenericResultDto<JsonNode> result = null;
		if (!userSearchesRepo.findById(search_uuid).isPresent()) {
			result = createResultDto(null, offset, limit);
			log.info("No Search found with UUID="+search_uuid);
		} else if (!userSearchesRepo.findById(search_uuid).get().getUserUuid().equals(user_uuid)) {
			result = createResultDto(null, offset, limit);
			log.info("User with UUID="+user_uuid+"does not have a search with UUID="+search_uuid);
		}else {
			Search search = userSearchesRepo.findById(search_uuid).get();

			JsonNode searchResult = search.getSearchResult();

			List<JsonNode> pageFilteredResults = filterJsonNodeResultsPagination(mapJsonNodeToList(searchResult), offset, limit);
			
			result = createResultDto(pageFilteredResults, offset, limit);
		}
		return Mono.just(result);
	}
	
	private JsonNode[] mapListToJsonNodeArray(List<JsonNode> pageFilteredResults) {
		JsonNode[] resultsForDto = {};
		if (pageFilteredResults.size() > 0) {
			resultsForDto = new JsonNode[pageFilteredResults.size()];
			pageFilteredResults.toArray(resultsForDto);
		}
		return resultsForDto;
	}
	
	private List<JsonNode> mapJsonNodeToList(JsonNode searchResult){
		List<JsonNode> allResults = new ArrayList<JsonNode>();
		for (int i = 0; i < searchResult.size(); i++) {
			allResults.add(searchResult.get(i));
		}
		return allResults;
	}
	
	private GenericResultDto<JsonNode> createResultDto(List<JsonNode> pageFilteredResults,int offset, int limit){
		GenericResultDto<JsonNode> result = new GenericResultDto<JsonNode>();
		result.setOffset(offset);
		result.setLimit(limit);
		if(pageFilteredResults!=null) {
			result.setCount(pageFilteredResults.size());
			result.setResults(mapListToJsonNodeArray(pageFilteredResults));
		}else {
			result.setCount(0);
			JsonNode[] resultsForDto = new JsonNode[1];
			JsonNode results = JsonHelper.deserializeToJsonNode("{\"NOT FOUND\":\"The required search does not exist\"}");
			resultsForDto[0]=results;
			
			result.setResults(resultsForDto);
		}
		
		return result;
	}
	
	// code modified from OpenData Json Helper
	private List<JsonNode> filterJsonNodeResultsPagination(List<JsonNode> allResults, int offset, int limit){
		
		List<JsonNode> toReturn = null;
		
		if(offset > allResults.size()-1)
			toReturn = allResults.stream().skip(offset).limit(limit).collect(Collectors.toList());
		//if limit == -1 it means that we should get all Data
		else if(limit == -1)
			toReturn = allResults.stream().skip(offset).limit(allResults.size()).collect(Collectors.toList());
		else {
			// If the ending point is out of bounce, we set the ending point in the last point of the array +1
			int end = offset + limit; // the ending index +1
			if (end > allResults.size())
				end = allResults.size();

			// Makes the subarray. The end point is excluded thats why we do +1.
			toReturn = allResults.stream().skip(offset).limit(end).collect(Collectors.toList());
		}
		return toReturn;
	}
}
