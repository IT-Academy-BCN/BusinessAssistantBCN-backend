package com.businessassistantbcn.login.security;



/**
 * El código comentado debe reincorporarse si los JWT de autorización empiezan a incluir
 * entre sus claims una lista de perfiles con rótulo SecurityConstants.AUTHORITIES
 */

import java.io.IOException;
//import java.util.List;
//import java.util.stream.Collectors;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
    		HttpServletResponse response,
    		FilterChain filterChain) throws IOException, ServletException {
    	try {
	        String authorizationHeader = request.getHeader(SecurityConstants.HEADER_STRING);
	        
	        if(authorizationHeaderIsInvalid(authorizationHeader))
	        	 SecurityContextHolder.clearContext();
	        else {
	        	Claims claims = validateToken(request);
/*	        	
	        	// Comprobación que el token contiene claims 'exp' y AUTHORITIES
	        	if(claims.getExpiration() != null) && claims.get(SecurityConstants.AUTHORITIES) != null)
*/
	        	if(claims.getExpiration() != null)
	        		setUpSpringAuthentication(claims);
	        	else
	        		SecurityContextHolder.clearContext();
	        }
	        filterChain.doFilter(request, response);
    	} catch(ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {
    		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
    	}
    }
    
    private boolean authorizationHeaderIsInvalid(String authorizationHeader) {
        return authorizationHeader == null || !authorizationHeader.startsWith(SecurityConstants.TOKEN_PREFIX);
    }
    
    private Claims validateToken(HttpServletRequest request) {
		String jwtToken = request.getHeader(SecurityConstants.HEADER_STRING).replace(SecurityConstants.TOKEN_PREFIX, "");
		
		return Jwts.parserBuilder()
				.setSigningKey(SecurityConstants.SECRET.getBytes())
				.build()
				.parseClaimsJws(jwtToken) // Incluye la comprobación temporal para claim 'exp'
				.getBody();
	}
    
	private void setUpSpringAuthentication(Claims claims) {
/*		@SuppressWarnings("unchecked")
		List<String> authorities = (List<String>)claims.get(SecurityConstants.AUTHORITIES);
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				claims.getSubject(),
				null,
				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
*/
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				claims.getSubject(), null);
		
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}
    
}
