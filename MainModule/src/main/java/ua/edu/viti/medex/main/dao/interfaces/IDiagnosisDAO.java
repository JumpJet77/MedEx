package ua.edu.viti.medex.main.dao.interfaces;

import ua.edu.viti.medex.main.entities.data.Diagnosis;

import java.util.List;

public interface IDiagnosisDAO {

	List<Diagnosis> getAllDiagnosisByPatient(Long id);

	List<Diagnosis> getAllDiagnosisByPatient(String email);

	Long addDiagnosis(Diagnosis diagnosis);
}
