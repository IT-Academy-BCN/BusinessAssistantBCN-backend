package com.businessassistantbcn.mydata.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.businessassistantbcn.mydata.entities.Search;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MydataService {
	public Search saveSearch(String payload, String user_uuid) {
		ObjectMapper objectMapper = new ObjectMapper();
		
		Search search = new Search();
		try {
			search = objectMapper.readValue(payload, Search.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		search.setUser_uuid(user_uuid);
		search.setSearch_date(new Date());
		
		return search;
		
	}
}
