package com.businessassistantbcn.mydata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.businessassistantbcn.mydata.entities.Search;

@Repository
@Transactional
public interface MySearchesRepository extends JpaRepository<Search, String> {
	
	public boolean existsBySearchUuid(String searchUuid);
	public boolean existsByUserUuid(String userUuid);
	
	public List<Search> findBySearchUuid(String searchUuid);
	public List<Search> findByUserUuid(String userUuid);
	
}