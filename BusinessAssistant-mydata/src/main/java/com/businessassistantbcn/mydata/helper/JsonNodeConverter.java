package com.businessassistantbcn.mydata.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;

/*
 * 
 * CODE FROM:
 * https://blog.gordonturner.com/2020/05/07/jpa-jsonnode-mysql-json-converter/
 * 
 */


public class JsonNodeConverter implements AttributeConverter<JsonNode, String>
{
  private static final Logger logger = LoggerFactory.getLogger( JsonNodeConverter.class );

  /**
   * @param jsonNode
   * @return
   */
  @Override
  public String convertToDatabaseColumn(JsonNode jsonNode)
  {
    if( jsonNode == null)
    {
      logger.warn( "jsonNode input is null, returning null" );
      return null;
    }

    String jsonNodeString = jsonNode.toPrettyString();
    return jsonNodeString;
  }

  /**
   * @param jsonNodeString
   * @return
   */
  @Override
  public JsonNode convertToEntityAttribute(String jsonNodeString) {

    if ( StringUtils.isEmpty(jsonNodeString) )
    {
      logger.warn( "jsonNodeString input is empty, returning null" );
      return null;
    }

    ObjectMapper mapper = new ObjectMapper();
    try
    {
      return mapper.readTree( jsonNodeString );
    }
    catch( JsonProcessingException e )
    {
      logger.error( "Error parsing jsonNodeString", e );
    }
    return null;
  }

}