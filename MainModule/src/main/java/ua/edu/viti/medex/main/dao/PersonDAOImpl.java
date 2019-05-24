package ua.edu.viti.medex.main.dao;

import javassist.NotFoundException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.edu.viti.medex.main.dao.interfaces.IPersonDAO;
import ua.edu.viti.medex.main.entities.humans.Person;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.lang.reflect.MalformedParametersException;
import java.util.EmptyStackException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ihor Dovhoshliubnyi
 * Service implemantaion for person persistent and management
 * Uses Hibernates session factory for transaction to DB
 */


@Transactional
@Service
public class PersonDAOImpl implements IPersonDAO {

	@Autowired
	@Qualifier("mainSessionFactory")
	SessionFactory mainSessionFactory;

	@Override
	public List<Person> getAllPersons() throws EmptyStackException {
		CriteriaBuilder cb = mainSessionFactory.getCriteriaBuilder();
		CriteriaQuery<Person> cquery = cb.createQuery(Person.class);
		Root<Person> root = cquery.from(Person.class);
		cquery.select(root);
		Query patientQuery = mainSessionFactory.getCurrentSession().createQuery(cquery);

		List<Person> searchedList = patientQuery.getResultList();
		if ((searchedList != null) || searchedList.size() != 0) {
			return searchedList;
		} else {
			throw new EmptyStackException();
		}
	}

	/**
	 * find person by email with hibernate criteria query
	 *
	 * @param email email of person
	 * @return searched person
	 * @throws MalformedParametersException in case of malformed email
	 */
	@Override
	public Person getPersonByEmail(String email) throws MalformedParametersException, NotFoundException {
		Matcher matcher = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(email);
		if (matcher.find()) {
			CriteriaBuilder cb = mainSessionFactory.getCriteriaBuilder();
			CriteriaQuery<Person> criteria = cb.createQuery(Person.class);
			Root<Person> root = criteria.from(Person.class);
			Predicate likeRestriction = cb.and(
					cb.like(root.get("email"), email)
			);
			criteria.select(root).where(likeRestriction);
			TypedQuery<Person> typedQuery = mainSessionFactory.getCurrentSession().createQuery(criteria);
			return typedQuery.getSingleResult();
		} else {
			throw new MalformedParametersException("Wrong email!");
		}
	}

	@Override
	public Person getPersonById(Long id) throws NotFoundException {
		Person person = mainSessionFactory.getCurrentSession().get(Person.class, id);
		if (person == null) {
			throw new NotFoundException("Person not found");
		} else {
			return person;
		}
	}

	/**
	 * signing up person then persists person
	 * method persists person in database
	 * encrypts password with Spring Security BCrypt password encoder
	 *
	 * @param person person to sign up
	 * @return id of signed up person or -1 if person with such email already exists
	 * @throws MalformedParametersException in case of wrong email pattern
	 */

	@Override
	public Long signUpPerson(Person person) throws MalformedParametersException {
		Matcher matcher = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(person.getEmail());
		try {
			getPersonByEmail(person.getEmail());
		} catch (Exception e) {
			if (person.getEmail() != null && matcher.find()) {
				String unEcrPass = person.getPassword();
				person.setPassword(new BCryptPasswordEncoder().encode(unEcrPass));
				mainSessionFactory.getCurrentSession().persist(person);
				return person.getId();
			} else {
				throw new MalformedParametersException("Wrong email!");
			}
		}
		return -1L;
	}

	@Override
	public void update(Person personToUpdate) throws MalformedParametersException {

	}

	@Override
	public void delete(Long id) throws NotFoundException {

	}
}
