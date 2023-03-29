package com.businessassistantbcn.mydata.repository;

import com.businessassistantbcn.mydata.entity.UserSearch;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = { DBTestConfiguration.class })
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
//CAUTION: For uses with real database
//@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class IUserSearchesRepositoryTest {
	@Autowired
	private IUserSearchesRepository IUserSearchesRepository;
	
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DataSource dataSource;
    
    private static UserSearch search = new UserSearch();
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
		System.out.println(IUserSearchesRepository.findAll().toString());

		assertNotNull(IUserSearchesRepository);
		assertNotNull(entityManager);
		assertNotNull(dataSource);
    }
	
	@Test
	void whenSaved_thenFindsByUserUuid() {
	  IUserSearchesRepository.save(search);
	  assertThat(IUserSearchesRepository.findByUserUuid("44c5c069-e907-45a9-8d49-2042044c56e0")).isNotNull();
	}

	@Test
	void whenSaved_thenSearchUuidIsCreated() {
		IUserSearchesRepository.save(search);
		assertThat(IUserSearchesRepository.findByUserUuid("44c5c069-e907-45a9-8d49-2042044c56e0")
				.orElseGet(Collections::emptyList).get(0).getSearchUuid()).isNotNull();
	}

	@Test
	void whenSearchExists_thenReturnsTrue() {
		assertTrue(IUserSearchesRepository.existsBySearchUuid(searchUuidFromTestDb));
	}
	
	@Test
	void whenSearchDoesNotExist_thenReturnsFalse() {
		assertFalse(IUserSearchesRepository.existsBySearchUuid(searchUuidNotIntTestDb));
	}
	
	@Test
	void whenFindByExistingUserUuid_thenReturnsSearch() {
		List<UserSearch> searchList = IUserSearchesRepository.findByUserUuid(userUuidFromTestDb).orElseGet(Collections::emptyList);
		assertThat(searchList).isNotEmpty();
		assertThat(searchList.get(0).getSearchUuid()).isEqualTo(searchUuidFromTestDb);
	}
	
	@Test
	void whenFindByNonExistingUserUuid_thenReturnsEmptyList() {
		assertThat(IUserSearchesRepository.findByUserUuid(userUuidNotInTestDb).orElseGet(Collections::emptyList)).isEmpty();
	}
	
	@Test
	void whenFindByExistingSearchUuid_thenReturnsSearch() {
		assertThat(IUserSearchesRepository.findBySearchUuid(searchUuidFromTestDb)).isNotEmpty();
		assertThat(IUserSearchesRepository.findBySearchUuid(searchUuidFromTestDb).get(0).getUserUuid()).isEqualTo(userUuidFromTestDb);
	}
	
	@Test
	void whenFindByNonExistingSearchUuid_thenReturnsEmptyList() {
		assertThat(IUserSearchesRepository.findByUserUuid(searchUuidNotIntTestDb).orElseGet(Collections::emptyList)).isEmpty();
	}

	@Test
	void whenFindOneBySearchUuid_thenReturnThisSearch(){
		Optional<UserSearch> userSearchFound = IUserSearchesRepository.findOneBySearchUuid(searchUuidFromTestDb);
		assertThat(userSearchFound.orElseThrow().getSearchUuid()).isEqualTo("8480788D-1FE0-035D-32D7-24984EBA8615");
	}

	@Test
	void whenfindOneBySearchUuidAndUserUuid_thenReturnThisSearch(){
		Optional<UserSearch> userSearchFound = IUserSearchesRepository.findOneBySearchUuidAndUserUuid(searchUuidFromTestDb, userUuidFromTestDb);

		assertThat(userSearchFound).isPresent();
		assertThat(userSearchFound.get().getUserUuid()).isEqualTo("DB3C2A2A-D36E-38C7-8A0C-1B2D3CF2BE57");
		assertThat(userSearchFound.get().getSearchUuid()).isEqualTo("8480788D-1FE0-035D-32D7-24984EBA8615");
	}
	@Test
	void whenfindOneBySearchUuidAndUserUuidNoExists_thenReturnEmpty(){
		Optional<UserSearch> userSearchFound = IUserSearchesRepository.findOneBySearchUuidAndUserUuid(searchUuidNotIntTestDb, userUuidNotInTestDb);
		assertThat(userSearchFound).isEmpty();
		assertNotNull(userSearchFound);
	}

}
