package ua.edu.viti.medex.main.dao.interfaces;

import javassist.NotFoundException;
import ua.edu.viti.medex.main.entities.humans.Patient;

import java.lang.reflect.MalformedParametersException;
import java.util.EmptyStackException;
import java.util.List;

/**
 * @author Ihor Dovhoshliubnyi
 * Interface for persons persisting and management
 */

public interface IPatientDAO {

	List<Patient> getAllPatients() throws EmptyStackException;

	Patient getPatientByEmail(String email) throws MalformedParametersException, NotFoundException;

	Long signUpPatient(Patient patient) throws MalformedParametersException;

}
