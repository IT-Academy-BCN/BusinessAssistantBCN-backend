package com.businessassistantbcn.mydata.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.businessassistantbcn.mydata.entity.UserSearch;

@Repository
@Transactional
public interface IUserSearchesRepository extends JpaRepository<UserSearch, String>{
	
	public boolean existsBySearchUuid(String searchUuid);
	public boolean existsByUserUuid(String userUuid);
	
	public List<UserSearch> findBySearchUuid(String searchUuid);
	public List<UserSearch> findByUserUuid(String userUuid);

	UserSearch findOneBySearchUuid(String searchUuid);

} 