package com.businessassistantbcn.login.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/v2")
public class CensController {

	public CensController() {
	}

	@GetMapping("/cens-activitats-economiques")
	public String getCens() throws JsonParseException, JsonMappingException, IOException, URISyntaxException {

		// TODO eliminar url y pasarlo por parametro->getCens(String url)
		String url = "https://opendata-ajuntament.barcelona.cat/data/dataset/671c46e9-5b85-4e63-8c97-088a2b907cd5/resource/7a3d5380-f79a-424e-be62-dd078efcb40a/download/2019_censcomercialbcn_class_act.json";

		return wrapJsonObject(getJSONdataViaHTTP(url));
	}

	public CensActivitatsEconomiquesBcnDTO[] getJSONdataViaHTTP(String url) {

		RestTemplate restTemplate = new RestTemplate();

		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		CensActivitatsEconomiquesBcnDTO[] censS = restTemplate.getForObject(url,

		CensActivitatsEconomiquesBcnDTO[].class);

		return censS;

	}

	/**
	 * - Wrapping an ArrayNode in ObjectNode and returning this ArrayNode as String
	 * 
	 * @param cens
	 * @return
	 */
	public String wrapJsonObject(CensActivitatsEconomiquesBcnDTO[] cens) {

		String jsonString = null;

		ObjectNode outerNodeJsonObject = null;

		try {
			ObjectMapper mapperDto = new ObjectMapper();

			jsonString = mapperDto.writeValueAsString(cens);

			ObjectMapper mapper = new ObjectMapper();

			JsonNode parsedJson = mapper.readTree(jsonString);

			outerNodeJsonObject = mapper.createObjectNode();

			outerNodeJsonObject.put("count", parsedJson.size()).putPOJO("elements", parsedJson);

		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}
		return outerNodeJsonObject.toPrettyString();
	}
}