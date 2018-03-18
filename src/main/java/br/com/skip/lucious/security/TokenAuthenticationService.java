package br.com.skip.lucious.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenticationService {

	private static final long SECONDS = 1000;
	private static final long EXPIRATION_TIME = 300 * SECONDS; //5 minutes
	private static final String SECRET = "a5d8545d837d";
	private static final String HEADER_STRING = "Authorization";
	private static final String TOKEN_PREFIX = "JWT";

	public static void addAuthentication(HttpServletResponse res, String username) {
		String token = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
		
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + token);
	}

	public static Authentication getAuthentication(HttpServletRequest req) {
		String token = req.getHeader(HEADER_STRING);
		
		if (token != null) {
			// parse the token
			String user = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
					.getBody()
					.getSubject();
			
			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null);
			}
		}
		return null;
	}

}
