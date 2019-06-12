package ua.edu.viti.medex.main.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.viti.medex.main.dao.interfaces.IDantistDataDAO;
import ua.edu.viti.medex.main.entities.data.DantistData;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.EmptyStackException;
import java.util.List;

@Transactional(value = "mainHibernateTransactionManager", transactionManager = "mainHibernateTransactionManager")
@Service
public class DantistDataDAOImpl implements IDantistDataDAO {

	@Autowired
	@Qualifier("mainSessionFactory")
	SessionFactory mainSessionFactory;

	@Override
	public List<DantistData> getAllDantistDataByPatient(Long id) {
		CriteriaBuilder cb = mainSessionFactory.getCriteriaBuilder();
		CriteriaQuery<DantistData> cq = cb.createQuery(DantistData.class);
		Root<DantistData> root = cq.from(DantistData.class);
		cq.select(root).where(cb.equal(root.get("patient"), id));
		Query query = mainSessionFactory.getCurrentSession().createQuery(cq);
		List<DantistData> searchedRolesList = query.getResultList();
		if ((searchedRolesList != null) || searchedRolesList.size() != 0) {
			return searchedRolesList;
		} else {
			throw new EmptyStackException();
		}
	}

	@Override
	public List<DantistData> getAllDantistDataByPatient(String email) {
		CriteriaBuilder cb = mainSessionFactory.getCriteriaBuilder();
		CriteriaQuery<DantistData> cq = cb.createQuery(DantistData.class);
		Root<DantistData> root = cq.from(DantistData.class);
		cq.select(root).where(cb.equal(root.get("patient").get("email"), email));
		Query query = mainSessionFactory.getCurrentSession().createQuery(cq);
		List<DantistData> searchedRolesList = query.getResultList();
		if ((searchedRolesList != null) || searchedRolesList.size() != 0) {
			return searchedRolesList;
		} else {
			throw new EmptyStackException();
		}
	}

	@Override
	public Long addDantistData(DantistData dantistData) {
		mainSessionFactory.getCurrentSession().persist(dantistData);
		return dantistData.getId();
	}
}
