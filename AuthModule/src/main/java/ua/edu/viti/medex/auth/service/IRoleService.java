package ua.edu.viti.medex.auth.service;

import javassist.NotFoundException;
import ua.edu.viti.medex.auth.entities.Roles;
import ua.edu.viti.medex.auth.entities.Users;
import ua.edu.viti.medex.auth.entities.enums.Role;

import java.lang.reflect.MalformedParametersException;
import java.util.EmptyStackException;
import java.util.List;

public interface IRoleService {

	List<Roles> getAll() throws EmptyStackException;

	Roles getRoleByEmail(String email) throws MalformedParametersException, NotFoundException;

	Roles getRoleById(Long id) throws NotFoundException;

	Long signUp(Users user, Role role) throws MalformedParametersException;

	void update(Users userToUpdate, Role roleToUpdate) throws MalformedParametersException;

	void delete(Long id) throws NotFoundException;
}
