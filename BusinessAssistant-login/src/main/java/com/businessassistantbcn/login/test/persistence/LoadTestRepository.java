package com.businessassistantbcn.login.test.persistence;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.businessassistantbcn.login.test.domain.TestUser;

@Configuration
@Slf4j
public class LoadTestRepository {
	
	@Bean
	CommandLineRunner initTestRepository(TestRepository repo) {
		return args -> {
			repo.save(new TestUser("jvicente@gmail.com", "56589pp05s", List.of("ROLE_ADMINISTRATOR")));
			// Aquí se puede añadir nuevos usuarios para los ensayos.
			
			repo.findAll().forEach(user -> log.info("Loading test user " + user.getEmail() + "..."));
		};
	}
	
}