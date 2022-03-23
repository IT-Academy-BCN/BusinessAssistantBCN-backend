package com.businessassistantbcn.mydata.helper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.businessassistantbcn.mydata.entities.Search;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Converter
public class JsonHelper implements AttributeConverter<JsonNode, String> {
	
	private static final Logger log = LoggerFactory.getLogger(JsonHelper.class);
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	 @Override
	  public String convertToDatabaseColumn(JsonNode jsonNode)
	  {
	    if( jsonNode == null)
	    {
	      log.warn( "jsonNode input is null, returning null" );
	      return null;
	    }

	    String jsonNodeString = jsonNode.toPrettyString();
	    return jsonNodeString;
	  }
	 
	 @Override
	  public JsonNode convertToEntityAttribute(String jsonNodeString) {

	    if ( StringUtils.isEmpty(jsonNodeString) )
	    {
	      log.warn( "jsonNodeString input is empty, returning null" );
	      return null;
	    }

	    ObjectMapper mapper = new ObjectMapper();
	    try
	    {
	      return mapper.readTree( jsonNodeString );
	    }
	    catch( JsonProcessingException e )
	    {
	      log.error( "Error parsing jsonNodeString", e );
	    }
	    return null;
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
