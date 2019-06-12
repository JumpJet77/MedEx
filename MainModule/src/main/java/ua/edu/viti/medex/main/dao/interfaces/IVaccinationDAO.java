package ua.edu.viti.medex.main.dao.interfaces;

import ua.edu.viti.medex.main.entities.data.Vaccination;

import java.util.List;

public interface IVaccinationDAO {

	List<Vaccination> getAllVaccinationsByPatient(Long id);

	List<Vaccination> getAllVaccinationsByPatient(String email);

	Long addVaccination(Vaccination vaccination);

}
