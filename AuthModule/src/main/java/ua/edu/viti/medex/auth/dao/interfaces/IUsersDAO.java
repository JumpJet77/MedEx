package ua.edu.viti.medex.auth.dao.interfaces;

import javassist.NotFoundException;
import ua.edu.viti.medex.auth.entities.Users;

import java.lang.reflect.MalformedParametersException;
import java.util.EmptyStackException;
import java.util.List;

/**
 * @author Ihor Dovhoshliubnyi
 * Interface for Users persisting and management
 */

public interface IUsersDAO {

	List<Users> getAll() throws EmptyStackException;

	Users getUserByEmail(String email) throws MalformedParametersException, NotFoundException;

	Users getUserById(Long id) throws NotFoundException;

	Long signUp(Users user) throws MalformedParametersException;

	void update(Users userToUpdate) throws MalformedParametersException;

	void delete(Long id) throws NotFoundException;
}
