package com.businessassistantbcn.mydata.service;

import java.util.*;
import java.util.stream.Collectors;

import com.businessassistantbcn.mydata.config.PropertiesConfig;
import com.businessassistantbcn.mydata.handleError.ErrorDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
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

import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.function.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class UserSearchesService {
	private static final Logger log = LoggerFactory.getLogger(UserSearchesService.class);
	
	@Autowired
	IUserSearchesRepository userSearchesRepo;

	@Autowired
	PropertiesConfig propertiesConfig;

//    @Autowired
//    MyDataRepository myDataRepo;

	public UserSearchesService() {
	}


	public UserSearchesService(IUserSearchesRepository userSearchesRepo, PropertiesConfig propertiesConfig) {
		this.propertiesConfig = propertiesConfig;
		this.userSearchesRepo = userSearchesRepo;
	}

	public Mono<SaveSearchResponseDto> saveSearch(SaveSearchRequestDto searchToSave, String user_uuid) {

		UserSearch search = DtoHelper.mapSaveSearchRequestDtoToSearch(searchToSave, user_uuid);

		boolean isLimitExceded = false;

		if (propertiesConfig.getIsLimitEnabled()){
			isLimitExceded = checkLimitUserSearches(user_uuid);
		}

		if(!isLimitExceded){

			UserSearch savedSearch = userSearchesRepo.save(search);
			return Mono.just(DtoHelper.mapSearchToSaveSearchResponseDto(savedSearch));

		} else {
			Mono<?> monoErrorResponse = Mono.just(new ErrorDetailsResponse("User " + user_uuid + " " + propertiesConfig.getErrorMessage()
					,HttpStatus.OK
					,new Date()));
			return (Mono<SaveSearchResponseDto>) monoErrorResponse;
		}
	}
	
	public Mono<GenericSearchesResultDto<JsonNode>> getAllUserSearches(String user_uuid, int offset, int limit) {

		List<JsonNode> allUserSearches = getAllSearchesFromUserUuid(user_uuid);
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

	private List<JsonNode> getAllSearchesFromUserUuid(String user_uuid){
		return userSearchesRepo.findByUserUuid(user_uuid).stream()
				.map(search -> JsonHelper.entityToJsonString(search))
				.map(string -> JsonHelper.deserializeStringToJsonNode(string))
				.collect(Collectors.toList());
	}

	public boolean checkLimitUserSearches(String user_uuid) {

		List<UserSearch> allUserSearches = userSearchesRepo.findByUserUuid(user_uuid);

		boolean excededLimit = false;

		if (allUserSearches.isEmpty()) {
			log.info("User with UUID="+user_uuid+" does not have any searches");
			return excededLimit;
		}

		int totalUserSearches = allUserSearches.size();
		int limit = propertiesConfig.getLimitValue();

		if (totalUserSearches >= limit) {

			Map<String,Object> details = new HashMap<>();
			details.put("Message", propertiesConfig.getErrorMessage());
			details.put("Limit", limit);
			details.put("User", user_uuid);
			details.put("Timestamp", new Date());
			details.forEach((k,v) -> log.info(k +": " + v));

			excededLimit = true;
		}
		return excededLimit;
	}

	public Mono<Void> deleteUserSearchBySearchUuid(String user_uuid, String search_uuid) {

		return Mono.just(search_uuid)
			.filter(Objects::nonNull)
			.flatMap(userSearchExists -> {

				if ((!userSearchesRepo.existsBySearchUuid(search_uuid)) || (!userSearchesRepo.existsByUserUuid(user_uuid))) {
					log.info("Search with UUID= " + search_uuid + " or User with UUID= " + user_uuid + " does not exist");
					return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
							"Search with UUID= " + search_uuid + " or User with UUID= " + user_uuid + " does not exist"));
				}
				return Mono.just(userSearchExists);
			})
			.flatMap(searchToDelete -> {

				UserSearch userSearchToDelete = userSearchesRepo.findOneBySearchUuid(searchToDelete);

				if (!userSearchToDelete.getUserUuid().equals(user_uuid)) {
					log.info("User with UUID= " + user_uuid + " does not have a search with UUID= " + search_uuid);
					return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
							"User with UUID= " + user_uuid + " does not have a search with UUID= " + search_uuid));
				}
				userSearchesRepo.deleteById(userSearchToDelete.getSearchUuid());
				log.info("Search with UUID= " + search_uuid + " has been deleted");
				return Mono.empty();
			});
	}
}
