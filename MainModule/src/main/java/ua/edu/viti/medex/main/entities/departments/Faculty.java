package ua.edu.viti.medex.main.entities.departments;


import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import ua.edu.viti.medex.main.entities.humans.Person;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity(name = "faculties")
public class Faculty implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_faculty", nullable = false, updatable = false, unique = true)
	private Long id;
	@Column(name = "faculty_name")
	private String facultyName;
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany
	@JoinTable(
			name = "patients_faculty",
			joinColumns = @JoinColumn(
					name = "id_faculty", referencedColumnName = "id_faculty"),
			inverseJoinColumns = @JoinColumn(
					name = "id_person", referencedColumnName = "id_person"))
	private Collection<Person> patients;
	@OneToOne(fetch = FetchType.EAGER, targetEntity = Person.class)
	private Person doctor;

	public Faculty() {
	}

	public Faculty(String facultyName, Collection<Person> patients, Person doctor) {
		this.facultyName = facultyName;
		this.patients = patients;
		this.doctor = doctor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFacultyName() {
		return facultyName;
	}

	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}

	public Collection<Person> getPatients() {
		return patients;
	}

	public void setPatients(Collection<Person> patients) {
		this.patients = patients;
	}

	public Person getDoctor() {
		return doctor;
	}

	public void setDoctor(Person doctor) {
		this.doctor = doctor;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Faculty)) return false;
		Faculty faculty = (Faculty) o;
		return Objects.equals(getId(), faculty.getId()) &&
				Objects.equals(getFacultyName(), faculty.getFacultyName()) &&
				Objects.equals(getPatients(), faculty.getPatients()) &&
				Objects.equals(getDoctor(), faculty.getDoctor());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getFacultyName(), getPatients(), getDoctor());
	}
}
