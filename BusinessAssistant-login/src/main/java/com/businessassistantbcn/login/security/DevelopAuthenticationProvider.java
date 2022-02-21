package com.businessassistantbcn.login.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.businessassistantbcn.login.config.DevelopAdminUser;
import com.businessassistantbcn.login.service.LoginService;

@SuppressWarnings("deprecation") // ¡Contraseñas sin encriptar!
@Component
public class DevelopAuthenticationProvider extends DaoAuthenticationProvider {
	
	@SuppressWarnings("unused") 
	private static final List<GrantedAuthority> AUTHORITIES; // Potencialmente útil próximamente
	private static final PasswordEncoder encoder;
	
	static {
		AUTHORITIES = AuthorityUtils.commaSeparatedStringToAuthorityList(""
				+ "ADMIN,"
				+ "USER"
				// Aquí se pueden añadir más credenciales separadas por comas
				);
		encoder = NoOpPasswordEncoder.getInstance();
	}
	
	private static List<TestUser> testUsers = new ArrayList<>();
	
	@Getter
	@AllArgsConstructor
	private static class TestUser {
		
		private String email;
		private String password;
		private List<String> roles;
		
		public boolean match(String name) {
			return email.equals(name);
		}
		
	}
	
	@Autowired
	public DevelopAuthenticationProvider(LoginService loginService, DevelopAdminUser adminUser) {
		testUsers.add(new TestUser(adminUser.getUserName(), adminUser.getPassword(), List.of("ADMIN")));
		// Aquí se pueden añadir más usuarios a 'testUsers'
		
		super.setUserDetailsService(loginService);
		super.setPasswordEncoder(encoder);
	}
	
	public static Optional<User> findByUserName(String username) {
		Function<TestUser, User> conversion = user -> {
				List<String> auth = user.getRoles();
				return new User(user.getEmail(), user.getPassword(),
						auth.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		};
		
		return testUsers.stream().filter(u -> u.match(username)).map(conversion).findFirst();
	}
	
}
