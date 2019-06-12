package ua.edu.viti.medex.main.dao.interfaces;

import ua.edu.viti.medex.main.entities.data.ExaminationData;

import java.util.List;

public interface IExaminationDataDAO {

	List<ExaminationData> getAllExaminationDataByPatient(Long id);

	List<ExaminationData> getAllExaminationDataByPatient(String email);

	Long addExaminationData(ExaminationData examinationData);

}
