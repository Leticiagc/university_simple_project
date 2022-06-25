package com.ufcg.university.settings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    
    private AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
        	com.ufcg.university.entities.User credentials = new ObjectMapper().readValue(request.getInputStream(), 
                						com.ufcg.university.entities.User.class);
        	
            return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getName(), credentials.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException("Could not read request" + e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException {
	    String token = Jwts.builder()
	                    .setSubject(((User) authResult.getPrincipal()).getUsername())
	                    .setIssuedAt(new Date(System.currentTimeMillis()))
	                    .setExpiration(new Date(System.currentTimeMillis() + 864_000_000))
	                    .signWith(SignatureAlgorithm.HS256, "SECRETKEYffdsfdsfdsfsdfdsfdsfsdfsdfsdsdfds".getBytes())
	                    .compact();
        response.addHeader("Authorization", "Bearer " + token);
    }
}
