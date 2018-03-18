package br.com.skip.lucious.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginFilter extends AbstractAuthenticationProcessingFilter implements Filter {

	public LoginFilter(String url, AuthenticationManager authManager) {
		super(url);
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException, IOException, ServletException {

		UserDTO user = new ObjectMapper().readValue(req.getInputStream(), UserDTO.class);

		return getAuthenticationManager()
				.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain,
			Authentication auth) throws IOException, ServletException {

		TokenAuthenticationService.addAuthentication(res, auth.getName());
	}

}
