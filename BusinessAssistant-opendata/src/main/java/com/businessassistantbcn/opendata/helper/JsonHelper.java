package com.businessassistantbcn.opendata.helper;

import java.util.Arrays;
import java.util.List;

import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Component;

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
			System.out.println("No se puede convertir de String a una Lista Generica porque: " + e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	public static JsonNode deserializeToJsonNode(String json){
		JsonNode jsonNode = null;
		try {
			jsonNode = mapper.readTree(json);
		} catch (JsonProcessingException e) {
			System.out.println("No se puede convertir de String to JsonNode porque: " + e.getMessage());
			e.printStackTrace();
		}
		return jsonNode;
	}

	public static String serialize(JsonNode node) {
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(node);
		} catch (JsonProcessingException e) {
			System.out.println("No se puede convertir de JsonNode to String porque: " + e.getMessage());
			e.printStackTrace();
		}
		return jsonString;
	}

	public static  <T> T[] filterDto(T[] dto, int offset, int limit) throws Exception{
		int start = offset + 1;
		if(start > dto.length-1){
			throw new Exception("Result not found");
		}
		int end = start + limit;
		if(end > dto.length-1){
			end = dto.length - 1;
		}
		T[] filteredDto = Arrays.copyOfRange(dto,start,end+1);

		return filteredDto;
	}
}
