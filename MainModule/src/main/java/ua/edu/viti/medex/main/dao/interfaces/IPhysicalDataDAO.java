package ua.edu.viti.medex.main.dao.interfaces;

import ua.edu.viti.medex.main.entities.data.PhysicalData;

import java.util.List;

public interface IPhysicalDataDAO {

	List<PhysicalData> getAllPhysicalDataByPatient(Long id);

	List<PhysicalData> getAllPhysicalDataByPatient(String email);

	Long addPhysicalData(PhysicalData physicalData);

}
