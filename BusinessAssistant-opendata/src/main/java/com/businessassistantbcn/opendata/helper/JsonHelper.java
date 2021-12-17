package com.businessassistantbcn.opendata.helper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import ch.qos.logback.classic.Logger;
import net.minidev.json.writer.ArraysMapper.GenericMapper;

@Component
public class JsonHelper {

	private static ObjectMapper mapper;

	static {
		mapper = new ObjectMapper();
	}

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

}
