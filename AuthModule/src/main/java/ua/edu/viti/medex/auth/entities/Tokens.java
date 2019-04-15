package ua.edu.viti.medex.auth.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity(name = "tokens")
public class Tokens {

	@Id
	@Column(name = "series", nullable = false)
	private String series;

	@Column(name = "email", nullable = false)
	private String user;

	@Column(name = "token", nullable = false)
	private String token;

	@Column(name = "last_used", nullable = false)
	private Date lastUsed;

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getLastUsed() {
		return lastUsed;
	}

	public void setLastUsed(Date lastUsed) {
		this.lastUsed = lastUsed;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Tokens tokens = (Tokens) o;
		return getSeries().equals(tokens.getSeries()) &&
				getUser().equals(tokens.getUser()) &&
				getToken().equals(tokens.getToken()) &&
				getLastUsed().equals(tokens.getLastUsed());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getSeries(), getUser(), getToken(), getLastUsed());
	}

	@Override
	public String toString() {
		return "Tokens{" +
				"series='" + series + '\'' +
				", user=" + user +
				", token='" + token + '\'' +
				", lastUsed=" + lastUsed +
				'}';
	}
}
