package com.businessassistantbcn.mydata.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.businessassistantbcn.mydata.helper.JsonNodeConverter;
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
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name="search_uuid", columnDefinition = "VARCHAR(36)")
	private String searchUuid;
	private Date searchDate;
	private String searchName;
	private String searchDetail;
	@Convert(converter = JsonNodeConverter.class)
	private JsonNode search_result;
	@Column(name="user_uuid")
	private String userUuid;
}