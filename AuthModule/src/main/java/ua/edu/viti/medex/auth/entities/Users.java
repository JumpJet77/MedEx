package ua.edu.viti.medex.auth.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * @author Ihor Dovhoshliubnyi
 * POJO representation for User entity in DB
 * User have own id, email as username (should be changed in future releases, as not good for military purposes),
 * password, and Collection of Roles objects.
 * Password encrypted with  default Spring Security BCrypt encryptor
 * Email pattern checked when interact with entity (something(.somethingelse)@something.domain)
 * Collection of roles for user consists of id user and certain role id, which user should have
 * (Relation Many to Many due to Spring Security guidelines)
 * @see ua.edu.viti.medex.auth.entities.Roles
 */

@SuppressWarnings("WeakerAccess")
@Entity(name = "users")
public class Users implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user", updatable = false, nullable = false, unique = true)
	private Long idUser;
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	@Column(name = "password", nullable = false)
	private String password;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(
					name = "id_user", referencedColumnName = "id_user"),
			inverseJoinColumns = @JoinColumn(
					name = "role_id", referencedColumnName = "role_id"))
	private Collection<Roles> roles;

	public Users() {
		this.email = "";
		this.password = "";
		this.roles = null;
	}

	public Users(String email, String password, Set<Roles> roles) {
		this.email = email;
		this.password = password;
		this.roles = roles;
	}

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

	public Collection<Roles> getCollectionRoles() {
		return roles;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Users)) return false;
		Users users = (Users) o;
		return idUser.equals(users.idUser) &&
				getEmail().equals(users.getEmail()) &&
				getPassword().equals(users.getPassword()) &&
				Objects.equals(getRoles(), users.getRoles());
	}

	@Override
	public int hashCode() {
		return Objects.hash(idUser, getEmail(), getPassword(), getRoles());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("Users{");
		builder.append(" idUser= " + idUser);
		builder.append(", email= '" + email + '\'');
		builder.append(", password= '" + password + '\'');
		builder.append(", roles= " + getRoles());
		builder.append('}');
		return builder.toString();
	}
}
