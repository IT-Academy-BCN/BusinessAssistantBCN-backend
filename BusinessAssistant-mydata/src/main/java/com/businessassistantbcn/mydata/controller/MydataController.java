package com.businessassistantbcn.mydata.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.util.Map;

import javax.naming.ServiceUnavailableException;
import javax.validation.Valid;

import org.apache.commons.lang3.math.NumberUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.businessassistantbcn.mydata.dto.GenericSearchesResultDto;
import com.businessassistantbcn.mydata.dto.SaveSearchRequestDto;
import com.businessassistantbcn.mydata.dto.SaveSearchResponseDto;
import com.businessassistantbcn.mydata.dto.SearchResultsDto;
import com.businessassistantbcn.mydata.service.UserSearchesService;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/businessassistantbcn/api/v1/mydata")
public class MydataController {

	@Autowired
	UserSearchesService userService;

//	@Autowired
//	private DiscoveryClient discoveryClient;

//	@Autowired
//	private HttpProxy httpProxy;

	public MydataController(UserSearchesService userService) {
		this.userService = userService;
	}

	private final boolean PAGINATION_ENABLED = true;

	@GetMapping(value = "/test")
	@Operation(summary = "Get test")
	@ApiResponse(responseCode = "200", description = "OK")
	//public Mono<String> test() throws ServiceUnavailableException, MalformedURLException {
	public String test() throws ServiceUnavailableException, MalformedURLException {

//		//All services available
///*		StringBuffer sb = new StringBuffer();
//		discoveryClient.getServices().forEach( (s) -> {
//			sb.append(s + "\r\n");
//		});*/
//		Optional<URI> uri = discoveryClient.getInstances("businessassistant-usermanagement")
//				.stream()
//				.findAny()
//				.map( s -> s.getUri());
//
//		return httpProxy.getRequestData(uri
//						.map( s -> s.resolve("/businessassistantbcn/api/v1/usermanagement/test"))
//						.get().toURL(), String.class)
//						.flatMap( s -> Mono.just(s));


//        return userService.getTest();

		return "Hello from BusinessAssistant MyData!!!";
	}

	@PostMapping(value="/mysearches/{user_uuid}")
	@Operation(summary = "Save user search")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not Found"),
			@ApiResponse(responseCode = "503", description = "Service Unavailable") })
	public Mono<SaveSearchResponseDto> saveSearch(@PathVariable(value="user_uuid") String user_uuid, @Valid @RequestBody SaveSearchRequestDto searchToSave) {
		return userService.saveSearch(searchToSave, user_uuid);
	}

	@GetMapping("/mysearches/{user_uuid}")
	@Operation(summary = "Get searches  SET 0 LIMIT 0")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Not Found"),
			@ApiResponse(responseCode = "503", description = "Service Unavailable") })
	public Mono<GenericSearchesResultDto<JsonNode>> getAllSearchesByUser(
			@RequestParam(required = false) String offset,
			@RequestParam(required = false) String limit,
			@PathVariable("user_uuid") String user_uuid,
			@RequestParam Map<String, String> map) {
		this.validateRequestParameters(map, this.PAGINATION_ENABLED);

		return userService.getAllUserSearches(user_uuid, getValidOffset(offset), getValidLimit(limit));
	}

	private void validateRequestParameters(Map<String, String> map, boolean paginationEnabled) {
		if (!paginationEnabled && !map.keySet().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		for (String key : map.keySet()) {
			if (!key.equals("offset") && !key.equals("limit")) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}
		}
	}

	private int getValidOffset(String offset) {
		if (offset == null || offset.isEmpty()) {
			return 0;
		}
		// NumberUtils.isDigits returns false for negative numbers
		if (!NumberUtils.isDigits(offset)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return Integer.parseInt(offset);
	}

	private int getValidLimit(String limit) {
		if (limit == null || limit.isEmpty() || limit.equals("-1")) {
			return -1;
		}
		// NumberUtils.isDigits returns false for negative numbers
		if (!NumberUtils.isDigits(limit)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return Integer.parseInt(limit);
	}

	@GetMapping("/mysearches/{user_uuid}/search/{search_uuid}")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Not Found"),
			@ApiResponse(responseCode = "503", description = "Service Unavailable"), })
	public Mono<SearchResultsDto> getOneSearchResults(
			@PathVariable("user_uuid") String user_uuid,
			@PathVariable("search_uuid") String search_uuid) {

		return userService.getSearchResults(search_uuid, user_uuid);
	}

	@DeleteMapping("/mysearches/{user_uuid}/search/{search_uuid}")
	@ApiResponses({@ApiResponse(responseCode = "204", description = "No Content"),
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Not Found"),
			@ApiResponse(responseCode = "503", description = "Service Unavailable"),})
	public Mono<ResponseEntity<Void>> deleteSearchFromUser(
			@PathVariable("user_uuid") String user_uuid,
			@PathVariable("search_uuid") String search_uuid) {

		Mono<Void> searchDeleted = userService.deleteUserSearchBySearchUuid(user_uuid, search_uuid);
		return searchDeleted.then(Mono.just(ResponseEntity.noContent().build()));
	}


}
