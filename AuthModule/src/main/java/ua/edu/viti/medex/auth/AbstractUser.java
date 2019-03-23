package ua.edu.viti.medex.auth;

import javax.persistence.*;
import java.util.Objects;

@SuppressWarnings("WeakerAccess")
@Entity
public class AbstractUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user", updatable = false, nullable = false)
	private Long idUser;
	@Column(name = "login", nullable = false)
	private String login;
	@Column(name = "password", nullable = false)
	private String password;
	@Enumerated(EnumType.STRING)
	private Roles role;

	public AbstractUser() {
	}

	public Long getId() {
		return idUser;
	}

	public void setId(Long id) {
		this.idUser = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String username) {
		this.login = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "AbstractUser{" +
				"id=" + idUser +
				", username='" + login + '\'' +
				", password='" + password + '\'' +
				", role=" + role +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AbstractUser abstractUser = (AbstractUser) o;
		return getLogin().equals(abstractUser.getLogin());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getLogin());
	}
}
