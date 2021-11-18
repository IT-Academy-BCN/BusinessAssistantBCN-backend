package com.businessassistantbcn.login.security;

/**
 * Estas constantes son de uso temporal, durante la fase de desarrollo
 */

public class SecurityConstants {
	
	public static final String SIGN_UP_URL = "/v1/api/login";
	public static final String SECRET = "SgVkYp3s6v9y$B&E)H+MbQeThWmZq4t7";
	public static final long EXPIRATION_TIME = 3_600_000L; // 1 hora
	public static final String HEADER_STRING = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String AUTHORITIES = "authorities"; // lista de permisos (ROLES)
	
}
