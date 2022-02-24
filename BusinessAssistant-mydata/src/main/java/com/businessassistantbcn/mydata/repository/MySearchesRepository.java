package com.businessassistantbcn.mydata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface MySearchesRepository extends JpaRepository<Search, String>{
	
	public boolean existsBySearch_uuid(String search_uuid);
	public boolean existsByUser_uuid(String user_uuid);
	
	public List<Search> findBySearch_uuid(String search_uuid);
	public List<Search> findByUser_uuid(String user_uuid);
}
