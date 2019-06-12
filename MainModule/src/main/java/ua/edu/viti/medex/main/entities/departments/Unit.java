package ua.edu.viti.medex.main.entities.departments;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import ua.edu.viti.medex.main.entities.humans.Person;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity(name = "units")
public class Unit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_unit", nullable = false, updatable = false, unique = true)
	private Long id;
	@Column(name = "unit_name")
	private String unitName;
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany
	@JoinTable(
			name = "unit_faculties",
			joinColumns = @JoinColumn(
					name = "id_unit", referencedColumnName = "id_unit"),
			inverseJoinColumns = @JoinColumn(
					name = "id_faculty", referencedColumnName = "id_faculty"))
	private Collection<Faculty> faculties;
	@OneToOne(fetch = FetchType.EAGER, targetEntity = Person.class)
	private Person departmentHead;

	public Unit() {
	}

	public Unit(String unitName, Collection<Faculty> faculties, Person departmentHead) {
		this.unitName = unitName;
		this.faculties = faculties;
		this.departmentHead = departmentHead;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Collection<Faculty> getFaculties() {
		return faculties;
	}

	public void setFaculties(Collection<Faculty> faculties) {
		this.faculties = faculties;
	}

	public Person getDepartmentHead() {
		return departmentHead;
	}

	public void setDepartmentHead(Person departmentHead) {
		this.departmentHead = departmentHead;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Unit)) return false;
		Unit unit = (Unit) o;
		return Objects.equals(getId(), unit.getId()) &&
				Objects.equals(getUnitName(), unit.getUnitName()) &&
				Objects.equals(getFaculties(), unit.getFaculties()) &&
				Objects.equals(getDepartmentHead(), unit.getDepartmentHead());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getUnitName(), getFaculties(), getDepartmentHead());
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Unit{");
		sb.append("id=").append(id);
		sb.append(", unitName='").append(unitName).append('\'');
		sb.append(", faculties=").append(faculties);
		sb.append(", departmentHead=").append(departmentHead);
		sb.append('}');
		return sb.toString();
	}
}
