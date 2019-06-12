package ua.edu.viti.medex.main.entities.data;

import ua.edu.viti.medex.main.entities.humans.Person;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity(name = "appeals")
public class Appeal implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_appeal", nullable = false, updatable = false, unique = true)
	private Long id;
	@Column(name = "appeal_date")
	private Date appealDate;
	@Column(name = "appeal_essence")
	private String appealEssence;
	@Column(name = "is_first_appeal", length = 2000)
	private Boolean isFirstAppeal;
	@OneToOne(fetch = FetchType.EAGER, targetEntity = Person.class)
	private Person patient;

	public Appeal() {
	}

	public Appeal(Person patient, Date appealDate, String appealEssence, Boolean isFirstAppeal) {
		this.patient = patient;
		this.appealDate = appealDate;
		this.appealEssence = appealEssence;
		this.isFirstAppeal = isFirstAppeal;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Person getPatient() {
		return patient;
	}

	public void setPatient(Person patient) {
		this.patient = patient;
	}

	public Date getAppealDate() {
		return appealDate;
	}

	public void setAppealDate(Date appealDate) {
		this.appealDate = appealDate;
	}

	public String getAppealEssence() {
		return appealEssence;
	}

	public void setAppealEssence(String appealEssence) {
		this.appealEssence = appealEssence;
	}

	public Boolean getFirstAppeal() {
		return isFirstAppeal;
	}

	public void setFirstAppeal(Boolean firstAppeal) {
		isFirstAppeal = firstAppeal;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Appeal)) return false;
		Appeal appeal1 = (Appeal) o;
		return Objects.equals(getId(), appeal1.getId()) &&
				Objects.equals(getPatient(), appeal1.getPatient()) &&
				Objects.equals(getAppealDate(), appeal1.getAppealDate()) &&
				Objects.equals(getAppealEssence(), appeal1.getAppealEssence()) &&
				Objects.equals(isFirstAppeal, appeal1.isFirstAppeal);
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getPatient(), getAppealDate(), getAppealEssence(), isFirstAppeal);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Appeal{");
		sb.append("id=").append(id);
		sb.append(", patient=").append(patient);
		sb.append(", appealDate=").append(appealDate);
		sb.append(", appealEssence='").append(appealEssence).append('\'');
		sb.append(", isFirstAppeal=").append(isFirstAppeal);
		sb.append('}');
		return sb.toString();
	}
}
