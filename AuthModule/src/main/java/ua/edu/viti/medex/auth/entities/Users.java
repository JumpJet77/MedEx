package ua.edu.viti.medex.auth.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("WeakerAccess")
@Entity(name = "users")
public class Users implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user", updatable = false, nullable = false, unique = true)
	private Long idUser;


	//TODO: create another login credential instead of email


	@Column(name = "email", nullable = false, unique = true)
	private String email;
	@Column(name = "password", nullable = false)
	private String password;

	public Long getId() {
		return idUser;
	}

	public void setId(Long id) {
		this.idUser = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String username) {
		this.email = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Users{" +
				"id=" + idUser +
				", username='" + email + '\'' +
				", password='" + password + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Users users = (Users) o;
		return getEmail().equals(users.getEmail());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getEmail());
	}
}
