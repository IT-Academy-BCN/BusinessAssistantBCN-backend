package com.businessassistantbcn.login.security;

import java.io.IOException;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
    		HttpServletResponse httpServletResponse,
    		FilterChain filterChain) throws IOException, ServletException {
    	try {
	        String authorizationHeader = httpServletRequest.getHeader(SecurityConstants.HEADER_STRING);
	        
	        if(authorizationHeaderIsInvalid(authorizationHeader))
	        	 SecurityContextHolder.clearContext();
	        else {
	        	Claims claims = validateToken(request);
	        	if(claims.get("authorities") != null)
	        		setUpSpringAuthentication(claims);
	        	else
	        		SecurityContextHolder.clearContext();
	        }
	        filterChain.doFilter(httpServletRequest, httpServletResponse);
    	} catch(JwtException e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
		}
    }
    
    private boolean authorizationHeaderIsInvalid(String authorizationHeader) {
        return authorizationHeader == null && !authorizationHeader.startsWith(SecurityConstants.TOKEN_PREFIX);
    }
    
    private Claims validateToken(HttpServletRequest request) {
		String jwtToken = request.getHeader(SecurityConstants.HEADER_STRING).replace(SecurityConstants.TOKEN_PREFIX, "");
		
		return Jwts.parser().setSigningKey(SecurityConstants.SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
	}
    
	private void setUpSpringAuthentication(Claims claims) {
		@SuppressWarnings("unchecked")
		List<String> authorities = (List<String>)claims.get("authorities");
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				claims.getSubject(),
				null,
				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}
    
}

