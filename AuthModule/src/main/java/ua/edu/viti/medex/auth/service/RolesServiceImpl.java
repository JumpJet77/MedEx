package ua.edu.viti.medex.auth.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.viti.medex.auth.dao.RolesDAOImpl;
import ua.edu.viti.medex.auth.entities.Roles;
import ua.edu.viti.medex.auth.entities.Users;
import ua.edu.viti.medex.auth.entities.enums.Role;

import java.lang.reflect.MalformedParametersException;
import java.util.EmptyStackException;
import java.util.List;

@Service
public class RolesServiceImpl implements IRoleService {

	@Autowired
	RolesDAOImpl rolesDAO;

	@Transactional
	@Override
	public List<Roles> getAll() throws EmptyStackException {
		return rolesDAO.getAll();
	}

	@Transactional
	@Override
	public Roles getRoleByEmail(String email) throws MalformedParametersException, NotFoundException {
		return rolesDAO.getRoleByEmail(email);
	}

	@Transactional
	@Override
	public Roles getRoleById(Long id) throws NotFoundException {
		return rolesDAO.getRoleById(id);
	}

	@Transactional
	@Override
	public Long signUp(Users user, Role role) throws MalformedParametersException {
		return rolesDAO.signUp(user, role);
	}

	@Transactional
	@Override
	public void update(Users userToUpdate, Role roleToUpdate) throws MalformedParametersException {
		rolesDAO.update(userToUpdate, roleToUpdate);
	}

	@Transactional
	@Override
	public void delete(Long id) throws NotFoundException {
		rolesDAO.delete(id);
	}
}
