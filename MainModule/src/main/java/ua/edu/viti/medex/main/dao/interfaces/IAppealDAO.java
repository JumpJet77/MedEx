package ua.edu.viti.medex.main.dao.interfaces;

import ua.edu.viti.medex.main.entities.data.Appeal;

import java.util.List;

public interface IAppealDAO {

	List<Appeal> getAllAppeals();

	List<Appeal> getAllAppealsByPatient(Long id);

	List<Appeal> getAllAppealsByPatient(String email);

	Long addAppeal(Appeal appeal, Long idDoctor);

	Long addAppeal(Appeal appeal, String emailDoctor);
}
