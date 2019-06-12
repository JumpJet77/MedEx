package ua.edu.viti.medex.main.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.viti.medex.main.dao.interfaces.IExaminationDataDAO;
import ua.edu.viti.medex.main.entities.data.ExaminationData;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.EmptyStackException;
import java.util.List;

@Transactional(value = "mainHibernateTransactionManager", transactionManager = "mainHibernateTransactionManager")
@Service
public class ExaminationDataDAOImpl implements IExaminationDataDAO {

	@Autowired
	@Qualifier("mainSessionFactory")
	SessionFactory mainSessionFactory;

	@Override
	public List<ExaminationData> getAllExaminationDataByPatient(Long id) {
		CriteriaBuilder cb = mainSessionFactory.getCriteriaBuilder();
		CriteriaQuery<ExaminationData> cq = cb.createQuery(ExaminationData.class);
		Root<ExaminationData> root = cq.from(ExaminationData.class);
		cq.select(root).where(cb.equal(root.get("patient"), id));
		Query query = mainSessionFactory.getCurrentSession().createQuery(cq);
		List<ExaminationData> searchedRolesList = query.getResultList();
		if ((searchedRolesList != null) || searchedRolesList.size() != 0) {
			return searchedRolesList;
		} else {
			throw new EmptyStackException();
		}
	}

	@Override
	public List<ExaminationData> getAllExaminationDataByPatient(String email) {
		CriteriaBuilder cb = mainSessionFactory.getCriteriaBuilder();
		CriteriaQuery<ExaminationData> cq = cb.createQuery(ExaminationData.class);
		Root<ExaminationData> root = cq.from(ExaminationData.class);
		cq.select(root).where(cb.equal(root.get("patient").get("email"), email));
		Query query = mainSessionFactory.getCurrentSession().createQuery(cq);
		List<ExaminationData> searchedRolesList = query.getResultList();
		if ((searchedRolesList != null) || searchedRolesList.size() != 0) {
			return searchedRolesList;
		} else {
			throw new EmptyStackException();
		}
	}

	@Override
	public Long addExaminationData(ExaminationData examinationData) {
		mainSessionFactory.getCurrentSession().persist(examinationData);
		return examinationData.getId();
	}
}
