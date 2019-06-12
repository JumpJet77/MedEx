package ua.edu.viti.medex.main.dao.interfaces;

import ua.edu.viti.medex.main.entities.data.Analysis;

import java.util.List;

public interface IAnalysisDAO {

	List<Analysis> getAllAnalysisByPatient(Long id);

	List<Analysis> getAllAnalysisByPatient(String email);

	Long addAnalysis(Analysis analysis);

}
