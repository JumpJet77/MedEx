package ua.edu.viti.medex.main.dao;

import javassist.NotFoundException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.viti.medex.main.dao.interfaces.IPatientDAO;
import ua.edu.viti.medex.main.entities.humans.Patient;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.lang.reflect.MalformedParametersException;
import java.util.EmptyStackException;
import java.util.List;

/**
 * @author Ihor Dovhoshliubnyi
 * Service implemantaion for patient persistent and management
 * Uses Hibernates session factory for transaction to DB
 */

@Transactional
@Service
public class PatientDAOImpl implements IPatientDAO {

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	PersonDAOImpl personDAO;

	@Override
	public List<Patient> getAllPatients() throws EmptyStackException {
		CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
		CriteriaQuery<Patient> cq = cb.createQuery(Patient.class);
		Root<Patient> root = cq.from(Patient.class);
		cq.select(root);
		Query query = sessionFactory.getCurrentSession().createQuery(cq);
		List<Patient> searchedRolesList = query.getResultList();
		if ((searchedRolesList != null) || searchedRolesList.size() != 0) {
			return searchedRolesList;
		} else {
			throw new EmptyStackException();
		}
	}

	@Override
	public Patient getPatientByEmail(String email) throws MalformedParametersException, NotFoundException {
		return sessionFactory.getCurrentSession().get(Patient.class, personDAO.getPersonByEmail(email).getId());
	}

	@Override
	public Long signUpPatient(Patient patient) throws MalformedParametersException {
		personDAO.signUpPerson(patient.getPerson());
		sessionFactory.getCurrentSession().persist(patient);
		return patient.getPerson().getId();
	}

	@Override
	public void update(Patient patientToUpdate) throws MalformedParametersException {

	}

	@Override
	public void delete(Long id) throws NotFoundException {

	}
}
