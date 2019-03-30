package ua.edu.viti.medex.auth.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.edu.viti.medex.auth.Users;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

//TODO: add error handlind (baeldung article)

@RestController
@RequestMapping("/")
public class AuthController {

	private Logger logger = LogManager.getLogger(AuthController.class);

	@Autowired
	private SessionFactory sessionFactory;

	@GetMapping
	public List<Users> findAll() {
		List<Users> users = sessionFactory.getCurrentSession().createQuery("from users").list();
		logger.error(sessionFactory);
		return users;
	}

	@GetMapping(params = "email")
	public Users findByEmail(@RequestParam(value = "email") String email) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Users> criteria = builder.createQuery(Users.class);

		Root<Users> myObjectRoot = criteria.from(Users.class);

		Predicate likeRestriction = builder.and(
				builder.like(myObjectRoot.get("email"), email)
		);
		criteria.select(myObjectRoot).where(likeRestriction);

		TypedQuery<Users> typedQuery = sessionFactory.getCurrentSession().createQuery(criteria);
		Users user = typedQuery.getSingleResult();
		logger.error(user);
		return user;
	}

	@GetMapping(params = "id")
	public Users findById(@RequestParam(value = "id") Long id) {
		return sessionFactory.getCurrentSession().get(Users.class, id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Long create(@RequestBody Users userToCreate) {
		return null;
	}

	@PutMapping(params = "id")
	@ResponseStatus(HttpStatus.OK)
	public void update(@RequestParam(value = "id") Long id, @RequestBody Users userToUpdate) {
	}

	@DeleteMapping(params = "id")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@RequestParam(value = "id") Long id) {
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public class BadRequestException extends RuntimeException {
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	public class NotFoundException extends RuntimeException {
	}
}
