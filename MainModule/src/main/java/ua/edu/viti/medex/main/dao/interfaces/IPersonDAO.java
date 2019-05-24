package ua.edu.viti.medex.main.dao.interfaces;

import javassist.NotFoundException;
import ua.edu.viti.medex.main.entities.humans.Person;

import java.lang.reflect.MalformedParametersException;
import java.util.EmptyStackException;
import java.util.List;

/**
 * @author Ihor Dovhoshliubnyi
 * Interface for persons persisting and management
 */

public interface IPersonDAO {

	List<Person> getAllPersons() throws EmptyStackException;

	Person getPersonByEmail(String email) throws MalformedParametersException, NotFoundException;

	Person getPersonById(Long id) throws NotFoundException;

	Long signUpPerson(Person person) throws MalformedParametersException;

	void update(Person personToUpdate) throws MalformedParametersException;

	void delete(Long id) throws NotFoundException;

}
