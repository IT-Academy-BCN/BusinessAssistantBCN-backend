package com.businessassistantbcn.login.security;

/*
 * El codigo comentado debe reincorporarse si los JWT de autorizacion empiezan a incluir
 * entre sus claims una lista de perfiles con rotulo config.getAuthorities()
 */

import java.io.IOException;

import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.businessassistantbcn.login.config.PropertiesConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private PropertiesConfig config;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		try {
			String authorizationHeader = request.getHeader(config.getHeaderString());
			
			if(authorizationHeaderIsValid(authorizationHeader)){
				Claims claims = validateToken(request);        	
/*	        	
				// Comprobacion que el token contiene claims 'exp' y config.getAuthorities()
				if(claims.getExpiration() != null) && claims.get(config.getAuthorities()) != null) {
*/
				if(claims.getExpiration() != null) {
					setUpSpringAuthentication(claims);
					filterChain.doFilter(request, response);
					return;
				}
				else throw new UnsupportedJwtException("Missing expiration claim");
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
		String jwtToken = request.getHeader(config.getHeaderString()).replace(config.getTokenPrefix(), "");
		
		return Jwts.parserBuilder()
				.setSigningKey(config.getSecret().getBytes())
				.build()
				.parseClaimsJws(jwtToken) // Incluye la comprobacion temporal para claim 'exp'
				.getBody();
	}
	private void setUpSpringAuthentication(Claims claims) {
/*		@SuppressWarnings("unchecked")
		List<String> authorities = (List<String>)claims.get(myConfig.getAuthorities());
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				claims.getSubject(), null,
				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
*/
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				claims.getSubject(), null, List.of());
		
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}
	
}
