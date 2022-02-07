package com.businessassistantbcn.login.test.persistence;

import com.businessassistantbcn.login.test.domain.TestUser;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Repository;

@Repository
public class TestRepository {
	
	private List<TestUser> testRepository;
	
	public TestRepository() {
		testRepository = new ArrayList<TestUser>();
	}
	
	public void save(TestUser newUser) {
		testRepository.add(newUser);
	}
	
	public TestUser findByUsername(String userName) { try {
		return testRepository.stream().filter(u -> u.getEmail().equals(userName)).findFirst().get();
	} catch(NoSuchElementException e) { return null; } }
	
	public List<TestUser> findAll() {
		return testRepository;
	}
	
}