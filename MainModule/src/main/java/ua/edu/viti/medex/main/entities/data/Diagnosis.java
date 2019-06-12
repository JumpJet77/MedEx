package ua.edu.viti.medex.main.entities.data;

import ua.edu.viti.medex.main.entities.humans.Person;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity(name = "diagnoses")
public class Diagnosis implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_diagnosis", nullable = false, updatable = false, unique = true)
	private Long id;
	@Column(name = "diagnosis_date")
	private Date diagnosisDate;
	@Column(name = "diagnosis")
	private String diagnosis;
	@Column(name = "treatment_start_date")
	private Date treatmentStartDate;
	@Column(name = "treatment_finish_date")
	private Date treatmentFinishDate;
	@Column(name = "treatment")
	private String treatment;
	@Column(name = "recommendations")
	private String recomendations;
	@OneToOne(fetch = FetchType.EAGER, targetEntity = Person.class)
	private Person patient;

	public Diagnosis() {
	}

	public Diagnosis(Date diagnosisDate, String diagnosis, Date treatmentStartDate, Date treatmentFinishDate, String treatment, String recomendations, Person patient) {
		this.diagnosisDate = diagnosisDate;
		this.diagnosis = diagnosis;
		this.treatmentStartDate = treatmentStartDate;
		this.treatmentFinishDate = treatmentFinishDate;
		this.treatment = treatment;
		this.recomendations = recomendations;
		this.patient = patient;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDiagnosisDate() {
		return diagnosisDate;
	}

	public void setDiagnosisDate(Date diagnosisDate) {
		this.diagnosisDate = diagnosisDate;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public Date getTreatmentStartDate() {
		return treatmentStartDate;
	}

	public void setTreatmentStartDate(Date treatmentStartDate) {
		this.treatmentStartDate = treatmentStartDate;
	}

	public Date getTreatmentFinishDate() {
		return treatmentFinishDate;
	}

	public void setTreatmentFinishDate(Date treatmentFinishDate) {
		this.treatmentFinishDate = treatmentFinishDate;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	public String getRecomendations() {
		return recomendations;
	}

	public void setRecomendations(String recomendations) {
		this.recomendations = recomendations;
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
		if (!(o instanceof Diagnosis)) return false;
		Diagnosis diagnosis1 = (Diagnosis) o;
		return Objects.equals(getId(), diagnosis1.getId()) &&
				Objects.equals(getDiagnosisDate(), diagnosis1.getDiagnosisDate()) &&
				Objects.equals(getDiagnosis(), diagnosis1.getDiagnosis()) &&
				Objects.equals(getTreatmentStartDate(), diagnosis1.getTreatmentStartDate()) &&
				Objects.equals(getTreatmentFinishDate(), diagnosis1.getTreatmentFinishDate()) &&
				Objects.equals(getTreatment(), diagnosis1.getTreatment()) &&
				Objects.equals(getRecomendations(), diagnosis1.getRecomendations()) &&
				Objects.equals(getPatient(), diagnosis1.getPatient());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getDiagnosisDate(), getDiagnosis(), getTreatmentStartDate(), getTreatmentFinishDate(), getTreatment(), getRecomendations(), getPatient());
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Diagnosis{");
		sb.append("id=").append(id);
		sb.append(", diagnosisDate=").append(diagnosisDate);
		sb.append(", diagnosis='").append(diagnosis).append('\'');
		sb.append(", treatmentStartDate=").append(treatmentStartDate);
		sb.append(", treatmentFinishDate=").append(treatmentFinishDate);
		sb.append(", treatment='").append(treatment).append('\'');
		sb.append(", recomendations='").append(recomendations).append('\'');
		sb.append(", patient=").append(patient);
		sb.append('}');
		return sb.toString();
	}
}
