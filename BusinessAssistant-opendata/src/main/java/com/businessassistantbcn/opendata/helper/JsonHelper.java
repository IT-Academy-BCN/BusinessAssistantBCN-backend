package com.businessassistantbcn.opendata.helper;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class JsonHelper<T> {

	private static final Logger log = LoggerFactory.getLogger(JsonHelper.class);
	private static ObjectMapper mapper;	

	static {
		mapper = new ObjectMapper();
	}

	//¿static? see https://www.baeldung.com/java-static-class-vs-singleton
	public static <T> List<T> deserializeToList(String json){
		List<T> list = null;
		try {
			list = (List<T>) mapper.readTree(json);
		} catch (JsonProcessingException e) {
			log.error("No se puede convertir de String a una Lista Generica porque: {}", e.getMessage());
			log.error("exception:", e);
		}
		return list;
	}

	public static JsonNode deserializeToJsonNode(String json){
		JsonNode jsonNode = null;
		try {
			jsonNode = mapper.readTree(json);
		} catch (JsonProcessingException e) {
			log.error("No se puede convertir de String to JsonNode porque: {}", e.getMessage());
			log.error("exception:", e);
		}
		return jsonNode;
	}

	public static String serialize(JsonNode node) {
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(node);
		} catch (JsonProcessingException e) {
			log.error("No se puede convertir de JsonNode to String porque: {}", e.getMessage());
			log.error("exception:", e);
		}
		return jsonString;
	}
	//Returns sub array starting from offset. if limit it's -1 it means there's no limit
	public static  <T> T[] filterDto(T[] dto, int offset, int limit){
		if(offset > dto.length-1){
			return Arrays.copyOfRange(dto,0,0);
		}
		//if limit == -1 it means that we should get all Data
		if(limit == -1){
			return Arrays.copyOfRange(dto,offset,dto.length);
		}
		//the ending index +1
		int end = offset + limit;
		//If the ending point is out of bounce, we set the ending point in the last point of the array +1
		if(end > dto.length){
			end = dto.length;
		}
		//Makes the subarray. The end point is excluded thats why we do +1.
		return Arrays.copyOfRange(dto,offset,end);
	}
	
	// Returns stream starting from 'offset', with 'limit' elements.
	// Returns all elements if limit < 0.
	public static <T> T[] pageDto(T[] dto, int offset, int limit) {
		if(offset > dto.length)
			offset = dto.length;
		else if(offset < 0)
			offset = 0;
		
		int end = limit < 0 ? dto.length : offset + limit;
		if(end > dto.length)
			end = dto.length;
		
		return Arrays.copyOfRange(dto, offset, end);
	}
	
}
