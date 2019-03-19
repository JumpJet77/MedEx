package ua.edu.viti.medex.auth;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Credentials {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_credential", updatable = false, nullable = false)
	private Long idCredential;
	@Column(name = "login", nullable = false)
	private String username;
	@Column(name = "password", nullable = false)
	private String password;
	@Enumerated(EnumType.STRING)
	private Roles role;

	public Credentials() {
	}

	public Long getId() {
		return idCredential;
	}

	public void setId(Long id) {
		this.idCredential = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
		return "Credentials{" +
				"id=" + idCredential +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", role=" + role +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Credentials credentials = (Credentials) o;
		return getUsername().equals(credentials.getUsername());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getUsername());
	}
}
