package ua.edu.viti.medex.auth.dao;

import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.viti.medex.auth.dao.interfaces.IUsersDAO;
import ua.edu.viti.medex.auth.entities.Users;

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

/**
 * @author Ihor Dovhoshliubnyi
 * Service implemantaion for User persistent and management
 * Uses Hibernates session factory for transaction to DB
 */

@Transactional
@Service
public class UsersDAOImpl implements IUsersDAO {

	@Autowired
	SessionFactory sessionFactory;

	private Logger logger = LogManager.getLogger(UsersDAOImpl.class);

	/**
	 * Find all persisted users with hibernate criteria query
	 *
	 * @return list of all users
	 * @throws EmptyStackException if returned list null or size = 0
	 */

	@Override
	public List<Users> getAll() throws EmptyStackException {
		CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
		CriteriaQuery<Users> cq = cb.createQuery(Users.class);
		Root<Users> root = cq.from(Users.class);
		cq.select(root);
		Query query = sessionFactory.getCurrentSession().createQuery(cq);
		List<Users> searchedRolesList = query.getResultList();
		if ((searchedRolesList != null) || searchedRolesList.size() != 0) {
			return searchedRolesList;
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
	public Users getUserByEmail(String email) throws MalformedParametersException, NotFoundException {
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
			return typedQuery.getSingleResult();
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
	public Users getUserById(Long id) throws NotFoundException {
		Users user = sessionFactory.getCurrentSession().get(Users.class, id);
		if (user == null) {
			throw new NotFoundException("User not found");
		} else {
			return user;
		}
	}


	/**
	 * signing up user then persists user
	 * method persists user in database
	 * encrypts password with Spring Security BCrypt password encoder
	 *
	 * @param user user to sign up
	 * @return id of signed up user or -1 if user with such email already exists
	 * @throws MalformedParametersException in case of wrong email pattern
	 */

	@Override
	public Long signUp(Users user) throws MalformedParametersException {
		Matcher matcher = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(user.getEmail());
		try {
			getUserByEmail(user.getEmail());
		} catch (Exception e) {
			if (user.getEmail() != null && matcher.find()) {
				String unEcrPass = user.getPassword();
				user.setPassword(new BCryptPasswordEncoder().encode(unEcrPass));
				sessionFactory.getCurrentSession().persist(user);
				return user.getId();
			} else {
				throw new MalformedParametersException("Wrong email!");
			}
		}
		return -1L;
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
			String unEcrPass = userToUpdate.getPassword();
			userToUpdate.setPassword(new BCryptPasswordEncoder().encode(unEcrPass));
			sessionFactory.getCurrentSession().merge(userToUpdate);
		} else {
			throw new MalformedParametersException("Wrong email!");
		}
	}

	/**
	 * deleting of persisted user
	 *
	 * @param id id of user to delete
	 * @throws NotFoundException if user not exists
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
