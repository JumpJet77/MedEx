package ua.edu.viti.medex.main.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.viti.medex.main.dao.interfaces.IUnitDAO;
import ua.edu.viti.medex.main.entities.departments.Unit;

@Transactional(value = "mainHibernateTransactionManager", transactionManager = "mainHibernateTransactionManager")
@Service
public class UnitDAOImpl implements IUnitDAO {

	private final Logger logger = LogManager.getLogger(UnitDAOImpl.class);

	@Autowired
	@Qualifier("mainSessionFactory")
	SessionFactory mainSessionFactory;

	@Override
	public Unit getUnitById(Long id) {
		return mainSessionFactory.getCurrentSession().get(Unit.class, id);
	}
}
