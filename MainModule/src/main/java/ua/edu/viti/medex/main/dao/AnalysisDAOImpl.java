package ua.edu.viti.medex.main.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.viti.medex.main.dao.interfaces.IAnalysisDAO;
import ua.edu.viti.medex.main.entities.data.Analysis;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.EmptyStackException;
import java.util.List;

@Transactional(value = "mainHibernateTransactionManager", transactionManager = "mainHibernateTransactionManager")
@Service
public class AnalysisDAOImpl implements IAnalysisDAO {

	@Autowired
	@Qualifier("mainSessionFactory")
	SessionFactory mainSessionFactory;

	@Override
	public List<Analysis> getAllAnalysisByPatient(Long id) {
		CriteriaBuilder cb = mainSessionFactory.getCriteriaBuilder();
		CriteriaQuery<Analysis> cq = cb.createQuery(Analysis.class);
		Root<Analysis> root = cq.from(Analysis.class);
		cq.select(root).where(cb.equal(root.get("patient"), id));
		Query query = mainSessionFactory.getCurrentSession().createQuery(cq);
		List<Analysis> searchedRolesList = query.getResultList();
		if ((searchedRolesList != null) || searchedRolesList.size() != 0) {
			return searchedRolesList;
		} else {
			throw new EmptyStackException();
		}
	}

	@Override
	public List<Analysis> getAllAnalysisByPatient(String email) {
		CriteriaBuilder cb = mainSessionFactory.getCriteriaBuilder();
		CriteriaQuery<Analysis> cq = cb.createQuery(Analysis.class);
		Root<Analysis> root = cq.from(Analysis.class);
		cq.select(root).where(cb.equal(root.get("patient").get("email"), email));
		Query query = mainSessionFactory.getCurrentSession().createQuery(cq);
		List<Analysis> searchedRolesList = query.getResultList();
		if ((searchedRolesList != null) || searchedRolesList.size() != 0) {
			return searchedRolesList;
		} else {
			throw new EmptyStackException();
		}
	}

	@Override
	public Long addAnalysis(Analysis analysis) {
		mainSessionFactory.getCurrentSession().persist(analysis);
		return analysis.getId();
	}
}
