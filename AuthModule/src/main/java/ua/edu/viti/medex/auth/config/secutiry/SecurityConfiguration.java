package ua.edu.viti.medex.auth.config.secutiry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import ua.edu.viti.medex.auth.dao.TokenDAOImp;

@EnableWebSecurity
@Configuration
@ComponentScan(value = "ua.edu.viti.medex")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Autowired
	MySavedRequestAwareAuthenticationSuccessHandler successHandler;

	@Autowired
	RolesDetailsService rolesDetailsService;

	@Autowired
	TokenDAOImp tokenDAOImp;

	private SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(rolesDetailsService).passwordEncoder(encoder());
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.exceptionHandling()
				.authenticationEntryPoint(restAuthenticationEntryPoint)
				.and()
				.authorizeRequests()
				.antMatchers("/signup").hasRole("ADMIN")
				.antMatchers("/login").permitAll()
				.antMatchers("/signout").permitAll()
				.antMatchers("/**").authenticated()
				.and()
				.formLogin()
				.successHandler(successHandler)
				.failureHandler(failureHandler)
				.and()
				.logout()
				.logoutSuccessUrl("/signout")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.and()
				.rememberMe().rememberMeParameter("remember-me")
				.tokenRepository(tokenDAOImp).userDetailsService(rolesDetailsService);
	}
}
