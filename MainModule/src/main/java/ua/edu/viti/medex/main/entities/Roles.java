package ua.edu.viti.medex.main.entities;

import ua.edu.viti.medex.main.entities.enums.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Ihor Dovhoshliubnyi
 * POJO for representing, and mapping in DB Role entity
 * Role has id, role itself
 * @see ua.edu.viti.medex.main.entities.enums.Role
 */

@SuppressWarnings("WeakerAccess")
@Entity(name = "roles")
public class Roles implements Serializable {
	@Id
	@Column(name = "id_role")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idRole;
	@Enumerated(EnumType.STRING)
	private Role role;

	public Roles() {
	}

	public Roles(Role role) {
		this.role = role;
	}

	public Long getIdRole() {
		return idRole;
	}

	public void setIdRole(Long idRole) {
		this.idRole = idRole;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Roles)) return false;
		Roles roles = (Roles) o;
		return getIdRole().equals(roles.getIdRole()) &&
				getRole() == roles.getRole();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getIdRole(), getRole());
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Roles{");
		sb.append("idRole=").append(idRole);
		sb.append(", role=").append(role);
		sb.append('}');
		return sb.toString();
	}
}
