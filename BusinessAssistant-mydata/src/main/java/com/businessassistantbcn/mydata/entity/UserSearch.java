package com.businessassistantbcn.mydata.entity;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

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
public class UserSearch {
	@Id
	@GeneratedValue(generator = "idGenerator") 
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof UserSearch that)) return false;
		return Objects.equals(searchUuid, that.searchUuid) && Objects.equals(userUuid, that.userUuid) && Objects.equals(searchDate, that.searchDate) && Objects.equals(searchName, that.searchName) && Objects.equals(searchDetail, that.searchDetail) && Objects.equals(searchResult, that.searchResult);
	}
}