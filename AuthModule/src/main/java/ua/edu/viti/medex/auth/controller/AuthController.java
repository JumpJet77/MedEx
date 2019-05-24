package ua.edu.viti.medex.auth.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.viti.medex.auth.config.secutiry.SecurityConstants;
import ua.edu.viti.medex.auth.dao.TokenDAOImpl;
import ua.edu.viti.medex.auth.entities.Tokens;

import java.util.Date;

//TODO: (in future) add more detailed exception handlers

/**
 * @author Ihor Dovhoshliubnyi
 * Contoroller with endpoints for auth management (token and users management)
 */

@SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection", "JavaDoc"})
@RestController
@RequestMapping(value = "/")
public class AuthController {

	private Logger logger = LogManager.getLogger(AuthController.class);

	@Autowired
	TokenDAOImpl tokenDAO;

	/**
	 * signout succsess url
	 * invalidates token which was used to perform operations
	 * @param token Token of person, which should be invalidated
	 * @return OK response if token successfully invalidated to this mapping or NOT_FOUND if token not found in DB
	 * @request GET http://139.28.37.150:8888/logout
	 */
	@PostMapping(value = "/signout")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> logout(@RequestBody String token) {
		byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();
		Jws<Claims> parsedToken = Jwts.parser()
				.setSigningKey(signingKey)
				.parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""));

		String email = parsedToken
				.getBody()
				.getSubject();
		try {
			tokenDAO.invalidateToken(tokenDAO.getTokenFromEmail(email));
		} catch (NotFoundException e) {
			logger.error("Token not found");
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
	 * endpoint to refresh old token to avoid persons logout during work
	 *
	 * @param token token which should be refreshed
	 * @return Ok status if token successfully refreshed
	 */

	@PostMapping(value = "/refresh")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> refresh(@RequestBody String token) {
		byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();
		Jws<Claims> parsedToken = Jwts.parser()
				.setSigningKey(signingKey)
				.parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""));

		Date expToken = parsedToken
				.getBody()
				.getExpiration();

		String email = parsedToken.getBody().getSubject();
		try {
			tokenDAO.refreshToken(new Tokens(token, email, expToken, true));
		} catch (NotFoundException e) {
			logger.error("Token not found");
		}
		return new ResponseEntity(HttpStatus.OK);
	}
}
