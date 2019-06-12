package ua.edu.viti.medex.main.dao;

import javassist.NotFoundException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.viti.medex.main.dao.interfaces.IPatientDAO;
import ua.edu.viti.medex.main.entities.humans.Patient;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.MalformedParametersException;
import java.util.EmptyStackException;
import java.util.List;

/**
 * @author Ihor Dovhoshliubnyi
 * Service implemantaion for patient persistent and management
 * Uses Hibernates session factory for transaction to DB
 */

@Transactional(transactionManager = "mainHibernateTransactionManager", value = "mainHibernateTransactionManager")
@Service
public class PatientDAOImpl implements IPatientDAO {

	@Autowired
	@Qualifier("mainSessionFactory")
	SessionFactory mainSessionFactory;

	@Autowired
	PersonDAOImpl personDAO;

	@Autowired
	FacultyDAOImpl facultyDAO;

	@Override
	public List<Patient> getAllPatients() throws EmptyStackException {
		CriteriaBuilder cb = mainSessionFactory.getCriteriaBuilder();
		CriteriaQuery<Patient> cq = cb.createQuery(Patient.class);
		Root<Patient> root = cq.from(Patient.class);
		cq.select(root);
		Query query = mainSessionFactory.getCurrentSession().createQuery(cq);
		List<Patient> searchedRolesList = query.getResultList();
		if ((searchedRolesList != null) || searchedRolesList.size() != 0) {
			return searchedRolesList;
		} else {
			throw new EmptyStackException();
		}
	}

	@Override
	public Patient getPatientByEmail(String email) throws MalformedParametersException, NotFoundException {
		return mainSessionFactory.getCurrentSession().get(Patient.class, personDAO.getPersonByEmail(email).getId());
	}

	@Override
	public Long signUpPatient(Patient patient) throws MalformedParametersException {
		personDAO.signUpPerson(patient.getPerson());
		mainSessionFactory.getCurrentSession().persist(patient);
		return patient.getPerson().getId();
	}
}
