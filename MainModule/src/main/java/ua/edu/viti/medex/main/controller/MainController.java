package ua.edu.viti.medex.main.controller;

import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.edu.viti.medex.main.dao.*;
import ua.edu.viti.medex.main.entities.data.*;
import ua.edu.viti.medex.main.entities.departments.Faculty;
import ua.edu.viti.medex.main.entities.departments.Unit;
import ua.edu.viti.medex.main.entities.humans.Doctor;
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

	//TODO: rewrite all DAO interfaces with one generic

	private final Logger logger = LogManager.getLogger(MainController.class);

	@Autowired
	PersonDAOImpl personDAO;

	@Autowired
	PatientDAOImpl patientDAO;

	@Autowired
	FacultyDAOImpl facultyDAO;

	@Autowired
	UnitDAOImpl unitDAO;

	@Autowired
	DoctorDAOImpl doctorDAO;

	@Autowired
	AppealDAOImpl appealDAO;

	@Autowired
	DiagnosisDAOImpl diagnosisDAO;

	@Autowired
	AnalysisDAOImpl analysisDAO;

	@Autowired
	VaccinationDAOImpl vaccinationDAO;

	@Autowired
	DantistDataDAOImpl dantistDataDAO;

	@Autowired
	PhysicalDataDAOImpl physicalDataDAO;

	@Autowired
	ExaminationDataDAOImpl examinationDataDAO;

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

	@GetMapping(value = "/faculties/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Faculty getFacultyById(@PathVariable(name = "id") Long id) {
		return facultyDAO.getFacultyById(id);
	}

	@GetMapping(value = "/units/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Unit getUnitById(@PathVariable(name = "id") Long id) {
		Unit unit = unitDAO.getUnitById(id);
		unit.getFaculties().forEach(f -> f.getPatients().clear());
		return unit;
	}

	@PostMapping(value = "/faculties/{id}/add")
	@ResponseStatus(HttpStatus.OK)
	public Long addPatient(@PathVariable(name = "id") Long id, @RequestBody Patient patient) {
		return facultyDAO.addPatient(patient, id);
	}

	@GetMapping(value = "/doctor/faculty/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Faculty getFacultyByDoctorId(@PathVariable(name = "id") Long id) {
		return facultyDAO.getFacultyByDoctorId(id);
	}

	@GetMapping(value = "/doctor/faculty/", params = "email")
	@ResponseStatus(HttpStatus.OK)
	public Faculty getFacultyByDoctorEmail(@RequestParam(name = "email") String email) {
		return facultyDAO.getFacultyByDoctorEmail(email);
	}

	@GetMapping(value = "/doctor/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Doctor getDoctorById(@PathVariable(name = "id") Long id) {
		return doctorDAO.getDoctorById(id);
	}

	@GetMapping(value = "/doctor/", params = "email")
	@ResponseStatus(HttpStatus.OK)
	public Doctor getDoctorByEmail(@RequestParam String email) {
		return doctorDAO.getDoctorByEmail(email);
	}

	@GetMapping(value = "/appeals")
	@ResponseStatus(HttpStatus.OK)
	public List<Appeal> getAllAppeals() {
		return appealDAO.getAllAppeals();
	}

	@GetMapping(value = "/appeals/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<Appeal> getAllAppealsByPatient(@PathVariable Long id) {
		return appealDAO.getAllAppealsByPatient(id);
	}

	@GetMapping(value = "/appeals/", params = "email")
	@ResponseStatus(HttpStatus.OK)
	public List<Appeal> getAllAppealsByPatient(@RequestParam String email) {
		return appealDAO.getAllAppealsByPatient(email);
	}

	@PostMapping(value = "/appeals/add/{idDoctor}")
	@ResponseStatus(HttpStatus.OK)
	public Long addAppeal(@RequestBody Appeal appeal, @PathVariable Long idDoctor) {
		return appealDAO.addAppeal(appeal, idDoctor);
	}

	@PostMapping(value = "/appeals/add/", params = "email")
	@ResponseStatus(HttpStatus.OK)
	public Long addAppeal(@RequestBody Appeal appeal, @RequestParam String email) {
		return appealDAO.addAppeal(appeal, email);
	}

	@GetMapping(value = "/diagnosis/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<Diagnosis> getAllDiagnosisByPatient(@PathVariable Long id) {
		return diagnosisDAO.getAllDiagnosisByPatient(id);
	}

	@GetMapping(value = "/diagnosis/", params = "email")
	@ResponseStatus(HttpStatus.OK)
	public List<Diagnosis> getAllDiagnosisByPatient(@RequestParam String email) {
		return diagnosisDAO.getAllDiagnosisByPatient(email);
	}

	@PostMapping(value = "/diagnosis/add/")
	@ResponseStatus(HttpStatus.OK)
	public Long addDiagnosis(@RequestBody Diagnosis diagnosis) {
		return diagnosisDAO.addDiagnosis(diagnosis);
	}

	@GetMapping(value = "/analysis/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<Analysis> getAllAnalysisByPatient(@PathVariable Long id) {
		return analysisDAO.getAllAnalysisByPatient(id);
	}

	@GetMapping(value = "/analysis/", params = "email")
	@ResponseStatus(HttpStatus.OK)
	public List<Analysis> getAllAnalysisByPatient(@RequestParam String email) {
		return analysisDAO.getAllAnalysisByPatient(email);
	}

	@PostMapping(value = "/analysis/add/")
	@ResponseStatus(HttpStatus.OK)
	public Long addAnalysis(@RequestBody Analysis analysis) {
		return analysisDAO.addAnalysis(analysis);
	}

	@GetMapping(value = "/vaccination/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<Vaccination> getAllVaccinationByPatient(@PathVariable Long id) {
		return vaccinationDAO.getAllVaccinationsByPatient(id);
	}

	@GetMapping(value = "/vaccination/", params = "email")
	@ResponseStatus(HttpStatus.OK)
	public List<Vaccination> getAllVaccinationByPatient(@RequestParam String email) {
		return vaccinationDAO.getAllVaccinationsByPatient(email);
	}

	@PostMapping(value = "/vaccination/add/")
	@ResponseStatus(HttpStatus.OK)
	public Long addVaccination(@RequestBody Vaccination vaccination) {
		return vaccinationDAO.addVaccination(vaccination);
	}


	@GetMapping(value = "/dantistData/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<DantistData> getAllDantistDataByPatient(@PathVariable Long id) {
		return dantistDataDAO.getAllDantistDataByPatient(id);
	}

	@GetMapping(value = "/dantistData/", params = "email")
	@ResponseStatus(HttpStatus.OK)
	public List<DantistData> getAllDantistDataByPatient(@RequestParam String email) {
		return dantistDataDAO.getAllDantistDataByPatient(email);
	}

	@PostMapping(value = "/dantistData/add/")
	@ResponseStatus(HttpStatus.OK)
	public Long addDantistData(@RequestBody DantistData dantistData) {
		return dantistDataDAO.addDantistData(dantistData);
	}

	@GetMapping(value = "/physicalData/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<PhysicalData> getAllPhysicalDataByPatient(@PathVariable Long id) {
		return physicalDataDAO.getAllPhysicalDataByPatient(id);
	}

	@GetMapping(value = "/physicalData/", params = "email")
	@ResponseStatus(HttpStatus.OK)
	public List<PhysicalData> getAllPhysicalDataByPatient(@RequestParam String email) {
		return physicalDataDAO.getAllPhysicalDataByPatient(email);
	}

	@PostMapping(value = "/physicalData/add/")
	@ResponseStatus(HttpStatus.OK)
	public Long addPhysicalData(@RequestBody PhysicalData physicalData) {
		return physicalDataDAO.addPhysicalData(physicalData);
	}

	@GetMapping(value = "/examinationData/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<ExaminationData> getAllExaminationDataByPatient(@PathVariable Long id) {
		return examinationDataDAO.getAllExaminationDataByPatient(id);
	}

	@GetMapping(value = "/examinationData/", params = "email")
	@ResponseStatus(HttpStatus.OK)
	public List<ExaminationData> getAllExaminationDataByPatient(@RequestParam String email) {
		return examinationDataDAO.getAllExaminationDataByPatient(email);
	}

	@PostMapping(value = "/examinationData/add/")
	@ResponseStatus(HttpStatus.OK)
	public Long addExaminationData(@RequestBody ExaminationData examinationData) {
		return examinationDataDAO.addExaminationData(examinationData);
	}
}