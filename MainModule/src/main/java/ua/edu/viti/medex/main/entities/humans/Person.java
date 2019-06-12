package ua.edu.viti.medex.main.entities.humans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@SuppressWarnings("WeakerAccess")
@Entity(name = "persons")
public class Person implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_person", nullable = false, updatable = false, unique = true)
	protected Long id;
	@Column(name = "email", nullable = false)
	protected String email;
	@JsonIgnore
	@JsonDeserialize
	@Column(name = "password", nullable = false)
	protected String password;
	@Column(name = "first_name", nullable = false)
	protected String firstName;
	@Column(name = "middle_name", nullable = false)
	protected String middleName;
	@Column(name = "last_name", nullable = false)
	protected String lastName;
	@Column(name = "birth_date", nullable = false)
	protected Date birthDate;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "persons_roles",
			joinColumns = @JoinColumn(
					name = "id_person", referencedColumnName = "id_person"),
			inverseJoinColumns = @JoinColumn(
					name = "id_role", referencedColumnName = "id_role"))
	protected Collection<Roles> roles;
	@Column(name = "range", nullable = false)
	protected String range;
	@Column(name = "service_place", nullable = false)
	protected String servicePlace;
	@Column(name = "home_address", nullable = false)
	protected String homeAddress;
	@Column(name = "phone_number", nullable = false)
	protected String phoneNumber;

	public Person() {
	}

	public Person(String email, String password, String firstName, String middleName, String lastName, Date birthDate, Collection<Roles> roles, String range, String servicePlace, String homeAddress, String phoneNumber) {
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.roles = roles;
		this.range = range;
		this.servicePlace = servicePlace;
		this.homeAddress = homeAddress;
		this.phoneNumber = phoneNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@JsonIgnore
	public Collection<Roles> getCollectionRoles() {
		return roles;
	}

	/**
	 * Getter for roles, which returns String of roles, which user have
	 * Main purpose, for passing it to the client side in JSON, an Collection itself causes StackOverflowException
	 *
	 * @return String representation of roles
	 */

	public String getRoles() {
		StringBuilder sb = new StringBuilder();
		for (Roles role : roles) {
			sb.append("," + role.getRole());
		}
		sb.delete(0, 1);
		return sb.toString();
	}

	public void setRoles(Collection<Roles> roles) {
		this.roles = roles;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getServicePlace() {
		return servicePlace;
	}

	public void setServicePlace(String servicePlace) {
		this.servicePlace = servicePlace;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Person)) return false;
		Person person = (Person) o;
		return id.equals(person.id) &&
				email.equals(person.email) &&
				password.equals(person.password) &&
				firstName.equals(person.firstName) &&
				middleName.equals(person.middleName) &&
				lastName.equals(person.lastName) &&
				birthDate.equals(person.birthDate) &&
				roles.equals(person.roles) &&
				range.equals(person.range) &&
				servicePlace.equals(person.servicePlace) &&
				homeAddress.equals(person.homeAddress) &&
				phoneNumber.equals(person.phoneNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, email, password, firstName, middleName, lastName, birthDate, roles, range, servicePlace, homeAddress, phoneNumber);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("id=").append(id);
		sb.append(", email='").append(email).append('\'');
		sb.append(", password='").append(password).append('\'');
		sb.append(", firstName='").append(firstName).append('\'');
		sb.append(", middleName='").append(middleName).append('\'');
		sb.append(", lastName='").append(lastName).append('\'');
		sb.append(", birthDate=").append(birthDate);
		sb.append(", roles=").append(roles);
		sb.append(", range='").append(range).append('\'');
		sb.append(", servicePlace='").append(servicePlace).append('\'');
		sb.append(", homeAddress='").append(homeAddress).append('\'');
		sb.append(", phoneNumber='").append(phoneNumber).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
