package com.businessassistantbcn.gencat.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
public class JsonHelper<T> {

	private static final Logger log = LoggerFactory.getLogger(JsonHelper.class);
	private static ObjectMapper mapper;	

	static {
		mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	//¿static? see https://www.baeldung.com/java-static-class-vs-singleton

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

}
