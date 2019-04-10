package ua.edu.viti.medex.auth.controller;

import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.edu.viti.medex.auth.dao.Users;
import ua.edu.viti.medex.auth.service.UserServiceImpl;

import java.lang.reflect.MalformedParametersException;
import java.util.EmptyStackException;
import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@RestController
@CrossOrigin
@RequestMapping(value = "/")
public class AuthController {

	private Logger logger = LogManager.getLogger(AuthController.class);

	@Autowired
	UserServiceImpl userService;

	/**
	 * Get request of root mapping maps to this method
	 *
	 * @return list of all persisted users
	 * @request GET http://139.28.37.150:80/
	 */
	@GetMapping
	public List<Users> findAll() {
		try {
			return userService.findAll();
		} catch (EmptyStackException e) {
			logger.error("Empty list!");
			return null;
		}
	}

	/**
	 * Get request with param of email (not path variable!)
	 * @param email email of searched user
	 * @return searched user
	 * @request GET http://139.28.37.150:80/?email={email}
	 */

	@GetMapping(params = "email")
	public Users findByEmail(@RequestParam(value = "email") String email) {
		try {
			return userService.findByEmail(email);
		} catch (MalformedParametersException e) {
			logger.error("Malformed email: " + email);
			return null;
		}
	}

	/**
	 * Get request with searched user id path variable
	 * @param id id of searched user
	 * @return searched user
	 * @request GET http://139.28.37.150:80/?id={id}
	 */

	@GetMapping(params = "id")
	public Users findById(@RequestParam(value = "id") Long id) {
		try {
			return userService.findById(id);
		} catch (NotFoundException e) {
			logger.error("User with id: " + id + " not found!");
			return null;
		}
	}

	/**
	 * Post request with body of json of user to sign up (persist)
	 *
	 * @param userToCreate user to persist
	 * @return id of new persisted user
	 * @request POST http://139.28.37.150:80/signup
	 */

	@PostMapping(value = "/signup")
	@ResponseStatus(HttpStatus.CREATED)
	public Long signUp(@RequestBody Users userToCreate) {
		try {
			return userService.signUp(userToCreate);
		} catch (MalformedParametersException e) {
			logger.error("Malformed email: " + userToCreate.getEmail());
			return -1L;
		}
	}

	/**
	 * Put request to update existing user
	 * id should be mentioned in request body, in other new user wil be created
	 * @param userToUpdate user to update
	 * @request PUT http://139.28.37.150:80/update
	 */

	@PutMapping(value = "/update")
	@ResponseStatus(HttpStatus.OK)
	public void update(@RequestBody Users userToUpdate) {
		try {
			userService.update(userToUpdate);
		} catch (MalformedParametersException e) {
			logger.error("Malformed email: " + userToUpdate.getEmail());
		}

	}

	/**
	 * Delete request to delete existing user
	 *
	 * @param id id of user to delete (in path variable)
	 * @request DELETE http://139.28.37.150:80/{id}
	 */

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.FOUND)
	public void delete(@PathVariable(value = "id") Long id) {
		try {
			userService.delete(id);
		} catch (NotFoundException e) {
			logger.error("User with id: " + id + " not found!");
		}
	}
}
