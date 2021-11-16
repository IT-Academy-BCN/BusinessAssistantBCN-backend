package com.businessassistantbcn.login.security;

/**
 * Estas constantes son de uso temporal, durante la fase de desarrollo
 */

public class SecurityConstants {
	
	public static final String SECRET = "w#~;^p@:FKhaM$m6";
	public static final long EXPIRATION_TIME = 3_600_000L; // 1 hora
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "businessassistantbcn/v1/api/login";
	
}
