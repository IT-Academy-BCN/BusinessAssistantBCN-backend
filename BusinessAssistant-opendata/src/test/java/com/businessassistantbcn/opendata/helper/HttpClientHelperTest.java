package com.businessassistantbcn.opendata.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class HttpClientHelperTest {

	@Autowired
	private Environment env;

	@Autowired
	private HttpClientHelper httpClientHelper;

	@Test
	@DisplayName(value = "Testing 'getStringRoot' method")
	public void getStringRootTest() throws MalformedURLException {

		// URL for testing
		String url = env.getProperty("ds_test");

		// Expected jsonString
		String expected = "{\"next\":\"https://swapi.py4e.com/api/vehicles/?page=2\",\"previous\":null,\"count\":39,\"results\":[{\"max_atmosphering_speed\":\"30\",\"cargo_capacity\":\"50000\",\"films\":[\"https://swapi.py4e.com/api/films/1/\",\"https://swapi.py4e.com/api/films/5/\"],\"passengers\":\"30\",\"pilots\":[],\"edited\":\"2014-12-20T21:30:21.661000Z\",\"consumables\":\"2 months\",\"created\":\"2014-12-10T15:36:25.724000Z\",\"length\":\"36.8 \",\"url\":\"https://swapi.py4e.com/api/vehicles/4/\",\"manufacturer\":\"Corellia Mining Corporation\",\"crew\":\"46\",\"vehicle_class\":\"wheeled\",\"cost_in_credits\":\"150000\",\"name\":\"Sand Crawler\",\"model\":\"Digger Crawler\"},{\"max_atmosphering_speed\":\"1200\",\"cargo_capacity\":\"50\",\"films\":[\"https://swapi.py4e.com/api/films/1/\"],\"passengers\":\"1\",\"pilots\":[],\"edited\":\"2014-12-20T21:30:21.665000Z\",\"consumables\":\"0\",\"created\":\"2014-12-10T16:01:52.434000Z\",\"length\":\"10.4 \",\"url\":\"https://swapi.py4e.com/api/vehicles/6/\",\"manufacturer\":\"Incom Corporation\",\"crew\":\"1\",\"vehicle_class\":\"repulsorcraft\",\"cost_in_credits\":\"14500\",\"name\":\"T-16 skyhopper\",\"model\":\"T-16 skyhopper\"},{\"max_atmosphering_speed\":\"250\",\"cargo_capacity\":\"5\",\"films\":[\"https://swapi.py4e.com/api/films/1/\"],\"passengers\":\"1\",\"pilots\":[],\"edited\":\"2014-12-20T21:30:21.668000Z\",\"consumables\":\"unknown\",\"created\":\"2014-12-10T16:13:52.586000Z\",\"length\":\"3.4 \",\"url\":\"https://swapi.py4e.com/api/vehicles/7/\",\"manufacturer\":\"SoroSuub Corporation\",\"crew\":\"1\",\"vehicle_class\":\"repulsorcraft\",\"cost_in_credits\":\"10550\",\"name\":\"X-34 landspeeder\",\"model\":\"X-34 landspeeder\"},{\"max_atmosphering_speed\":\"1200\",\"cargo_capacity\":\"65\",\"films\":[\"https://swapi.py4e.com/api/films/1/\",\"https://swapi.py4e.com/api/films/2/\",\"https://swapi.py4e.com/api/films/3/\"],\"passengers\":\"0\",\"pilots\":[],\"edited\":\"2014-12-20T21:30:21.670000Z\",\"consumables\":\"2 days\",\"created\":\"2014-12-10T16:33:52.860000Z\",\"length\":\"6.4\",\"url\":\"https://swapi.py4e.com/api/vehicles/8/\",\"manufacturer\":\"Sienar Fleet Systems\",\"crew\":\"1\",\"vehicle_class\":\"starfighter\",\"cost_in_credits\":\"unknown\",\"name\":\"TIE/LN starfighter\",\"model\":\"Twin Ion Engine/Ln Starfighter\"},{\"max_atmosphering_speed\":\"650\",\"cargo_capacity\":\"10\",\"films\":[\"https://swapi.py4e.com/api/films/2/\"],\"passengers\":\"0\",\"pilots\":[\"https://swapi.py4e.com/api/people/1/\",\"https://swapi.py4e.com/api/people/18/\"],\"edited\":\"2014-12-20T21:30:21.672000Z\",\"consumables\":\"none\",\"created\":\"2014-12-15T12:22:12Z\",\"length\":\"4.5\",\"url\":\"https://swapi.py4e.com/api/vehicles/14/\",\"manufacturer\":\"Incom corporation\",\"crew\":\"2\",\"vehicle_class\":\"airspeeder\",\"cost_in_credits\":\"unknown\",\"name\":\"Snowspeeder\",\"model\":\"t-47 airspeeder\"},{\"max_atmosphering_speed\":\"850\",\"cargo_capacity\":\"none\",\"films\":[\"https://swapi.py4e.com/api/films/2/\",\"https://swapi.py4e.com/api/films/3/\"],\"passengers\":\"0\",\"pilots\":[],\"edited\":\"2014-12-20T21:30:21.675000Z\",\"consumables\":\"2 days\",\"created\":\"2014-12-15T12:33:15.838000Z\",\"length\":\"7.8\",\"url\":\"https://swapi.py4e.com/api/vehicles/16/\",\"manufacturer\":\"Sienar Fleet Systems\",\"crew\":\"1\",\"vehicle_class\":\"space/planetary bomber\",\"cost_in_credits\":\"unknown\",\"name\":\"TIE bomber\",\"model\":\"TIE/sa bomber\"},{\"max_atmosphering_speed\":\"60\",\"cargo_capacity\":\"1000\",\"films\":[\"https://swapi.py4e.com/api/films/2/\",\"https://swapi.py4e.com/api/films/3/\"],\"passengers\":\"40\",\"pilots\":[],\"edited\":\"2014-12-20T21:30:21.677000Z\",\"consumables\":\"unknown\",\"created\":\"2014-12-15T12:38:25.937000Z\",\"length\":\"20\",\"url\":\"https://swapi.py4e.com/api/vehicles/18/\",\"manufacturer\":\"Kuat Drive Yards, Imperial Department of Military Research\",\"crew\":\"5\",\"vehicle_class\":\"assault walker\",\"cost_in_credits\":\"unknown\",\"name\":\"AT-AT\",\"model\":\"All Terrain Armored Transport\"},{\"max_atmosphering_speed\":\"90\",\"cargo_capacity\":\"200\",\"films\":[\"https://swapi.py4e.com/api/films/2/\",\"https://swapi.py4e.com/api/films/3/\"],\"passengers\":\"0\",\"pilots\":[\"https://swapi.py4e.com/api/people/13/\"],\"edited\":\"2014-12-20T21:30:21.679000Z\",\"consumables\":\"none\",\"created\":\"2014-12-15T12:46:42.384000Z\",\"length\":\"2\",\"url\":\"https://swapi.py4e.com/api/vehicles/19/\",\"manufacturer\":\"Kuat Drive Yards, Imperial Department of Military Research\",\"crew\":\"2\",\"vehicle_class\":\"walker\",\"cost_in_credits\":\"unknown\",\"name\":\"AT-ST\",\"model\":\"All Terrain Scout Transport\"},{\"max_atmosphering_speed\":\"1500\",\"cargo_capacity\":\"10\",\"films\":[\"https://swapi.py4e.com/api/films/2/\"],\"passengers\":\"0\",\"pilots\":[],\"edited\":\"2014-12-20T21:30:21.681000Z\",\"consumables\":\"1 day\",\"created\":\"2014-12-15T12:58:50.530000Z\",\"length\":\"7\",\"url\":\"https://swapi.py4e.com/api/vehicles/20/\",\"manufacturer\":\"Bespin Motors\",\"crew\":\"2\",\"vehicle_class\":\"repulsorcraft\",\"cost_in_credits\":\"75000\",\"name\":\"Storm IV Twin-Pod cloud car\",\"model\":\"Storm IV Twin-Pod\"},{\"max_atmosphering_speed\":\"100\",\"cargo_capacity\":\"2000000\",\"films\":[\"https://swapi.py4e.com/api/films/3/\"],\"passengers\":\"500\",\"pilots\":[],\"edited\":\"2014-12-20T21:30:21.684000Z\",\"consumables\":\"Live food tanks\",\"created\":\"2014-12-18T10:44:14.217000Z\",\"length\":\"30\",\"url\":\"https://swapi.py4e.com/api/vehicles/24/\",\"manufacturer\":\"Ubrikkian Industries Custom Vehicle Division\",\"crew\":\"26\",\"vehicle_class\":\"sail barge\",\"cost_in_credits\":\"285000\",\"name\":\"Sail barge\",\"model\":\"Modified Luxury Sail Barge\"}]}";

		// Actual jsonString
		String actual = httpClientHelper.getStringRoot(new URL(url));

		assertEquals(expected, actual, "The 'getStringRoot' method should return a stringJson");
	}

	@Test
	@DisplayName(value = "Testing 'getJsonRoot' method")
	public void getJsonRootTest() throws IOException {

		// URL for testing
		String url = env.getProperty("ds_test");

		// Get jsonString
		String jsonString = httpClientHelper.getStringRoot(new URL(url));

		// Create objectMapper
		ObjectMapper objectMapper = new ObjectMapper();

		// Convert JsonString to JsonNode
		JsonNode jsonNode = objectMapper.readTree(jsonString);

		// Get expected array "results" from jsonNode
		JsonNode expected = jsonNode.get("results");

		// Get actual array "results" from jsonNode
		JsonNode actual = httpClientHelper.getJsonRoot(new URL(url)).get("results");

		assertEquals(expected, actual, "The 'getJsonRoot' method should return a JsonNode object");
	}

	// @Test
	public void getObjectRootTest() {

	}
}
