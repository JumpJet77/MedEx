package ua.edu.viti.medex.auth.config.secutiry;

import io.jsonwebtoken.*;
import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;
import ua.edu.viti.medex.auth.dao.TokenDAOImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ihor Dovhoshliubnyi
 * Filter which filters all requests and discover if user, who attempting requests has neccecary role
 * Constructor receives AuthenticationMAnager and TokenDAO as filter is not part of Spring context so field could not be autoired
 * so injecing fields through constructor
 */

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private static final Logger log = LogManager.getLogger(JwtAuthorizationFilter.class);
	private TokenDAOImpl tokenDAO;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, TokenDAOImpl tokenDAO) {
		super(authenticationManager);
		this.tokenDAO = tokenDAO;
	}

	/**
	 * Method which perform filtering and discover if user has role to access endpoints
	 * and discovers if token must be refresh id exp time < 15 mins
	 *
	 * @param request     request to certain endpoint
	 * @param response    response of certain endpoint
	 * @param filterChain Spring Securityb filter chain
	 * @throws IOException      thrown if writer could not write
	 * @throws ServletException for other exceptions
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
	                                FilterChain filterChain) throws IOException, ServletException {
		UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
		String header = request.getHeader(SecurityConstants.TOKEN_HEADER);

		if (StringUtils.isEmpty(header) || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			filterChain.doFilter(request, response);
			return;
		}
		byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();
		Jws<Claims> parsedToken = Jwts.parser()
				.setSigningKey(signingKey)
				.parseClaimsJws(header.replace(SecurityConstants.TOKEN_PREFIX, ""));

		Date expToken = parsedToken
				.getBody()
				.getExpiration();

		if (expToken.getTime() - (System.currentTimeMillis()) < 600000) {
			response.sendRedirect("/refresh");
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}


	/**
	 * Method for receiving roles from internal Spring Security authentication object
	 *
	 * @param request request where authentication stores
	 * @return internal Spring Security token
	 */
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(SecurityConstants.TOKEN_HEADER);
		if (!StringUtils.isEmpty(token)) {
			try {
				byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();
				Jws<Claims> parsedToken = Jwts.parser()
						.setSigningKey(signingKey)
						.parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""));

				String username = parsedToken
						.getBody()
						.getSubject();

				if (!tokenDAO.getTokenFromEmail(username).isValid()) {
					return new UsernamePasswordAuthenticationToken(username, null, null);
				}

				List<SimpleGrantedAuthority> authorities = ((List<?>) parsedToken.getBody()
						.get("role")).stream()
						.map(authority -> new SimpleGrantedAuthority((String) authority))
						.collect(Collectors.toList());

				if (!StringUtils.isEmpty(username)) {
					return new UsernamePasswordAuthenticationToken(username, null, authorities);
				}
			} catch (ExpiredJwtException exception) {
				log.warn("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
			} catch (UnsupportedJwtException exception) {
				log.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
			} catch (MalformedJwtException exception) {
				log.warn("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
			} catch (IllegalArgumentException exception) {
				log.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
			} catch (NotFoundException e) {
				log.error("Token not found");
			}
		}
		return null;
	}
}
