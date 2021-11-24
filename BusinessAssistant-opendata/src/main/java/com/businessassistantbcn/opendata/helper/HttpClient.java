package com.businessassistantbcn.opendata.helper;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

//La clase HttpClient fectua consultas REST a cualquier URL.
@Component
public class HttpClient {

	// El resultado de la consulta es un file JSON en crudo, tal
	// como se provee en muchas de las peticiones de
	// https://opendata-ajuntament.barcelona.cat/es/api-cataleg.
	// El json resultante retorna un JSONObject
	public JSONObject getJsonFromUrl(String url) {

		// Creating a HttpClient object
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// Creating a HttpGet object
		HttpGet request = new HttpGet(url);
		// Executing the Get request
		HttpResponse response = null;

		try {
			response = httpClient.execute(request);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		HttpEntity entity = response.getEntity();

		if (entity != null) {
			String result = null;
			try {
				result = EntityUtils.toString(entity);
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// Convert String to JSON Object
			JSONObject entityJson = new JSONObject(result);
			return entityJson;
		}
		return null;
	}
}
