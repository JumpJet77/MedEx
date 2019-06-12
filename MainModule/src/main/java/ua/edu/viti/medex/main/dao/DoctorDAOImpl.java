package ua.edu.viti.medex.main.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.viti.medex.main.dao.interfaces.IDoctorDAO;
import ua.edu.viti.medex.main.entities.humans.Doctor;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.MalformedParametersException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Transactional(value = "mainHibernateTransactionManager", transactionManager = "mainHibernateTransactionManager")
@Service
public class DoctorDAOImpl implements IDoctorDAO {

	@Autowired
	@Qualifier("mainSessionFactory")
	SessionFactory mainSessionFactory;


	@Override
	public Doctor getDoctorById(Long id) {
		return mainSessionFactory.getCurrentSession().get(Doctor.class, id);
	}

	@Override
	public Doctor getDoctorByEmail(String email) {
		Matcher matcher = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(email);
		if (matcher.find()) {
			CriteriaBuilder cb = mainSessionFactory.getCriteriaBuilder();
			CriteriaQuery<Doctor> criteria = cb.createQuery(Doctor.class);
			Root<Doctor> root = criteria.from(Doctor.class);
			Predicate likeRestriction = cb.and(
					cb.like(root.get("person").get("email"), email)
			);
			criteria.select(root).where(likeRestriction);
			TypedQuery<Doctor> typedQuery = mainSessionFactory.getCurrentSession().createQuery(criteria);
			return typedQuery.getSingleResult();
		} else {
			throw new MalformedParametersException("Wrong email!");
		}
	}
}
