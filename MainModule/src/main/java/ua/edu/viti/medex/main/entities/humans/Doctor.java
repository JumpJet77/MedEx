package ua.edu.viti.medex.main.entities.humans;

import ua.edu.viti.medex.main.entities.data.Appeal;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity(name = "doctors")
public class Doctor implements Serializable {

	@Id
	@OneToOne(fetch = FetchType.EAGER, targetEntity = Person.class)
	private Person person;
	@Column(name = "specialization")
	private String specialization;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "appeals_doctor",
			joinColumns = @JoinColumn(
					name = "id_doctor", referencedColumnName = "person_id_person"),
			inverseJoinColumns = @JoinColumn(
					name = "id_appeal", referencedColumnName = "id_appeal"))
	private Collection<Appeal> appeals;

	public Doctor() {
	}

	public Doctor(Person person, String specialization, Collection<Appeal> appeals) {
		this.person = person;
		this.specialization = specialization;
		this.appeals = appeals;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public Collection<Appeal> getAppeals() {
		return appeals;
	}

	public void setAppeals(Collection<Appeal> appeals) {
		this.appeals = appeals;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Doctor)) return false;
		Doctor doctor = (Doctor) o;
		return Objects.equals(getPerson(), doctor.getPerson()) &&
				Objects.equals(getSpecialization(), doctor.getSpecialization()) &&
				Objects.equals(getAppeals(), doctor.getAppeals());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPerson(), getSpecialization(), getAppeals());
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Doctor{");
		sb.append("person=").append(person);
		sb.append(", specialization='").append(specialization).append('\'');
		sb.append(", appeals=").append(appeals);
		sb.append('}');
		return sb.toString();
	}
}
