package ua.edu.viti.medex.auth.service;

import javassist.NotFoundException;
import ua.edu.viti.medex.auth.dao.Users;

import java.lang.reflect.MalformedParametersException;
import java.util.EmptyStackException;
import java.util.List;

public interface IUsersService {

	List<Users> findAll() throws EmptyStackException;

	Users findById(Long id) throws NotFoundException;

	Long signUp(Users user) throws MalformedParametersException;

	Users findByEmail(String email) throws MalformedParametersException;

	void update(Users userToUpdate) throws MalformedParametersException;

	void delete(Long id) throws NotFoundException;

}
