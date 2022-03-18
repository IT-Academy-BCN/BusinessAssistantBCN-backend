package com.businessassistantbcn.mydata.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.businessassistantbcn.mydata.dto.GenericResultDto;
import com.businessassistantbcn.mydata.dto.SaveSearchRequestDto;
import com.businessassistantbcn.mydata.dto.SaveSearchResponseDto;
import com.businessassistantbcn.mydata.entities.Search;
import com.businessassistantbcn.mydata.helper.DtoHelper;
import com.businessassistantbcn.mydata.helper.JsonHelper;
import com.businessassistantbcn.mydata.repository.MySearchesRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import reactor.core.publisher.Mono;

@Service
public class UserService {
	
	MySearchesRepository mySearchesRepo;
	
	@Autowired
	public UserService(MySearchesRepository mySearchesRepo) {
		this.mySearchesRepo = mySearchesRepo;
	}
	
//TODO
	//No guarda search en el repo ==> es por algo relacionado con el searchUuid que se supone que se genera al guardar, pero da error
	//Más abajo están las versiones usando string en el body del post. Ocurre lo mismo, la segunda opción el el código cleanded de la 3ª que es la que alguien hizo al principio
	//No funciona ninguna
	public Mono<SaveSearchResponseDto> saveSearch(SaveSearchRequestDto searchToSave, String user_uuid) {
		Search search = new Search();
		search = DtoHelper.mapSaveSearchRequestDtoToSearch(searchToSave, user_uuid);
		
		Search savedSearch = mySearchesRepo.save(search);
		
		return Mono.just(DtoHelper.mapSearchToSaveSearchResponseDto(savedSearch));
	}

//2ª VERSIÓN	
//	public Mono<SaveSearchResponseDto> saveSearch(String searchToSave, String user_uuid) {
//		Search search = JsonHelper.jsonToEntity(searchToSave);
//		search.setUserUuid(user_uuid);
//		search.setSearchDate(new Date());
//		
//		Search savedSearch = mySearchesRepo.save(search);
//		
//		return Mono.just(DtoHelper.mapSearchToSaveSearchResponseDto(savedSearch));
//	}

//3ª VERSIÓN
//	public Mono<SaveSearchResponseDto> saveSearch(String payload, String user_uuid) {
//
//		// Uses ObjectMapper to read the JSON payload.
//		ObjectMapper objectMapper = new ObjectMapper();
//
//		// Creates a Search entity
//		Search search = new Search();
//
//		try {
//			// Read payload value and map it to Search
//			search = objectMapper.readValue(payload, Search.class);
//		} catch (JsonMappingException e) {
//			// Needs to be checked (throw to controller?)
//			e.printStackTrace();
//		} catch (JsonProcessingException e) {
//			// Needs to be checked (throw to controller?)
//			e.printStackTrace();
//		}
//
//		// set user_uuid in Search.
//		search.setUserUuid(user_uuid);
//
//		// set today's date in Search
//		search.setSearchDate(new Date());
//
//		// save the entity
//		Search saved = mySearchesRepo.save(search);
//
//		return Mono.just(DtoHelper.mapSearchToSaveSearchResponseDto(saved));
//	}

//******************************************************
	
	public Mono<GenericResultDto<JsonNode>> getAllSearches(String user_uuid, int offset, int limit) {
		List<JsonNode> allUserSearches = mySearchesRepo.findByUserUuid(user_uuid).stream()
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
		if (!mySearchesRepo.findById(search_uuid).isPresent())
			throw new NoSuchElementException("No existe ninguna búsqueda con ese id");
		else if (!mySearchesRepo.findById(search_uuid).get().getUserUuid().equals(user_uuid))
			throw new NoSuchElementException("Este usuario no tiene ninguna búsqueda con ese id");
		else {
			Search search = mySearchesRepo.findById(search_uuid).get();

			JsonNode searchResult = search.getSearchResult();

			List<JsonNode> pageFilteredResults = filterJsonNodeResultsPagination(mapJsonNodeToList(searchResult), offset, limit);
			
			GenericResultDto<JsonNode> result = createResultDto(pageFilteredResults, offset, limit);

			return Mono.just(result);
		}
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
		result.setCount(pageFilteredResults.size());
		result.setOffset(offset);
		result.setLimit(limit);
		result.setResults(mapListToJsonNodeArray(pageFilteredResults));
		
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
