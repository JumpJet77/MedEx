package ua.edu.viti.medex.auth.config.secutiry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ua.edu.viti.medex.auth.dao.TokenDAOImpl;

/**
 * @author Ihor Dovhoshliubnyi
 * Spring Security main configuration class
 * Configured to load user by username from DB
 * Created CORS bean for complatibility with React SPA
 * Autowired Rest Auth Entry Point which has method which receives control in case of unathorized access
 * Confidured to work with JWT tokens system, by two filters, which also persists tokens
 */

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
@ComponentScan(value = "ua.edu.viti.medex")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Autowired
	UsersDetailsService usersDetailsService;

	@Autowired
	TokenDAOImpl tokenDAO;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usersDetailsService).passwordEncoder(encoder());
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.cors()
				.and()
				.csrf().disable()
				.exceptionHandling()
				.authenticationEntryPoint(restAuthenticationEntryPoint)
				.and()
				.authorizeRequests()
				.antMatchers("/signup").hasRole("ADMIN")
				.antMatchers("/login").permitAll()
				.antMatchers("/signout").permitAll()
				.antMatchers("/refresh").permitAll()
				.antMatchers("/").hasRole("DOCTOR, NURSE, ADMIN")
				.antMatchers("/{id}").hasRole("DOCTOR, NURSE, ADMIN, USER")
				.antMatchers("/update").hasRole("DOCTOR, NURSE, ADMIN")
				.and()
				.addFilter(new JwtAuthenticationFilter(authenticationManager(), tokenDAO))
				.addFilter(new JwtAuthorizationFilter(authenticationManager(), tokenDAO))
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
}
