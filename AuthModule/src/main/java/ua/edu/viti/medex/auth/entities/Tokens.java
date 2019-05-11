package ua.edu.viti.medex.auth.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author Ihor Dovhoshliubnyi
 * POJO for representing and maping in DB auth token entity
 * Token has it`s own id, email of owner date of expiration and validity field
 * each of this fields, can be extracted from token itself, but need class with field for persisting, and management
 */
@Entity(name = "tokens")
public class Tokens implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false, unique = true)
	private Long idToken;
	@Column(length = 500, nullable = false)
	private String token;
	@Column(nullable = false)
	private String tokenOwnerEmail;
	@Column(nullable = false)
	private Date expiration;
	@Column(nullable = false)
	private boolean isValid;

	public Tokens() {
		this.idToken = -1L;
		this.token = "";
		this.tokenOwnerEmail = "";
		this.isValid = false;
	}

	public Tokens(String token, String tokenOwnerEmail, Date expiration, boolean isValid) {
		this.token = token;
		this.tokenOwnerEmail = tokenOwnerEmail;
		this.expiration = expiration;
		this.isValid = isValid;
	}

	public Long getIdToken() {
		return idToken;
	}

	public void setIdToken(Long idToken) {
		this.idToken = idToken;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenOwnerEmail() {
		return tokenOwnerEmail;
	}

	public void setTokenOwnerEmail(String tokenOwnerEmail) {
		this.tokenOwnerEmail = tokenOwnerEmail;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean valid) {
		isValid = valid;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Tokens)) return false;
		Tokens tokens = (Tokens) o;
		return isValid() == tokens.isValid() &&
				getIdToken().equals(tokens.getIdToken()) &&
				getToken().equals(tokens.getToken()) &&
				getTokenOwnerEmail().equals(tokens.getTokenOwnerEmail()) &&
				getExpiration().equals(tokens.getExpiration());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getIdToken(), getToken(), getTokenOwnerEmail(), getExpiration(), isValid());
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Tokens{");
		sb.append("idToken=").append(idToken);
		sb.append(", token='").append(token).append('\'');
		sb.append(", tokenOwnerEmail='").append(tokenOwnerEmail).append('\'');
		sb.append(", expiration=").append(expiration);
		sb.append(", isValid=").append(isValid);
		sb.append('}');
		return sb.toString();
	}
}
