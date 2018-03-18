package br.com.skip.lucious.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class AuthenticationFilter extends GenericFilterBean implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		Authentication auth = TokenAuthenticationService.getAuthentication((HttpServletRequest) req);

		SecurityContextHolder.getContext().setAuthentication(auth);
		chain.doFilter(req, res);
	}

}
