package ua.edu.viti.medex.auth.config.secutiry;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.edu.viti.medex.auth.dao.TokenDAOImpl;
import ua.edu.viti.medex.auth.entities.Tokens;

import javax.persistence.NoResultException;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Ihor Dovhoshliubnyi
 * Spring Security filter to provide creating JWT token on user successful log in and persisting it into DB
 * @implNote constructor requires AuthenticationManager an TokenDAO as filter not a part of Spring context
 * so beans must be injected through constructor
 */

@SuppressWarnings({"WeakerAccess", "SpringJavaAutowiredMembersInspection"})
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final Logger logger = LogManager.getLogger(JwtAuthenticationFilter.class);
	private final TokenDAOImpl tokenDAO;
	private final AuthenticationManager authenticationManager;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, TokenDAOImpl tokenDAO) {
		this.authenticationManager = authenticationManager;
		setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
		this.tokenDAO = tokenDAO;
	}

	/**
	 * Method which provides authentication attempt receiving JSON with credentials in HTTP request body
	 *
	 * @param request  received HTTP request on '/login' endpoint
	 *                 must contain JSON with credentials
	 * @param response passes regular response
	 * @return Spring Security authentication attempt
	 * @throws AuthenticationException if authentication failed
	 */

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
	                                            HttpServletResponse response) throws AuthenticationException {

		String body = "";
		if ("POST".equalsIgnoreCase(request.getMethod())) {
			try {
				body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		GsonJsonParser jsonParser = new GsonJsonParser();
		Map<String, Object> credentials = jsonParser.parseMap(body);

		String username = (String) (credentials != null ? credentials.get("username") : null);
		String password = (String) (credentials != null ? credentials.get("password") : null);

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		return authenticationManager.authenticate(authenticationToken);
	}

	/**
	 * Method which handles control on successful authentication to create JWT and persist it to DB through Tokens entity
	 *
	 * @param request        regular HTTP request after auth attempt
	 * @param response       passes formed JWT with prefix which has all data
	 * @param filterChain    Spring Security filter chain
	 * @param authentication auth data formed on authentication attempt
	 * @throws IOException thrown if not able to write token into response body
	 * @see ua.edu.viti.medex.auth.entities.Tokens
	 */

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
	                                        FilterChain filterChain, Authentication authentication) throws IOException {
		User user = ((User) authentication.getPrincipal());
		Tokens token = null;
		try {
			token = tokenDAO.getTokenFromEmail(user.getUsername());
		} catch (NotFoundException | EmptyResultDataAccessException | NoResultException e) {

			List<String> roles = user.getAuthorities()
					.stream()
					.map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList());

			byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

			String newToken = Jwts.builder()
					.signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
					.setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
					.setIssuer(SecurityConstants.TOKEN_ISSUER)
					.setAudience(SecurityConstants.TOKEN_AUDIENCE)
					.setSubject(user.getUsername())
					.setExpiration(new Date(System.currentTimeMillis() + 86400000))
					.claim("role", roles)
					.compact();

			String completeToken = SecurityConstants.TOKEN_PREFIX + newToken;
			tokenDAO.addTokenData(completeToken);
			PrintWriter out = response.getWriter();
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			out.print(completeToken);
			out.flush();
			response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + newToken);
			return;
		}
		if ((token.getExpiration().getTime() - System.currentTimeMillis() > 900000)) {
			String completeToken = token.getToken();
			PrintWriter out = response.getWriter();
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			out.print(completeToken);
			out.flush();
			response.addHeader(SecurityConstants.TOKEN_HEADER, completeToken);
			return;
		} else {
			String completeToken = null;
			try {
				completeToken = tokenDAO.refreshToken(token).getToken();
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
			PrintWriter out = response.getWriter();
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			out.print(completeToken);
			out.flush();
			response.addHeader(SecurityConstants.TOKEN_HEADER, completeToken);
		}
	}
}
