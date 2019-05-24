package ua.edu.viti.medex.main.entities.humans;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity(name = "patients")
public class Patient implements Serializable {

	@Id
	@MapsId
	@OneToOne(fetch = FetchType.EAGER, targetEntity = Person.class)
	private Person person;
	@Column(name = "nationality")
	private String nationality;
	@Column(name = "education")
	private String education;
	@Column(name = "recruitment_date")
	private Date recruitmentDate;
	@Column(name = "family_state")
	private String familyState;
	@Column(name = "heredity_disease_name", length = 1000)
	private String heredityDiseaseName;
	@Column(name = "special_notes", length = 1000)
	private String specialNotices;

	public Patient() {
	}

	public Patient(Person person, String nationality, String education, Date recruitmentDate, String familyState, String heredityDiseaseName, String specialNotices) {
		this.person = person;
		this.nationality = nationality;
		this.education = education;
		this.recruitmentDate = recruitmentDate;
		this.familyState = familyState;
		this.heredityDiseaseName = heredityDiseaseName;
		this.specialNotices = specialNotices;
	}


	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public Date getRecruitmentDate() {
		return recruitmentDate;
	}

	public void setRecruitmentDate(Date recruitmentDate) {
		this.recruitmentDate = recruitmentDate;
	}

	public String getFamilyState() {
		return familyState;
	}

	public void setFamilyState(String familyState) {
		this.familyState = familyState;
	}

	public String getHeredityDiseaseName() {
		return heredityDiseaseName;
	}

	public void setHeredityDiseaseName(String heredityDiseaseName) {
		this.heredityDiseaseName = heredityDiseaseName;
	}

	public String getSpecialNotices() {
		return specialNotices;
	}

	public void setSpecialNotices(String specialNotices) {
		this.specialNotices = specialNotices;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Patient)) return false;
		Patient patient = (Patient) o;
		return getPerson().equals(patient.getPerson()) &&
				Objects.equals(getNationality(), patient.getNationality()) &&
				Objects.equals(getEducation(), patient.getEducation()) &&
				Objects.equals(getRecruitmentDate(), patient.getRecruitmentDate()) &&
				Objects.equals(getFamilyState(), patient.getFamilyState()) &&
				Objects.equals(getHeredityDiseaseName(), patient.getHeredityDiseaseName()) &&
				Objects.equals(getSpecialNotices(), patient.getSpecialNotices());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPerson(), getNationality(), getEducation(), getRecruitmentDate(), getFamilyState(), getHeredityDiseaseName(), getSpecialNotices());
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Patient{");
		sb.append("person=").append(person);
		sb.append(", nationality='").append(nationality).append('\'');
		sb.append(", education='").append(education).append('\'');
		sb.append(", recruitmentDate=").append(recruitmentDate);
		sb.append(", familyState='").append(familyState).append('\'');
		sb.append(", heredityDiseaseName='").append(heredityDiseaseName).append('\'');
		sb.append(", specialNotices='").append(specialNotices).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
