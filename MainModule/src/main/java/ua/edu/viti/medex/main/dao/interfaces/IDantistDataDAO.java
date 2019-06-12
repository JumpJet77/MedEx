package ua.edu.viti.medex.main.dao.interfaces;

import ua.edu.viti.medex.main.entities.data.DantistData;

import java.util.List;

public interface IDantistDataDAO {

	List<DantistData> getAllDantistDataByPatient(Long id);

	List<DantistData> getAllDantistDataByPatient(String email);

	Long addDantistData(DantistData dantistData);

}
