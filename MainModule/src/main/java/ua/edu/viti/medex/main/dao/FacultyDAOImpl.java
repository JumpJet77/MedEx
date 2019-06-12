package ua.edu.viti.medex.main.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.viti.medex.main.dao.interfaces.IFacultyDAO;
import ua.edu.viti.medex.main.entities.departments.Faculty;
import ua.edu.viti.medex.main.entities.humans.Patient;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Transactional(value = "mainHibernateTransactionManager", transactionManager = "mainHibernateTransactionManager")
@Service
public class FacultyDAOImpl implements IFacultyDAO {

	@Autowired
	@Qualifier("mainSessionFactory")
	SessionFactory mainSessionFactory;

	@Autowired
	PatientDAOImpl patientDAO;

	@Autowired
	DoctorDAOImpl doctorDAO;

	@Override
	public Faculty getFacultyById(Long id) {
		return mainSessionFactory.getCurrentSession().get(Faculty.class, id);
	}

	@Override
	public Long addPatient(Patient patient, Long idFaculty) {
		patientDAO.signUpPatient(patient);
		getFacultyById(idFaculty).getPatients().add(patient.getPerson());
		return patient.getPerson().getId();
	}

	@Override
	public Faculty getFacultyByDoctorId(Long id) {
		CriteriaBuilder cb = mainSessionFactory.getCriteriaBuilder();
		CriteriaQuery<Faculty> criteria = cb.createQuery(Faculty.class);
		Root<Faculty> root = criteria.from(Faculty.class);
		criteria.select(root).where(cb.equal(root.get("doctor"), id));
		TypedQuery<Faculty> typedQuery = mainSessionFactory.getCurrentSession().createQuery(criteria);
		return typedQuery.getSingleResult();
	}

	@Override
	public Faculty getFacultyByDoctorEmail(String email) {
		CriteriaBuilder cb = mainSessionFactory.getCriteriaBuilder();
		CriteriaQuery<Faculty> criteria = cb.createQuery(Faculty.class);
		Root<Faculty> root = criteria.from(Faculty.class);
		criteria.select(root).where(cb.equal(root.get("doctor").get("email"), email));
		TypedQuery<Faculty> typedQuery = mainSessionFactory.getCurrentSession().createQuery(criteria);
		return typedQuery.getSingleResult();
	}


}
