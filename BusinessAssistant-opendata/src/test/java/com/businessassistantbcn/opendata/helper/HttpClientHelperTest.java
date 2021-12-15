package com.businessassistantbcn.opendata.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.Test;

import com.businessassistantbcn.opendata.config.PropertiesConfig;

public class HttpClientHelperTest {
	
	HttpClientHelper httpClientHelper = new HttpClientHelper(new PropertiesConfig());

	@Test
	public void getStringRootTest() throws MalformedURLException {
		String url = "https://jsonplaceholder.typicode.com/todos/1";
		String expected = "{\"userId\": 1,\"id\": 1, \"title\": \"delectus aut autem\",\"completed\": false\"}";
		String result = httpClientHelper.getStringRoot(new URL(url));
		assertEquals(expected, result, "The 'getStringRoot' method should return a stringJson");
	}

	@Test
	public void getJsonRootTest() {
	}

	@Test
	public void getObjectRootTest() {

	}
}
