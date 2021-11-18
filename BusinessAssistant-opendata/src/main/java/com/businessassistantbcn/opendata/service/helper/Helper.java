package com.businessassistantbcn.opendata.service.helper;

import org.json.JSONObject;

public class Helper {
	public String getObject() {

		JSONObject myObject = new JSONObject();

		// Cadenas de texto basicas
		myObject.put("name", "Carlos");
		myObject.put("last_name", "Carlos");

		// Valores primitivos
		myObject.put("age", 21);
		myObject.put("bank_account_balance", 20.5);
		myObject.put("is_developer", true);

		// Matrices
		double[] myList = { 1.9, 2.9, 3.4, 3.5 };
		myObject.put("number_list", myList);

		// Objeto dentro de objeto
		JSONObject subdata = new JSONObject();
		subdata.put("nombre", "pepito");
		subdata.put("edad", 30);

		myObject.put("extra_data", subdata);

		// Generar cadena de texto JSON
		System.out.print(myObject);
		return "" + myObject;
	}
}
