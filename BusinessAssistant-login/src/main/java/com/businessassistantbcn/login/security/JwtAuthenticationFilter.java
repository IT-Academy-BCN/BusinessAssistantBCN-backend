package com.businessassistantbcn.login.security;

import com.businessassistantbcn.login.config.SecurityConfig;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private SecurityConfig config;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain filterChain) throws IOException, ServletException {
		try {

			String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

			if(authorizationHeaderIsValid(authorizationHeader)) {
				Claims claims = validateToken(request);
				setUpSpringAuthentication(claims);
			}
			filterChain.doFilter(request, response);
		} catch(ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	private boolean authorizationHeaderIsValid(String authorizationHeader) {
		return authorizationHeader != null && authorizationHeader.startsWith(config.getTokenPrefix());
	}

	private Claims validateToken(HttpServletRequest request) {
		String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION).replace(config.getTokenPrefix(), "");

		return Jwts.parserBuilder()
				.setSigningKey(config.getSecret().getBytes())
				.build()
				.parseClaimsJws(jwtToken) // Incluye la comprobación temporal para claim 'exp'
				.getBody();
	}

	private void setUpSpringAuthentication(Claims claims) {
		@SuppressWarnings("unchecked")
		List<String> authorities = (List<String>)claims.get(config.getAuthoritiesClaim());

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				claims.getSubject(), null,
				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}

}