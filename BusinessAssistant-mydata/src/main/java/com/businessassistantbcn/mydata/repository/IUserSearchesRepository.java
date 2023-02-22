package com.businessassistantbcn.mydata.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.businessassistantbcn.mydata.entity.UserSearch;

import javax.swing.text.html.Option;

@Repository
@Transactional
public interface IUserSearchesRepository extends JpaRepository<UserSearch, String>{
	
	boolean existsBySearchUuid(String searchUuid);
	boolean existsByUserUuid(String userUuid);
	
	List<UserSearch> findBySearchUuid(String searchUuid);
	Optional<List<UserSearch>> findByUserUuid(String userUuid);

	Optional<UserSearch> findOneBySearchUuid(String searchUuid);

} 