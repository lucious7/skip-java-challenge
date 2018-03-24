package br.com.skip.lucious.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${queries.roles-query}")
	private String rolesQuery;

	@Value("${queries.users-query}")
	private String usersQuery;

	@Autowired
	private DataSource dataSource;
	
//	@Autowired
//	private BCryptPasswordEncoder bCryptEncoder;

	@Override
	protected void configure(HttpSecurity security) throws Exception {
		security.authorizeRequests()
				.antMatchers("/Order/*").authenticated()
				.anyRequest().permitAll()
				.and()
				.addFilterBefore(new LoginFilter("/Customer/auth", authenticationManager()),
						UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new AuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		security.csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		 builder.jdbcAuthentication().dataSource(dataSource)
		 .authoritiesByUsernameQuery(rolesQuery)
		 .usersByUsernameQuery(usersQuery);
//		.passwordEncoder(bCryptEncoder);
	}

//	@Bean
//	public BCryptPasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
	
	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}

}
