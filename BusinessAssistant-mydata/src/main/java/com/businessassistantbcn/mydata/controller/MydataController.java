package com.businessassistantbcn.mydata.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import reactor.core.publisher.Mono;

import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.math.NumberUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

	public MydataController(UserSearchesService userService) {
		this.userService = userService;
	}

	private final boolean PAGINATION_ENABLED = true;

	@GetMapping(value = "/test")
	@Operation(summary = "Get test")
	@ApiResponse(responseCode = "200", description = "OK")
	public String test() {
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

	
}
