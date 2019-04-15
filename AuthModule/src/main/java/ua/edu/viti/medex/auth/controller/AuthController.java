package ua.edu.viti.medex.auth.controller;

import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.viti.medex.auth.entities.Roles;
import ua.edu.viti.medex.auth.service.RolesServiceImpl;

import java.lang.reflect.MalformedParametersException;
import java.util.List;

//TODO: (in future) add more detailed exception handlers

@SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection", "JavaDoc"})
@RestController
@CrossOrigin
@RequestMapping(value = "/")
public class AuthController {

	private Logger logger = LogManager.getLogger(AuthController.class);

	@Autowired
	RolesServiceImpl rolesService;

	/**
	 * Get request of root mapping maps to this method
	 *
	 * @return list of all persisted roles
	 * @throws NullPointerException
	 * @request GET http://139.28.37.150:8888/
	 */
	@GetMapping
	public List<Roles> getAllRoles() throws NullPointerException {
		return rolesService.getAll();
	}

	/**
	 * Get request with param of email (not path variable!)
	 * @param email email of searched role
	 * @return searched role
	 * @throws MalformedParametersException
	 * @throws NotFoundException
	 * @request GET http://139.28.37.150:8888/?email={email}
	 */

	@GetMapping(params = "email")
	public Roles getRoleByEmail(@RequestParam(value = "email") String email) throws MalformedParametersException, NotFoundException {
		return rolesService.getRoleByEmail(email);
	}

	/**
	 * Get request with searched role id path variable
	 * @param id id of searched role
	 * @return searched role
	 * @throws NotFoundException
	 * @request GET http://139.28.37.150:8888/{id}
	 */

	@GetMapping(path = "/{id}")
	public Roles getRoleById(@PathVariable(value = "id") Long id) throws NotFoundException {
		return rolesService.getRoleById(id);
	}

	/**
	 * Post request with body of json of role to sign up (persist)
	 *
	 * @param roles role to persist
	 * @return id of new persisted role
	 * @throws MalformedParametersException
	 * @request POST http://139.28.37.150:8888/signup
	 */

	@PostMapping(value = "/signup")
	@ResponseStatus(HttpStatus.CREATED)
	public Long signUp(@RequestBody Roles roles) throws MalformedParametersException {
		return rolesService.signUp(roles.getUser(), roles.getRole());
	}

	/**
	 * Put request to update existing role
	 * id should be mentioned in request body, in other new role wil be created
	 * @param roleToUpdate role to update
	 * @throws MalformedParametersException
	 * @request PUT http://139.28.37.150:8888/update
	 */

	@PutMapping(value = "/update")
	@ResponseStatus(HttpStatus.OK)
	public void update(@RequestBody Roles roleToUpdate) throws MalformedParametersException {
		rolesService.update(roleToUpdate.getUser(), roleToUpdate.getRole());
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
		rolesService.delete(id);
	}

	/**
	 * signout succsess url
	 *
	 * @return OK response, as /logout reditrects to this mapping
	 * @request GET http://139.28.37.150:8888/logout
	 */
	@GetMapping(value = "/signout")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> logout() {
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
