package com.businessassistantbcn.mydata.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.businessassistantbcn.mydata.entities.Search;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonHelper {
	
	private static final Logger log = LoggerFactory.getLogger(JsonHelper.class);
	
	private static ObjectMapper objectMapper = new ObjectMapper();

	public static Search jsonToEntity(String jsonSearch) {
		Search search = new Search();
		try {
			search = objectMapper.readValue(jsonSearch, Search.class);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
		
		return search;
	}
	
	public static String entityToJsonString(Search search) {
		String jsonSearch = new String();
		try {
			jsonSearch = objectMapper.writeValueAsString(search);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
		return jsonSearch;
	}
	
	public static JsonNode deserializeToJsonNode(String json){
		JsonNode jsonNode = null;
		try {
			jsonNode = objectMapper.readTree(json);
		} catch (JsonProcessingException e) {
			log.error("Unable to deserialize to jsonNode:", e.getMessage());
		}
		
		return jsonNode;
	}
	
	public static String serialize(JsonNode node) {
		String jsonString = null;
		try {
			jsonString = objectMapper.writeValueAsString(node);
		} catch (JsonProcessingException e) {
			log.error("Unable to serialize jsonNode", e.getMessage());
		}
		
		return jsonString;
	}
}
