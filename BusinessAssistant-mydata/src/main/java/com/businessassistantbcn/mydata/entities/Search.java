package com.businessassistantbcn.mydata.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	@Column(name = "search_uuid")
	private String searchUuid;
	@Column(name = "user_uuid", nullable = false)
	private String userUuid;
	@Column(name = "search_date", updatable = false, nullable = false)
	@Temporal(TemporalType.DATE)
	private Date searchDate;
	@Column(name = "search_name", nullable = false, length = 45)
	private String searchName;
	@Column(name = "search_detail", nullable = true)
	private String searchDetail;
	@Convert(converter = JsonHelper.class)
	@Column(name = "search_result", nullable = true)
	private JsonNode searchResult;
	
}