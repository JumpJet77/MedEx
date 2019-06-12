package ua.edu.viti.medex.main.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.viti.medex.main.dao.interfaces.IAppealDAO;
import ua.edu.viti.medex.main.entities.data.Appeal;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.EmptyStackException;
import java.util.List;

@Transactional(value = "mainHibernateTransactionManager", transactionManager = "mainHibernateTransactionManager")
@Service
public class AppealDAOImpl implements IAppealDAO {

	@Autowired
	@Qualifier("mainSessionFactory")
	SessionFactory mainSessionFactory;

	@Autowired
	DoctorDAOImpl doctorDAO;

	@Override
	public List<Appeal> getAllAppeals() {
		CriteriaBuilder cb = mainSessionFactory.getCriteriaBuilder();
		CriteriaQuery<Appeal> cq = cb.createQuery(Appeal.class);
		Root<Appeal> root = cq.from(Appeal.class);
		cq.select(root);
		Query query = mainSessionFactory.getCurrentSession().createQuery(cq);
		List<Appeal> searchedRolesList = query.getResultList();
		if ((searchedRolesList != null) || searchedRolesList.size() != 0) {
			return searchedRolesList;
		} else {
			throw new EmptyStackException();
		}
	}

	@Override
	public List<Appeal> getAllAppealsByPatient(Long id) {
		CriteriaBuilder cb = mainSessionFactory.getCriteriaBuilder();
		CriteriaQuery<Appeal> cq = cb.createQuery(Appeal.class);
		Root<Appeal> root = cq.from(Appeal.class);
		cq.select(root).where(cb.equal(root.get("patient"), id));
		Query query = mainSessionFactory.getCurrentSession().createQuery(cq);
		List<Appeal> searchedRolesList = query.getResultList();
		if ((searchedRolesList != null) || searchedRolesList.size() != 0) {
			return searchedRolesList;
		} else {
			throw new EmptyStackException();
		}
	}

	@Override
	public List<Appeal> getAllAppealsByPatient(String email) {
		CriteriaBuilder cb = mainSessionFactory.getCriteriaBuilder();
		CriteriaQuery<Appeal> cq = cb.createQuery(Appeal.class);
		Root<Appeal> root = cq.from(Appeal.class);
		cq.select(root).where(cb.equal(root.get("patient").get("email"), email));
		Query query = mainSessionFactory.getCurrentSession().createQuery(cq);
		List<Appeal> searchedRolesList = query.getResultList();
		if ((searchedRolesList != null) || searchedRolesList.size() != 0) {
			return searchedRolesList;
		} else {
			throw new EmptyStackException();
		}
	}

	@Override
	public Long addAppeal(Appeal appeal, Long idDoctor) {
		doctorDAO.getDoctorById(idDoctor).getAppeals().add(appeal);
		mainSessionFactory.getCurrentSession().persist(appeal);
		return appeal.getId();
	}

	@Override
	public Long addAppeal(Appeal appeal, String emailDoctor) {
		doctorDAO.getDoctorByEmail(emailDoctor).getAppeals().add(appeal);
		mainSessionFactory.getCurrentSession().persist(appeal);
		return appeal.getId();
	}
}
