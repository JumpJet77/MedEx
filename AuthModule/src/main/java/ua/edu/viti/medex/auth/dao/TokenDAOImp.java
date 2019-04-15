package ua.edu.viti.medex.auth.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.viti.medex.auth.entities.Tokens;

import java.util.Date;

@Repository
@Transactional
public class TokenDAOImp implements PersistentTokenRepository {

	@Autowired
	RolesDAOImpl rolesDAO;
	@Autowired
	SessionFactory sessionFactory;
	private Logger logger = LogManager.getLogger(TokenDAOImp.class);

	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		Tokens tokensToPersist = new Tokens();
		tokensToPersist.setUser(token.getUsername());
		tokensToPersist.setSeries(token.getSeries());
		tokensToPersist.setToken(token.getTokenValue());
		tokensToPersist.setLastUsed(token.getDate());
		logger.error(tokensToPersist.toString());
		logger.error(token.getUsername());
		sessionFactory.getCurrentSession().persist(tokensToPersist);
	}

	@Override
	public void updateToken(String series, String tokenValue, Date lastUsed) {
		Session session = sessionFactory.getCurrentSession();
		Tokens token = session.get(Tokens.class, series);
		token.setToken(tokenValue);
		token.setLastUsed(lastUsed);

	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {

		Tokens tokens = sessionFactory.getCurrentSession().get(Tokens.class, seriesId);
		if (tokens != null) {
			return new PersistentRememberMeToken(tokens.getUser(), tokens.getSeries(), tokens.getToken(), tokens.getLastUsed());
		}
		return null;
	}

	@SuppressWarnings("JpaQlInspection")
	@Override
	public void removeUserTokens(String username) {
		sessionFactory.getCurrentSession().createQuery("delete from tokens"
				+ " where email=:userToDelete")
				.setParameter("userToDelete", username).executeUpdate();
	}
}
