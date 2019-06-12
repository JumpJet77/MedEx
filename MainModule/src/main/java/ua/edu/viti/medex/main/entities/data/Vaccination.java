package ua.edu.viti.medex.main.entities.data;

import ua.edu.viti.medex.main.entities.humans.Person;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity(name = "vaccinations")
public class Vaccination {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_vaccination", nullable = false, updatable = false, unique = true)
	private Long id;
	@Column(name = "vaccination_date")
	private Date vaccinationDate;
	@Column(name = "vaccination_name")
	private String vaccinaitionName;
	@Column(name = "vaccination_reaction")
	private String vaccinationReaction;
	@OneToOne(fetch = FetchType.EAGER, targetEntity = Person.class)
	private Person patient;

	public Vaccination() {
	}

	public Vaccination(Date vaccinationDate, String vaccinationName, String vaccinationReaction, Person patient) {
		this.vaccinationDate = vaccinationDate;
		this.vaccinaitionName = vaccinationName;
		this.vaccinationReaction = vaccinationReaction;
		this.patient = patient;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getVaccinationDate() {
		return vaccinationDate;
	}

	public void setVaccinationDate(Date vaccinationDate) {
		this.vaccinationDate = vaccinationDate;
	}

	public String getVaccinationName() {
		return vaccinaitionName;
	}

	public void setVaccinaitionName(String vaccinaitionName) {
		this.vaccinaitionName = vaccinaitionName;
	}

	public String getVaccinationReaction() {
		return vaccinationReaction;
	}

	public void setVaccinationReaction(String vaccinationReaction) {
		this.vaccinationReaction = vaccinationReaction;
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
		if (!(o instanceof Vaccination)) return false;
		Vaccination that = (Vaccination) o;
		return Objects.equals(getId(), that.getId()) &&
				Objects.equals(getVaccinationDate(), that.getVaccinationDate()) &&
				Objects.equals(getVaccinationName(), that.getVaccinationName()) &&
				Objects.equals(getVaccinationReaction(), that.getVaccinationReaction()) &&
				Objects.equals(getPatient(), that.getPatient());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getVaccinationDate(), getVaccinationName(), getVaccinationReaction(), getPatient());
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Vaccination{");
		sb.append("id=").append(id);
		sb.append(", vaccinationDate=").append(vaccinationDate);
		sb.append(", vaccinationName='").append(vaccinaitionName).append('\'');
		sb.append(", vaccinationReaction='").append(vaccinationReaction).append('\'');
		sb.append(", patient=").append(patient);
		sb.append('}');
		return sb.toString();
	}
}
