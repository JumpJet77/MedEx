package ua.edu.viti.medex.main.controller;

import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.edu.viti.medex.main.dao.PatientDAOImpl;
import ua.edu.viti.medex.main.dao.PersonDAOImpl;
import ua.edu.viti.medex.main.entities.humans.Patient;
import ua.edu.viti.medex.main.entities.humans.Person;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {
		RequestMethod.HEAD,
		RequestMethod.DELETE,
		RequestMethod.GET,
		RequestMethod.OPTIONS,
		RequestMethod.POST,
		RequestMethod.PUT,
		RequestMethod.TRACE,
		RequestMethod.PATCH
})
@RequestMapping(value = "/")
public class MainController {

	private final Logger logger = LogManager.getLogger(MainController.class);

	@Autowired
	PersonDAOImpl personDAO;

	@Autowired
	PatientDAOImpl patientDAO;

	@PostMapping(value = "/signUpPerson")
	@ResponseStatus(HttpStatus.CREATED)
	public Long signUpPerson(@RequestBody Person person) {
		return personDAO.signUpPerson(person);
	}

	@GetMapping(value = "/person", params = "email")
	@ResponseStatus(HttpStatus.FOUND)
	public Person getPersonByEmail(@RequestParam(value = "email") String email) {
		try {
			return personDAO.getPersonByEmail(email);
		} catch (NotFoundException e) {
			logger.error("Person with email: " + email + " not found!");
		}
		return null;
	}

	@PostMapping(value = "/signUpPatient")
	@ResponseStatus(HttpStatus.CREATED)
	public Long signUpPatient(@RequestBody Patient patient) {
		return patientDAO.signUpPatient(patient);
	}

	@GetMapping(value = "/patient", params = "email")
	@ResponseStatus(HttpStatus.OK)
	public Patient getPatientByEmail(@RequestParam(value = "email") String email) {
		try {
			return patientDAO.getPatientByEmail(email);
		} catch (NotFoundException e) {
			logger.error("Patient with email: " + email + " not found!");
		}
		return null;
	}

	@GetMapping(value = "/patients")
	@ResponseStatus(HttpStatus.OK)
	public List<Patient> getAllPatients() {
		return patientDAO.getAllPatients();
	}

}