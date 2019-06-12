package ua.edu.viti.medex.main.entities.data;


import ua.edu.viti.medex.main.entities.humans.Person;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity(name = "examination_data")
public class ExaminationData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_examination_data", nullable = false, updatable = false, unique = true)
	private Long id;
	@Column(name = "examination_date")
	private Date examinationDate;
	@Column(name = "pulse")
	private Integer pulse;
	@Column(name = "body_structure")
	private String bodyStructure;
	@Column(name = "body_cover")
	private String bodyCover;
	@Column(name = "musculoskeletal_system")
	private String musculoskeletalSystem;
	@Column(name = "breate_organs")
	private String breateOrgans;
	@Column(name = "blood_circulation")
	private String bloodCirculation;
	@Column(name = "blood_pressure")
	private String bloodPressure;
	@Column(name = "digestion")
	private String digestion;
	@Column(name = "eyesight")
	private String eyesight;
	@Column(name = "hearing")
	private String hearing;
	@Column(name = "neural_system")
	private String neuralSystem;
	@Column(name = "another_organs")
	private String anotherOrgans;
	@Column(name = "complaints")
	private String complaints;
	@Column(name = "physical_growth")
	private String physicalGrowth;
	@Column(name = "health_state")
	private String healthState;
	@Column(name = "treatment_reaction")
	private String treatmentReaction;
	@Column(name = "bad_habits")
	private String badHabits;
	@Column(name = "special_notices")
	private String specialNotices;
	@OneToOne(fetch = FetchType.EAGER, targetEntity = Person.class)
	private Person patient;

	public ExaminationData() {
	}

	public ExaminationData(Date examinationDate, Integer pulse, String bodyStructure, String bodyCover, String musculoskeletalSystem, String breateOrgans, String bloodCirculation, String bloodPressure, String digestion, String eyesight, String hearing, String neuralSystem, String anotherOrgans, String complaints, String physicalGrowth, String healthState, String treatmentReaction, String badHabits, String specialNotices, Person patient) {
		this.examinationDate = examinationDate;
		this.pulse = pulse;
		this.bodyStructure = bodyStructure;
		this.bodyCover = bodyCover;
		this.musculoskeletalSystem = musculoskeletalSystem;
		this.breateOrgans = breateOrgans;
		this.bloodCirculation = bloodCirculation;
		this.bloodPressure = bloodPressure;
		this.digestion = digestion;
		this.eyesight = eyesight;
		this.hearing = hearing;
		this.neuralSystem = neuralSystem;
		this.anotherOrgans = anotherOrgans;
		this.complaints = complaints;
		this.physicalGrowth = physicalGrowth;
		this.healthState = healthState;
		this.treatmentReaction = treatmentReaction;
		this.badHabits = badHabits;
		this.specialNotices = specialNotices;
		this.patient = patient;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getExaminationDate() {
		return examinationDate;
	}

	public void setExaminationDate(Date examinationDate) {
		this.examinationDate = examinationDate;
	}

	public Integer getPulse() {
		return pulse;
	}

	public void setPulse(Integer pulse) {
		this.pulse = pulse;
	}

	public String getBodyStructure() {
		return bodyStructure;
	}

	public void setBodyStructure(String bodyStructure) {
		this.bodyStructure = bodyStructure;
	}

	public String getBodyCover() {
		return bodyCover;
	}

	public void setBodyCover(String bodyCover) {
		this.bodyCover = bodyCover;
	}

	public String getMusculoskeletalSystem() {
		return musculoskeletalSystem;
	}

	public void setMusculoskeletalSystem(String musculoskeletalSystem) {
		this.musculoskeletalSystem = musculoskeletalSystem;
	}

	public String getBreateOrgans() {
		return breateOrgans;
	}

	public void setBreateOrgans(String breateOrgans) {
		this.breateOrgans = breateOrgans;
	}

	public String getBloodCirculation() {
		return bloodCirculation;
	}

	public void setBloodCirculation(String bloodCirculation) {
		this.bloodCirculation = bloodCirculation;
	}

	public String getBloodPressure() {
		return bloodPressure;
	}

	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}

	public String getDigestion() {
		return digestion;
	}

	public void setDigestion(String digestion) {
		this.digestion = digestion;
	}

	public String getEyesight() {
		return eyesight;
	}

	public void setEyesight(String eyesight) {
		this.eyesight = eyesight;
	}

	public String getHearing() {
		return hearing;
	}

	public void setHearing(String hearing) {
		this.hearing = hearing;
	}

	public String getNeuralSystem() {
		return neuralSystem;
	}

	public void setNeuralSystem(String neuralSystem) {
		this.neuralSystem = neuralSystem;
	}

	public String getAnotherOrgans() {
		return anotherOrgans;
	}

	public void setAnotherOrgans(String anotherOrgans) {
		this.anotherOrgans = anotherOrgans;
	}

	public String getComplaints() {
		return complaints;
	}

	public void setComplaints(String complaints) {
		this.complaints = complaints;
	}

	public String getPhysicalGrowth() {
		return physicalGrowth;
	}

	public void setPhysicalGrowth(String physicalGrowth) {
		this.physicalGrowth = physicalGrowth;
	}

	public String getHealthState() {
		return healthState;
	}

	public void setHealthState(String healthState) {
		this.healthState = healthState;
	}

	public String getTreatmentReaction() {
		return treatmentReaction;
	}

	public void setTreatmentReaction(String treatmentReaction) {
		this.treatmentReaction = treatmentReaction;
	}

	public String getBadHabits() {
		return badHabits;
	}

	public void setBadHabits(String badHabits) {
		this.badHabits = badHabits;
	}

	public String getSpecialNotices() {
		return specialNotices;
	}

	public void setSpecialNotices(String specialNotices) {
		this.specialNotices = specialNotices;
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
		if (!(o instanceof ExaminationData)) return false;
		ExaminationData that = (ExaminationData) o;
		return Objects.equals(getId(), that.getId()) &&
				Objects.equals(getExaminationDate(), that.getExaminationDate()) &&
				Objects.equals(getPulse(), that.getPulse()) &&
				Objects.equals(getBodyStructure(), that.getBodyStructure()) &&
				Objects.equals(getBodyCover(), that.getBodyCover()) &&
				Objects.equals(getMusculoskeletalSystem(), that.getMusculoskeletalSystem()) &&
				Objects.equals(getBreateOrgans(), that.getBreateOrgans()) &&
				Objects.equals(getBloodCirculation(), that.getBloodCirculation()) &&
				Objects.equals(getBloodPressure(), that.getBloodPressure()) &&
				Objects.equals(getDigestion(), that.getDigestion()) &&
				Objects.equals(getEyesight(), that.getEyesight()) &&
				Objects.equals(getHearing(), that.getHearing()) &&
				Objects.equals(getNeuralSystem(), that.getNeuralSystem()) &&
				Objects.equals(getAnotherOrgans(), that.getAnotherOrgans()) &&
				Objects.equals(getComplaints(), that.getComplaints()) &&
				Objects.equals(getPhysicalGrowth(), that.getPhysicalGrowth()) &&
				Objects.equals(getHealthState(), that.getHealthState()) &&
				Objects.equals(getTreatmentReaction(), that.getTreatmentReaction()) &&
				Objects.equals(getBadHabits(), that.getBadHabits()) &&
				Objects.equals(getSpecialNotices(), that.getSpecialNotices()) &&
				Objects.equals(getPatient(), that.getPatient());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getExaminationDate(), getPulse(), getBodyStructure(), getBodyCover(), getMusculoskeletalSystem(), getBreateOrgans(), getBloodCirculation(), getBloodPressure(), getDigestion(), getEyesight(), getHearing(), getNeuralSystem(), getAnotherOrgans(), getComplaints(), getPhysicalGrowth(), getHealthState(), getTreatmentReaction(), getBadHabits(), getSpecialNotices(), getPatient());
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("ExaminationData{");
		sb.append("id=").append(id);
		sb.append(", examinationDate=").append(examinationDate);
		sb.append(", pulse=").append(pulse);
		sb.append(", bodyStructure='").append(bodyStructure).append('\'');
		sb.append(", bodyCover='").append(bodyCover).append('\'');
		sb.append(", musculoskeletalSystem='").append(musculoskeletalSystem).append('\'');
		sb.append(", breateOrgans='").append(breateOrgans).append('\'');
		sb.append(", bloodCirculation='").append(bloodCirculation).append('\'');
		sb.append(", bloodPressure='").append(bloodPressure).append('\'');
		sb.append(", digestion='").append(digestion).append('\'');
		sb.append(", eyesight='").append(eyesight).append('\'');
		sb.append(", hearing='").append(hearing).append('\'');
		sb.append(", neuralSystem='").append(neuralSystem).append('\'');
		sb.append(", anotherOrgans='").append(anotherOrgans).append('\'');
		sb.append(", complaints='").append(complaints).append('\'');
		sb.append(", physicalGrowth='").append(physicalGrowth).append('\'');
		sb.append(", healthState='").append(healthState).append('\'');
		sb.append(", treatmentReaction='").append(treatmentReaction).append('\'');
		sb.append(", badHabits='").append(badHabits).append('\'');
		sb.append(", specialNotices='").append(specialNotices).append('\'');
		sb.append(", patient=").append(patient);
		sb.append('}');
		return sb.toString();
	}
}
