package com.businessassistantbcn.mydata.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.businessassistantbcn.mydata.dto.GenericResultDto;
import com.businessassistantbcn.mydata.dto.SavedSearchResponseDto;
import com.businessassistantbcn.mydata.entities.Search;
import com.businessassistantbcn.mydata.helper.DtoHelper;
import com.businessassistantbcn.mydata.helper.JsonHelper;
import com.businessassistantbcn.mydata.repository.MySearchesRepository;
import com.fasterxml.jackson.databind.JsonNode;

import reactor.core.publisher.Mono;

@Service
public class UserService {
	
	MySearchesRepository mySearchesRepo;
	
	@Autowired
	public UserService(MySearchesRepository mySearchesRepo) {
		this.mySearchesRepo = mySearchesRepo;
	}
	
	public SavedSearchResponseDto saveSearch(String jsonSearch, String user_uuid) {
		Search search = JsonHelper.jsonToEntity(jsonSearch);
		search.setUserUuid(user_uuid);
		search.setSearchDate(new Date());
	
		Search savedSearch = mySearchesRepo.save(search);
		
		return DtoHelper.mapSearchToSearchResponseDto(savedSearch);
	}

	/* 
	 * Get all searches
	 * No support for pagination (not required?)
	 */
	public Mono<GenericResultDto<Search>> getAllSearches(String user_uuid) {
		// find all searches
		List<Search> resultsList = mySearchesRepo.findByUserUuid(user_uuid);
		
		// array to save in GenericResultDto
		Search[] resultsArray = {};
		
		// convert list to array
		if(resultsList.size() > 0) {
			resultsArray = new Search[resultsList.size()];
			resultsList.toArray(resultsArray);
		}
		
		// Create Response Dto and set it properties
		GenericResultDto<Search> result = new GenericResultDto<Search>();
		result.setCount(resultsArray.length);
		result.setOffset(0); result.setLimit(0);
		result.setResults(resultsArray);
		
		// return the response
		return Mono.just(result);
	}
	
	/*
	 * Get results of a search
	 * Support for pagination
	 */
	public Mono<GenericResultDto<JsonNode>> getSearchResults(String search_uuid, String user_uuid, int offset, int limit) {
		// Find a Search by id;
		Optional<Search> search = mySearchesRepo.findById(search_uuid);
		
		// If the result is empty returns null
		if(search.isEmpty()) {
			return null;
		}
		
		// Gets the result
		Search isSearch = search.get();
		
		// JsonNode object where I'm gonna save the results inside of the search
		JsonNode searchResult = null;
		
		// Save the results of the search
		searchResult = isSearch.getSearchResult();
		
		// Get the value of the property results
		JsonNode results = searchResult.findValue("results");
		
		// Converts results into a List of JsonNode
		List<JsonNode> resultsArray = new ArrayList<JsonNode>();
		
		for(int i = 0; i < results.size(); i++) {
			resultsArray.add(results.get(i));
		}
		
		// Filter JSON data if there's pagination
		List<JsonNode> realResults = filterJsonNode(resultsArray, offset, limit);
		
		// Generate final results
		GenericResultDto<JsonNode> finalResult = new GenericResultDto<JsonNode>();
		
		// Convert JsonNode list to Array in order to put it in the dto
		JsonNode[] lastArray = {};
		
		if(realResults.size() > 0) {
			lastArray = new JsonNode[realResults.size()];
			realResults.toArray(lastArray);
		}
		
		// fill dto
		finalResult.setCount(realResults.size());
		finalResult.setOffset(offset);
		finalResult.setLimit(limit);
		finalResult.setResults(lastArray);
		
		return Mono.just(finalResult);
	}
	
	// code modified from OpenData Json Helper
	public List<JsonNode> filterJsonNode(List<JsonNode> array, int offset, int limit){
		if(offset > array.size()-1){
			List<JsonNode> toReturn = 
					array.stream()
					.skip(offset).limit(limit).collect(Collectors.toList());
			return toReturn;
		}
		//if limit == -1 it means that we should get all Data
		if(limit == -1){
			List<JsonNode> toReturn = 
					array.stream()
					.skip(offset).limit(array.size()).collect(Collectors.toList());	
			return toReturn;
		}
		//the ending index +1
		int end = offset + limit;
		//If the ending point is out of bounce, we set the ending point in the last point of the array +1
		if(end > array.size()){
			end = array.size();
		}
		//Makes the subarray. The end point is excluded thats why we do +1.
		List<JsonNode> toReturn = 
				array.stream()
				.skip(offset).limit(end).collect(Collectors.toList());		
		return toReturn;
	}
}
