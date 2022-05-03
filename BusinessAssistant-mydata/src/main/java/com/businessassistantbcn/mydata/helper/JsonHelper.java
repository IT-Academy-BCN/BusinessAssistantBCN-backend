package com.businessassistantbcn.mydata.helper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Component
@Converter
public class JsonHelper implements AttributeConverter<JsonNode, String> {

	private static ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public String convertToDatabaseColumn(JsonNode jsonNode) {
		if (jsonNode == null) {
			log.warn("jsonNode input is null, returning null");
			return null;
		}
		return jsonNode.toPrettyString();
	}

	@Override
	public JsonNode convertToEntityAttribute(String jsonNodeString) {
		JsonNode jsonNode = null;
		if (jsonNodeString.isEmpty())
			log.warn("jsonNodeString input is empty, returning null");
		else {
			try {
				jsonNode = objectMapper.readTree(jsonNodeString);
			} catch (JsonProcessingException e) {
				log.error("Error parsing jsonNodeString", e);
			}
		}
		return jsonNode;
	}

	public static<T> String entityToJsonString(T entity) {
		String jsonEntity = new String();
		try {
			jsonEntity = objectMapper.writeValueAsString(entity);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
		return jsonEntity;
	}
	
	public static JsonNode deserializeStringToJsonNode(String json){
		JsonNode jsonNode = null;
		try {
			jsonNode = objectMapper.readTree(json);
		} catch (JsonProcessingException e) {
			log.error("Unable to deserialize to jsonNode:", e.getMessage());
		}
		return jsonNode;
	}
	
	public static String serializeJsonNodeToString(JsonNode node) {
		String jsonString = null;
		try {
			jsonString = objectMapper.writeValueAsString(node);
		} catch (JsonProcessingException e) {
			log.error("Unable to serialize jsonNode", e.getMessage());
		}
		return jsonString;
	}
}
