package ua.edu.viti.medex.auth.service;

import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.viti.medex.auth.dao.UserDAOImpl;
import ua.edu.viti.medex.auth.dao.Users;

import java.lang.reflect.MalformedParametersException;
import java.util.EmptyStackException;
import java.util.List;

@Service
public class UserServiceImpl implements IUsersService {
	@Autowired
	UserDAOImpl userDAO;
	private Logger logger = LogManager.getLogger(UserServiceImpl.class);

	@Override
	@Transactional("hibernateTransactionManager")
	public List<Users> findAll() throws EmptyStackException {
		return userDAO.findAll();
	}

	@Override
	@Transactional("hibernateTransactionManager")
	public Users findByEmail(String email) throws MalformedParametersException {
		return userDAO.findByEmail(email);
	}

	@Override
	@Transactional("hibernateTransactionManager")
	public Users findById(Long id) throws NotFoundException {
		return userDAO.findById(id);
	}

	@Override
	@Transactional("hibernateTransactionManager")
	public Long signUp(Users user) throws MalformedParametersException {
		userDAO.signUp(user);
		return user.getId();
	}

	@Override
	@Transactional("hibernateTransactionManager")
	public void update(Users userToUpdate) throws MalformedParametersException {
		userDAO.update(userToUpdate);
	}

	@Override
	@Transactional("hibernateTransactionManager")
	public void delete(Long id) throws NotFoundException {
		userDAO.delete(id);
	}
}
