package ua.edu.viti.medex.main.entities.data;


import ua.edu.viti.medex.main.entities.humans.Person;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity(name = "physical_data")
public class PhysicalData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_physical_data", nullable = false, updatable = false, unique = true)
	private Long id;
	@Column(name = "physical_examination_date")
	private Date physicalExaminationDate;
	@Column(name = "height")
	private Integer height;
	@Column(name = "weight")
	private Integer weight;
	@Column(name = "circumference_calm")
	private Integer circumferenceCalm;
	@Column(name = "circumference_breathe_in")
	private Integer circumferenceBreatheIn;
	@Column(name = "circumference_breathe_out")
	private Integer circumferenceBreatheOut;
	@Column(name = "spirometry")
	private Integer spirometry;
	@Column(name = "dynamometry_left_hand")
	private Integer dynamometryLeftHand;
	@Column(name = "dynamometry_right_hand")
	private Integer dynamometryRightHand;
	@Column(name = "circumference_stomach")
	private Integer circumferenceStomach;
	@OneToOne(fetch = FetchType.EAGER, targetEntity = Person.class)
	private Person patient;

	public PhysicalData() {
	}

	public PhysicalData(Date physicalExaminationDate, Integer height, Integer weight, Integer circumferenceCalm, Integer circumferenceBreatheIn, Integer circumferenceBreatheOut, Integer spirometry, Integer dynamometryLeftHand, Integer dynamometryRightHand, Integer circumferenceStomach, Person patient) {
		this.physicalExaminationDate = physicalExaminationDate;
		this.height = height;
		this.weight = weight;
		this.circumferenceCalm = circumferenceCalm;
		this.circumferenceBreatheIn = circumferenceBreatheIn;
		this.circumferenceBreatheOut = circumferenceBreatheOut;
		this.spirometry = spirometry;
		this.dynamometryLeftHand = dynamometryLeftHand;
		this.dynamometryRightHand = dynamometryRightHand;
		this.circumferenceStomach = circumferenceStomach;
		this.patient = patient;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getPhysicalExaminationDate() {
		return physicalExaminationDate;
	}

	public void setPhysicalExaminationDate(Date physicalExaminationDate) {
		this.physicalExaminationDate = physicalExaminationDate;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getCircumferenceCalm() {
		return circumferenceCalm;
	}

	public void setCircumferenceCalm(Integer circumferenceCalm) {
		this.circumferenceCalm = circumferenceCalm;
	}

	public Integer getCircumferenceBreatheIn() {
		return circumferenceBreatheIn;
	}

	public void setCircumferenceBreatheIn(Integer circumferenceBreatheIn) {
		this.circumferenceBreatheIn = circumferenceBreatheIn;
	}

	public Integer getCircumferenceBreatheOut() {
		return circumferenceBreatheOut;
	}

	public void setCircumferenceBreatheOut(Integer circumferenceBreatheOut) {
		this.circumferenceBreatheOut = circumferenceBreatheOut;
	}

	public Integer getSpirometry() {
		return spirometry;
	}

	public void setSpirometry(Integer spirometry) {
		this.spirometry = spirometry;
	}

	public Integer getDynamometryLeftHand() {
		return dynamometryLeftHand;
	}

	public void setDynamometryLeftHand(Integer dynamometryLeftHand) {
		this.dynamometryLeftHand = dynamometryLeftHand;
	}

	public Integer getDynamometryRightHand() {
		return dynamometryRightHand;
	}

	public void setDynamometryRightHand(Integer dynamometryRightHand) {
		this.dynamometryRightHand = dynamometryRightHand;
	}

	public Integer getCircumferenceStomach() {
		return circumferenceStomach;
	}

	public void setCircumferenceStomach(Integer circumferenceStomach) {
		this.circumferenceStomach = circumferenceStomach;
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
		if (!(o instanceof PhysicalData)) return false;
		PhysicalData that = (PhysicalData) o;
		return Objects.equals(getId(), that.getId()) &&
				Objects.equals(getPhysicalExaminationDate(), that.getPhysicalExaminationDate()) &&
				Objects.equals(getHeight(), that.getHeight()) &&
				Objects.equals(getWeight(), that.getWeight()) &&
				Objects.equals(getCircumferenceCalm(), that.getCircumferenceCalm()) &&
				Objects.equals(getCircumferenceBreatheIn(), that.getCircumferenceBreatheIn()) &&
				Objects.equals(getCircumferenceBreatheOut(), that.getCircumferenceBreatheOut()) &&
				Objects.equals(getSpirometry(), that.getSpirometry()) &&
				Objects.equals(getDynamometryLeftHand(), that.getDynamometryLeftHand()) &&
				Objects.equals(getDynamometryRightHand(), that.getDynamometryRightHand()) &&
				Objects.equals(getCircumferenceStomach(), that.getCircumferenceStomach()) &&
				Objects.equals(getPatient(), that.getPatient());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getPhysicalExaminationDate(), getHeight(), getWeight(), getCircumferenceCalm(), getCircumferenceBreatheIn(), getCircumferenceBreatheOut(), getSpirometry(), getDynamometryLeftHand(), getDynamometryRightHand(), getCircumferenceStomach(), getPatient());
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("PhysicalData{");
		sb.append("id=").append(id);
		sb.append(", physicalExaminationDate=").append(physicalExaminationDate);
		sb.append(", height=").append(height);
		sb.append(", weight=").append(weight);
		sb.append(", circumferenceCalm=").append(circumferenceCalm);
		sb.append(", circumferenceBreatheIn=").append(circumferenceBreatheIn);
		sb.append(", circumferenceBreatheOut=").append(circumferenceBreatheOut);
		sb.append(", spirometry=").append(spirometry);
		sb.append(", dynamometryLeftHand=").append(dynamometryLeftHand);
		sb.append(", dynamometryRightHand=").append(dynamometryRightHand);
		sb.append(", circumferenceStomach=").append(circumferenceStomach);
		sb.append(", patient=").append(patient);
		sb.append('}');
		return sb.toString();
	}
}
