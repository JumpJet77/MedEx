package ua.edu.viti.medex.main.entities.data;

import ua.edu.viti.medex.main.entities.humans.Person;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity(name = "dantist_data")
public class DantistData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_dantist_data", nullable = false, updatable = false, unique = true)
	private Long id;
	@Column(name = "dantist_examination_date")
	private Date dantistExaminationDate;
	@Column(name = "mucosa_state")
	private String mucosaState;
	@Column(name = "gums_state")
	private String gumsState;
	@Column(name = "tooth_sediment")
	private String toothSediment;
	@Column(name = "diagnosis")
	private String diagnosis;
	@OneToOne(fetch = FetchType.EAGER, targetEntity = Person.class)
	private Person patient;

	public DantistData() {
	}

	public DantistData(Date dantistExaminationDate, String mucosaState, String gumsState, String toothSediment, String diagnosis, Person patient) {
		this.dantistExaminationDate = dantistExaminationDate;
		this.mucosaState = mucosaState;
		this.gumsState = gumsState;
		this.toothSediment = toothSediment;
		this.diagnosis = diagnosis;
		this.patient = patient;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDantistExaminationDate() {
		return dantistExaminationDate;
	}

	public void setDantistExaminationDate(Date dantistExaminationDate) {
		this.dantistExaminationDate = dantistExaminationDate;
	}

	public String getMucosaState() {
		return mucosaState;
	}

	public void setMucosaState(String mucosaState) {
		this.mucosaState = mucosaState;
	}

	public String getGumsState() {
		return gumsState;
	}

	public void setGumsState(String gumsState) {
		this.gumsState = gumsState;
	}

	public String getToothSediment() {
		return toothSediment;
	}

	public void setToothSediment(String toothSediment) {
		this.toothSediment = toothSediment;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
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
		if (!(o instanceof DantistData)) return false;
		DantistData that = (DantistData) o;
		return Objects.equals(getId(), that.getId()) &&
				Objects.equals(getDantistExaminationDate(), that.getDantistExaminationDate()) &&
				Objects.equals(getMucosaState(), that.getMucosaState()) &&
				Objects.equals(getGumsState(), that.getGumsState()) &&
				Objects.equals(getToothSediment(), that.getToothSediment()) &&
				Objects.equals(getDiagnosis(), that.getDiagnosis()) &&
				Objects.equals(getPatient(), that.getPatient());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getDantistExaminationDate(), getMucosaState(), getGumsState(), getToothSediment(), getDiagnosis(), getPatient());
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("DantistData{");
		sb.append("id=").append(id);
		sb.append(", dantistExaminationDate=").append(dantistExaminationDate);
		sb.append(", mucosaState='").append(mucosaState).append('\'');
		sb.append(", gumsState='").append(gumsState).append('\'');
		sb.append(", toothSediment='").append(toothSediment).append('\'');
		sb.append(", diagnosis='").append(diagnosis).append('\'');
		sb.append(", patient=").append(patient);
		sb.append('}');
		return sb.toString();
	}
}
