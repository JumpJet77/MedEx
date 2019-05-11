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
import ua.edu.viti.medex.auth.dao.UsersDAOImpl;
import ua.edu.viti.medex.auth.entities.Tokens;
import ua.edu.viti.medex.auth.entities.Users;

import java.lang.reflect.MalformedParametersException;
import java.util.Date;
import java.util.List;

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
	UsersDAOImpl usersDAO;

	@Autowired
	TokenDAOImpl tokenDAO;

	/**
	 * Get request of root mapping maps to this method
	 *
	 * @return list of all persisted users
	 * @throws NullPointerException
	 * @request GET http://139.28.37.150:8888/
	 */
	@GetMapping
	public List<Users> getAllUsers() throws NullPointerException {
		return usersDAO.getAll();
	}

	/**
	 * Get request with param of email (not path variable!)
	 * @param email email of searched user
	 * @return searched user
	 * @throws MalformedParametersException
	 * @throws NotFoundException
	 * @request GET http://139.28.37.150:8888/?email={email}
	 */

	@GetMapping(params = "email")
	public Users getUserByEmail(@RequestParam(value = "email") String email) throws MalformedParametersException, NotFoundException {
		return usersDAO.getUserByEmail(email);
	}

	/**
	 * Get request with searched user id path variable
	 * @param id id of searched user
	 * @return searched user
	 * @throws NotFoundException
	 * @request GET http://139.28.37.150:8888/{id}
	 */

	@GetMapping(path = "/{id}")
	public Users getUserById(@PathVariable(value = "id") Long id) throws NotFoundException {
		return usersDAO.getUserById(id);
	}

	/**
	 * Post request with body of json of user to sign up (persist)
	 *
	 * @param user user to persist
	 * @return id of new persisted role
	 * @throws MalformedParametersException
	 * @request POST http://139.28.37.150:8888/signup
	 */

	@PostMapping(value = "/signup")
	@ResponseStatus(HttpStatus.CREATED)
	public Long signUp(@RequestBody Users user) throws MalformedParametersException {
		return usersDAO.signUp(user);
	}

	/**
	 * Put request to update existing user
	 * id should be mentioned in request body, in other new user wil be created
	 * @param userToUpdate user to update
	 * @throws MalformedParametersException
	 * @request PUT http://139.28.37.150:8888/update
	 */

	@PutMapping(value = "/update")
	@ResponseStatus(HttpStatus.OK)
	public void update(@RequestBody Users userToUpdate) throws MalformedParametersException {
		usersDAO.update(userToUpdate);
	}

	/**
	 * Delete request to delete existing user
	 *
	 * @param id id of user to delete (in path variable)
	 * @throws NotFoundException
	 * @request DELETE http://139.28.37.150:8888/{id}
	 */

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.FOUND)
	public void delete(@PathVariable(value = "id") Long id) throws NotFoundException {
		usersDAO.delete(id);
	}

	/**
	 * signout succsess url
	 * invalidates token which was used to perform operations
	 * @param token Token of user, which should be invalidated
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
	 * endpoint to refresh old token to avoid users logout during work
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
			e.printStackTrace();
		}
		return new ResponseEntity(HttpStatus.OK);
	}

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleAppException(NotFoundException e) {
		return e.getMessage();
	}

	@ExceptionHandler(MalformedParametersException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String handleAppException(MalformedParametersException e) {
		return e.getMessage();
	}

	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleAppException(NullPointerException e) {
		return e.getMessage();
	}

	@ExceptionHandler(InternalError.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String handleAppException(InternalError error) {
		return error.getMessage();
	}
}
