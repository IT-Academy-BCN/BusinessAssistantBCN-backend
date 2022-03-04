package com.businessassistantbcn.mydata.entities;

import java.util.Date;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.businessassistantbcn.mydata.helper.JsonHelper;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "my_searches")
public class Search {
	@Id
	@GeneratedValue(generator = "idGenerator") 
	private String searchUuid;
	private String userUuid;
	private Date searchDate;
	private String searchName;
	private String searchDetail;
	@Convert(converter = JsonHelper.class)
	private JsonNode searchResult;
	
}