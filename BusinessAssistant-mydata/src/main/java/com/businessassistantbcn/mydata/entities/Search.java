package com.businessassistantbcn.mydata.entities;

import java.util.Date;

import javax.persistence.Entity;

import org.springframework.data.annotation.Id;

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
public class Search {
	@Id
	private String search_uuid;
	private Date search_date;
	private String search_name;
	private String search_detail;
	private JsonNode search_result;
	private String user_uuid;
}
