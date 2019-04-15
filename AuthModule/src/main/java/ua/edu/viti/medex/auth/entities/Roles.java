package ua.edu.viti.medex.auth.entities;

import ua.edu.viti.medex.auth.entities.enums.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "roles")
public class Roles implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@OneToOne
	@MapsId
	private Users user;
	@Enumerated(value = EnumType.STRING)
	private Role role;


	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Roles{" +
				"user=" + user +
				", role=" + role +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Roles roles = (Roles) o;
		return getUser().equals(roles.getUser()) &&
				getRole() == roles.getRole();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getUser(), getRole());
	}
}
