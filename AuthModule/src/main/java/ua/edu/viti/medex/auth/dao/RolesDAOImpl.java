package ua.edu.viti.medex.auth.dao;

import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.edu.viti.medex.auth.entities.Role;
import ua.edu.viti.medex.auth.entities.Roles;
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

@Repository
public class RolesDAOImpl implements IRolesDAO {

	@Autowired
	SessionFactory sessionFactory;

	private Logger logger = LogManager.getLogger(RolesDAOImpl.class);


	/**
	 * Find all persisted roles with hibernate criteria query
	 *
	 * @return list of all roles
	 * @throws EmptyStackException if returned list null or size = 0
	 */

	@Override
	public List<Roles> getAll() throws EmptyStackException {
		CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
		CriteriaQuery<Roles> cq = cb.createQuery(Roles.class);
		Root<Roles> root = cq.from(Roles.class);
		cq.select(root);
		Query query = sessionFactory.getCurrentSession().createQuery(cq);
		List<Roles> searchedRolesList = query.getResultList();
		logger.error(searchedRolesList);
		if ((searchedRolesList != null) || searchedRolesList.size() != 0) {
			return searchedRolesList;
		} else {
			throw new EmptyStackException();
		}
	}

	/**
	 * find role by email with hibernate criteria query
	 *
	 * @param email email of role
	 * @return searched role
	 * @throws MalformedParametersException in case of malformed email
	 */

	@Override
	public Roles getRoleByEmail(String email) throws MalformedParametersException, NotFoundException {
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
			return getRoleById(user.getId());
		} else {
			throw new MalformedParametersException("Wrong email!");
		}
	}

	/**
	 * search role by id
	 *
	 * @param id id of persisted searched role
	 * @return searched persisted role
	 * @throws NotFoundException if role not found
	 */

	@Override
	public Roles getRoleById(Long id) throws NotFoundException {
		Roles roles = sessionFactory.getCurrentSession().get(Roles.class, id);
		if (roles == null) {
			throw new NotFoundException("User not found");
		} else {
			return roles;
		}
	}

	/**
	 * signing up user then persists role
	 * method persists role and user in database
	 *
	 * @param user user to sign up
	 * @param role role which user should have
	 * @return id of signed up role and user
	 * @throws MalformedParametersException in case of wrong email pattern
	 */

	@Override
	public Long signUp(Users user, Role role) throws MalformedParametersException {
		Matcher matcher = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(user.getEmail());
		Roles roles = new Roles();
		if (user.getEmail() != null && matcher.find()) {
			sessionFactory.getCurrentSession().persist(user);
			if (role != null && !role.toString().equals("")) {
				roles.setUser(user);
				roles.setRole(role);
				sessionFactory.getCurrentSession().persist(roles);
			}
			return user.getId();
		} else {
			throw new MalformedParametersException("Wrong email!");
		}
	}

	/**
	 * updating of persisted role
	 *
	 * @param userToUpdate role to update (must be with id in json)
	 * @param roleToUpdate role to update (exactly enum)
	 * @throws MalformedParametersException in case of wrong email pattern
	 */

	@Override
	public void update(Users userToUpdate, Role roleToUpdate) throws MalformedParametersException {
		Matcher matcher = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(userToUpdate.getEmail());
		Roles role = new Roles();
		if (userToUpdate.getEmail() != null && matcher.find()) {
			sessionFactory.getCurrentSession().merge(userToUpdate);
			if (!role.toString().equals("")) {
				role.setUser(userToUpdate);
				role.setRole(roleToUpdate);
				logger.error(userToUpdate + " " + role);
				sessionFactory.getCurrentSession().merge(role);
			}
		} else {
			throw new MalformedParametersException("Wrong email!");
		}
	}

	/**
	 * deleting of persisted role
	 *
	 * @param id id of role to delete
	 * @throws NotFoundException if role not exists
	 */

	@Override
	public void delete(Long id) throws NotFoundException {
		Users userToDelete = sessionFactory.getCurrentSession().get(Users.class, id);
		Roles rolesToDelete = sessionFactory.getCurrentSession().get(Roles.class, id);
		if (userToDelete == null || rolesToDelete == null) {
			throw new NotFoundException("User not found");
		} else {
			sessionFactory.getCurrentSession().delete(rolesToDelete);
			sessionFactory.getCurrentSession().delete(userToDelete);
		}
	}
}
