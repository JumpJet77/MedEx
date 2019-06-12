package ua.edu.viti.medex.main.dao.interfaces;

import ua.edu.viti.medex.main.entities.humans.Doctor;

public interface IDoctorDAO {
	Doctor getDoctorById(Long id);

	Doctor getDoctorByEmail(String email);
}
