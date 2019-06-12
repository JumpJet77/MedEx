package ua.edu.viti.medex.main.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.viti.medex.main.dao.interfaces.IVaccinationDAO;
import ua.edu.viti.medex.main.entities.data.Vaccination;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.EmptyStackException;
import java.util.List;

@Transactional(value = "mainHibernateTransactionManager", transactionManager = "mainHibernateTransactionManager")
@Service
public class VaccinationDAOImpl implements IVaccinationDAO {

	@Autowired
	@Qualifier("mainSessionFactory")
	SessionFactory mainSessionFactory;


	@Override
	public List<Vaccination> getAllVaccinationsByPatient(Long id) {
		CriteriaBuilder cb = mainSessionFactory.getCriteriaBuilder();
		CriteriaQuery<Vaccination> cq = cb.createQuery(Vaccination.class);
		Root<Vaccination> root = cq.from(Vaccination.class);
		cq.select(root).where(cb.equal(root.get("patient"), id));
		Query query = mainSessionFactory.getCurrentSession().createQuery(cq);
		List<Vaccination> searchedRolesList = query.getResultList();
		if ((searchedRolesList != null) || searchedRolesList.size() != 0) {
			return searchedRolesList;
		} else {
			throw new EmptyStackException();
		}
	}

	@Override
	public List<Vaccination> getAllVaccinationsByPatient(String email) {
		CriteriaBuilder cb = mainSessionFactory.getCriteriaBuilder();
		CriteriaQuery<Vaccination> cq = cb.createQuery(Vaccination.class);
		Root<Vaccination> root = cq.from(Vaccination.class);
		cq.select(root).where(cb.equal(root.get("patient").get("email"), email));
		Query query = mainSessionFactory.getCurrentSession().createQuery(cq);
		List<Vaccination> searchedRolesList = query.getResultList();
		if ((searchedRolesList != null) || searchedRolesList.size() != 0) {
			return searchedRolesList;
		} else {
			throw new EmptyStackException();
		}
	}

	@Override
	public Long addVaccination(Vaccination vaccination) {
		mainSessionFactory.getCurrentSession().persist(vaccination);
		return vaccination.getId();
	}
}
