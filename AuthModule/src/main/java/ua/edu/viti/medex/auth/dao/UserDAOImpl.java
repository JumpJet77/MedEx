package ua.edu.viti.medex.auth.dao;

import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.edu.viti.medex.auth.service.IUsersService;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.MalformedParametersException;
import java.util.EmptyStackException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Repository
public class UserDAOImpl implements IUsersService {

	@Autowired
	SessionFactory sessionFactory;
	private Logger logger = LogManager.getLogger(UserDAOImpl.class);

	/**
	 * Find all persisted user with hibernate criteria query
	 *
	 * @return list of all users
	 * @throws EmptyStackException if returned list null or size = 0
	 */

	@Override
	public List<Users> findAll() throws EmptyStackException {
		CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
		CriteriaQuery<Users> cq = cb.createQuery(Users.class);
		Root<Users> root = cq.from(Users.class);
		cq.select(root);
		Query query = sessionFactory.getCurrentSession().createQuery(cq);
		List<Users> searchedUsers = query.getResultList();
		if ((searchedUsers != null) || searchedUsers.size() != 0) {
			return searchedUsers;
		} else {
			throw new EmptyStackException();
		}
	}

	/**
	 * find user by email with hibernate criteria query
	 *
	 * @param email email of user
	 * @return searched user
	 * @throws MalformedParametersException in case of malformed email
	 */

	@Override
	public Users findByEmail(String email) throws MalformedParametersException {
		Matcher matcher = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(email);
		if (matcher.find()) {
			CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
			CriteriaQuery<Users> criteria = cb.createQuery(Users.class);
			Root<Users> root = criteria.from(Users.class);
			Predicate likeRestriction = cb.and(
					cb.like(root.get("email"), email)
			);
			criteria.select(root).where(likeRestriction);

			TypedQuery<Users> typedQuery = sessionFactory.getCurrentSession().createQuery(criteria);
			Users user = typedQuery.getSingleResult();
			return user;
		} else {
			throw new MalformedParametersException("Wrong email!");
		}
	}

	/**
	 * search user by id
	 *
	 * @param id id of persisted searched user
	 * @return searched persisted user
	 * @throws NotFoundException if user not found
	 */

	@Override
	public Users findById(Long id) throws NotFoundException {
		Users user = sessionFactory.getCurrentSession().get(Users.class, id);
		if (user == null) {
			throw new NotFoundException("User not found");
		} else {
			return user;
		}
	}

	/**
	 * signing up user
	 * method persists user in database
	 *
	 * @param user user to sign up
	 * @return id of signed up user
	 * @throws MalformedParametersException in case of wrong email pattern
	 */

	@Override
	public Long signUp(Users user) throws MalformedParametersException {
		Matcher matcher = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(user.getEmail());
		if (user.getEmail() != null && matcher.find()) {
			sessionFactory.getCurrentSession().persist(user);
			return user.getId();
		} else {
			throw new MalformedParametersException("Wrong email!");
		}
	}

	/**
	 * updating of persisted user
	 *
	 * @param userToUpdate user to update (must be with id in json)
	 * @throws MalformedParametersException in case of wrong email pattern
	 */

	@Override
	public void update(Users userToUpdate) throws MalformedParametersException {
		Matcher matcher = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(userToUpdate.getEmail());
		if (userToUpdate.getEmail() != null && matcher.find()) {
			sessionFactory.getCurrentSession().merge(userToUpdate);
		} else {
			throw new MalformedParametersException("Wrong email!");
		}
	}

	/**
	 * deleting of persisted user
	 *
	 * @param id id of user to delete
	 */

	@Override
	public void delete(Long id) throws NotFoundException {
		Users userToDelete = sessionFactory.getCurrentSession().get(Users.class, id);
		if (userToDelete == null) {
			throw new NotFoundException("User not found");
		} else {
			sessionFactory.getCurrentSession().delete(userToDelete);
		}
	}
}
