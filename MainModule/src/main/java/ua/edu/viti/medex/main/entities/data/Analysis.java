package ua.edu.viti.medex.main.entities.data;

import ua.edu.viti.medex.main.entities.humans.Person;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity(name = "analyses")
public class Analysis {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_analysis", nullable = false, updatable = false, unique = true)
	private Long id;
	@Column(name = "analysis_date")
	private Date analysisDate;
	@Column(name = "analysis_name")
	private String analysisName;
	@Column(name = "analysis_result")
	private String analysisResult;
	@Column(name = "analysis_additional_comment")
	private String analysisAdditionalComment;
	@OneToOne(fetch = FetchType.EAGER, targetEntity = Person.class)
	private Person patient;

	public Analysis() {
	}

	public Analysis(Date analysisDate, String analysisName, String analysisResult, String analysisAdditionalComment, Person patient) {
		this.analysisDate = analysisDate;
		this.analysisName = analysisName;
		this.analysisResult = analysisResult;
		this.analysisAdditionalComment = analysisAdditionalComment;
		this.patient = patient;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getAnalysisDate() {
		return analysisDate;
	}

	public void setAnalysisDate(Date analysisDate) {
		this.analysisDate = analysisDate;
	}

	public String getAnalysisName() {
		return analysisName;
	}

	public void setAnalysisName(String analysisName) {
		this.analysisName = analysisName;
	}

	public String getAnalysisResult() {
		return analysisResult;
	}

	public void setAnalysisResult(String analysisResult) {
		this.analysisResult = analysisResult;
	}

	public String getAnalysisAdditionalComment() {
		return analysisAdditionalComment;
	}

	public void setAnalysisAdditionalComment(String analysisAdditionalComment) {
		this.analysisAdditionalComment = analysisAdditionalComment;
	}

	public Person getPatient() {
		return patient;
	}

	public void setPatient(Person patient) {
		this.patient = patient;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Analysis)) return false;
		Analysis analysis = (Analysis) o;
		return Objects.equals(getId(), analysis.getId()) &&
				Objects.equals(getAnalysisDate(), analysis.getAnalysisDate()) &&
				Objects.equals(getAnalysisName(), analysis.getAnalysisName()) &&
				Objects.equals(getAnalysisResult(), analysis.getAnalysisResult()) &&
				Objects.equals(getAnalysisAdditionalComment(), analysis.getAnalysisAdditionalComment()) &&
				Objects.equals(getPatient(), analysis.getPatient());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getAnalysisDate(), getAnalysisName(), getAnalysisResult(), getAnalysisAdditionalComment(), getPatient());
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Analysis{");
		sb.append("id=").append(id);
		sb.append(", analysisDate=").append(analysisDate);
		sb.append(", analysisName='").append(analysisName).append('\'');
		sb.append(", analysisResult='").append(analysisResult).append('\'');
		sb.append(", analysisAdditionalComment='").append(analysisAdditionalComment).append('\'');
		sb.append(", patient=").append(patient);
		sb.append('}');
		return sb.toString();
	}
}
