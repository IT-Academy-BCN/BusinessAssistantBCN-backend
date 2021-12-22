package com.businessassistantbcn.opendata.helper;

import java.util.Arrays;
import java.util.List;

import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Component;

import com.businessassistantbcn.opendata.config.LoggerConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class JsonHelper<T> {

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
			LoggerConfig.log.error("No se puede convertir de String a una Lista Generica porque: {}", e.getMessage());
			LoggerConfig.logStackTrace(e);
		}
		return list;
	}

	public static JsonNode deserializeToJsonNode(String json){
		JsonNode jsonNode = null;
		try {
			jsonNode = mapper.readTree(json);
		} catch (JsonProcessingException e) {
			LoggerConfig.log.error("No se puede convertir de String to JsonNode porque: {}", e.getMessage());
			LoggerConfig.logStackTrace(e);
		}
		return jsonNode;
	}

	public static String serialize(JsonNode node) {
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(node);
		} catch (JsonProcessingException e) {
			LoggerConfig.log.error("No se puede convertir de JsonNode to String porque: {}", e.getMessage());
			LoggerConfig.logStackTrace(e);
		}
		return jsonString;
	}
	//Returns sub array starting from offset. if limit it's -1 it means there's no limit
	public static  <T> T[] filterDto(T[] dto, int offset, int limit) throws Exception{

		if(offset > dto.length-1){
			throw new Exception("Result not found");
		}
		if(offset < 0){
			throw new Exception("Offset Can not be negative");
		}
		int end = offset + limit;
		//if limit == -1 it means that we should get all Data
		if(limit == -1){
			end = dto.length-1;
		}
		if(limit < -1){
			throw new Exception("Limit value not accepted");
		}
		//If the ending point is out of bounce, we set the ending point in the last point of the array
		if(end > dto.length-1){
			end = dto.length - 1;
		}
		//Makes the subarray. The end point is excluded thats why we do +1.
		T[] filteredDto = Arrays.copyOfRange(dto,offset,end+1);

		return filteredDto;
	}
	
}
