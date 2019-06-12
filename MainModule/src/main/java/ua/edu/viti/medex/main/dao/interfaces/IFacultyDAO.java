package ua.edu.viti.medex.main.dao.interfaces;

import ua.edu.viti.medex.main.entities.departments.Faculty;
import ua.edu.viti.medex.main.entities.humans.Patient;

public interface IFacultyDAO {

	Faculty getFacultyById(Long id);

	Long addPatient(Patient patien, Long idFaculty);

	Faculty getFacultyByDoctorId(Long id);

	Faculty getFacultyByDoctorEmail(String email);
}
