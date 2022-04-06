package com.businessassistantbcn.mydata.repository;

import com.businessassistantbcn.mydata.config.SpringDBTestConfiguration;
import com.businessassistantbcn.mydata.entities.Search;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.HashMap;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SpringDBTestConfiguration.class })
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
//CAUTION: For uses with real database
//@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class UserSearchesRepositoryTest {
	@Autowired
	private UserSearchesRepository userSearchesRepository;
	
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DataSource dataSource;
    
    private static Search search = new Search();
    private String userUuidFromTestDb = "DB3C2A2A-D36E-38C7-8A0C-1B2D3CF2BE57";
	private String searchUuidFromTestDb = "8480788D-1FE0-035D-32D7-24984EBA8615";
	private String userUuidNotInTestDb = "NOEXITS1-D36E-38C7-8A0C-1B2D3CF2BE57";
	private String searchUuidNotIntTestDb = "NOEXITS1-1FE0-035D-32D7-24984EBA8615";
	
    @BeforeAll
    public static void createSearchForTest() {
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, String> searchResult = new HashMap<String, String>();
		searchResult.put("name", "John Doe");
		searchResult.put("email", "john.doe@example.com");

		search.setUserUuid("44c5c069-e907-45a9-8d49-2042044c56e0");
		search.setSearchDate(new Date());
		search.setSearchName("searchName");
		search.setSearchDetail("detail");
		search.setSearchResult(mapper.valueToTree(searchResult));
    }
    
	@Test
	void test() {
		System.out.println(userSearchesRepository.findAll().toString());

		assertNotNull(userSearchesRepository);
		assertNotNull(entityManager);
		assertNotNull(dataSource);
    }
	
	@Test
	void whenSaved_thenFindsByUserUuid() {
	  userSearchesRepository.save(search);
	  assertThat(userSearchesRepository.findByUserUuid("44c5c069-e907-45a9-8d49-2042044c56e0")).isNotNull();
	}

	@Test
	void whenSaved_thenSearchUuidIsCreated() {
		userSearchesRepository.save(search);
		assertThat(userSearchesRepository.findByUserUuid("44c5c069-e907-45a9-8d49-2042044c56e0").get(0).getSearchUuid()).isNotNull();
	}

	@Test
	void whenSearchExists_thenReturnsTrue() {
		assertTrue(userSearchesRepository.existsBySearchUuid(searchUuidFromTestDb));
	}
	
	@Test
	void whenSearchDoesNotExist_thenReturnsFalse() {
		assertFalse(userSearchesRepository.existsBySearchUuid(searchUuidNotIntTestDb));
	}
	
	@Test
	void whenFindByExistingUserUuid_thenReturnsSearch() {
		assertThat(userSearchesRepository.findByUserUuid(userUuidFromTestDb)).isNotEmpty();
		assertThat(userSearchesRepository.findByUserUuid(userUuidFromTestDb).get(0).getSearchUuid()).isEqualTo(searchUuidFromTestDb);
	}
	
	@Test
	void whenFindByNonExistingUserUuid_thenReturnsEmptyList() {
		assertThat(userSearchesRepository.findByUserUuid(userUuidNotInTestDb)).isEmpty();
	}
	
	@Test
	void whenFindByExistingSearchUuid_thenReturnsSearch() {
		assertThat(userSearchesRepository.findBySearchUuid(searchUuidFromTestDb)).isNotEmpty();
		assertThat(userSearchesRepository.findBySearchUuid(searchUuidFromTestDb).get(0).getUserUuid()).isEqualTo(userUuidFromTestDb);
	}
	
	@Test
	void whenFindByNonExistingSearchUuid_thenReturnsEmptyList() {
		assertThat(userSearchesRepository.findByUserUuid(searchUuidNotIntTestDb)).isEmpty();
	}

}
