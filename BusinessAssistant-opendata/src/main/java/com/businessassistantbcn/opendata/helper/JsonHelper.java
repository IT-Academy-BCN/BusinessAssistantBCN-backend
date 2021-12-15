package com.businessassistantbcn.opendata.helper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.Logger;
import net.minidev.json.writer.ArraysMapper.GenericMapper;

@Component
public class JsonHelper {

	private static ObjectMapper mapper = new ObjectMapper();
		
    public <T> List<T> deserializeToList(String json){
        return null;
    }

    public JsonNode deserializeToJsonNode(String json){
        return null;
    }

    public String serialize(JsonNode node) {
    	String jsonString = null;
    		try {
				jsonString = mapper.writeValueAsString(node);
			} catch (JsonProcessingException e) {
				System.out.println("El error es " + e.getMessage());
				e.printStackTrace();
			}		
    	return jsonString;
    }
 
}
