package ua.edu.viti.medex.auth.dao;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.viti.medex.auth.config.secutiry.SecurityConstants;
import ua.edu.viti.medex.auth.dao.interfaces.ITokenDAO;
import ua.edu.viti.medex.auth.entities.Tokens;
import ua.edu.viti.medex.main.dao.PersonDAOImpl;
import ua.edu.viti.medex.main.entities.Roles;
import ua.edu.viti.medex.main.entities.enums.Role;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * @author Ihor Dovhoshliubnyi
 * Implementation for management of jwt tokens
 * Use hibernate's session factory as persistent mechanism and users DAO for receiving user's information
 */
@Transactional(transactionManager = "hibernateTransactionManager", value = "hibernateTransactionManager")
@Service
public class TokenDAOImpl implements ITokenDAO {

	private final Logger logger = LogManager.getLogger(TokenDAOImpl.class);

	@Autowired
	@Qualifier("sessionFactory")
	SessionFactory sessionFactory;

	@Autowired
	PersonDAOImpl personDAO;

	/**
	 * Persists token into db as entity
	 *
	 * @param tokenToAdd Sting of Bearer token which should be persisted (with prefix before token itself)
	 * @return Id of new persisted token
	 */

	@Override
	public Long addTokenData(String tokenToAdd) {
		if ("".equals(tokenToAdd) || tokenToAdd == null) {
			return -1L;
		}
		byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();
		Jws<Claims> parsedToken = Jwts.parser()
				.setSigningKey(signingKey)
				.parseClaimsJws(tokenToAdd.replace(SecurityConstants.TOKEN_PREFIX, ""));

		String email = parsedToken
				.getBody()
				.getSubject();

		Date expiration = parsedToken
				.getBody()
				.getExpiration();
		Tokens token = new Tokens(tokenToAdd, email, expiration, true);
		sessionFactory.getCurrentSession().persist(token);
		return token.getIdToken();
	}

	/**
	 * refreshing token if current exp time < 15 min
	 *
	 * @param oldToken token which should be refreshed (as entity on DB, should be not expired)
	 * @return new persisted Token entity
	 * @throws NotFoundException in case of not found old token
	 */

	@Override
	public Tokens refreshToken(Tokens oldToken) throws NotFoundException {
		if (oldToken == null) {
			throw new NotFoundException("Token not found");
		}
		sessionFactory.getCurrentSession().delete(getTokenFromEmail(oldToken.getTokenOwnerEmail()));
		Tokens token = new Tokens();
		token.setTokenOwnerEmail(oldToken.getTokenOwnerEmail());
		Collection<Roles> roles = personDAO.getPersonByEmail(token.getTokenOwnerEmail()).getCollectionRoles();
		Iterator<Roles> iterator = roles.iterator();
		List<Role> roles1 = new ArrayList<>();
		while (iterator.hasNext()) {
			roles1.add(iterator.next().getRole());
		}
		byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();
		logger.error(Arrays.deepToString(roles.toArray()));
		Date expiration = new Date(System.currentTimeMillis() + 7200000);
		String newToken = Jwts.builder()
				.signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
				.setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
				.setIssuer(SecurityConstants.TOKEN_ISSUER)
				.setAudience(SecurityConstants.TOKEN_AUDIENCE)
				.setSubject(token.getTokenOwnerEmail())
				.setExpiration(expiration)
				.claim("role", roles1)
				.compact();

		String completeToken = SecurityConstants.TOKEN_PREFIX + newToken;
		token.setToken(completeToken);
		token.setExpiration(expiration);
		addTokenData(completeToken);
		return token;
	}

	/**
	 * Invalidates existing token in DB if Person makes logout
	 *
	 * @param tokenToInvalidate Token which should be invalidated (as DB entity)
	 * @return id of invalidated token
	 * @throws NotFoundException in case of not founding token
	 */

	@Override
	public Long invalidateToken(Tokens tokenToInvalidate) throws NotFoundException {
		Tokens token = getTokenFromId(tokenToInvalidate.getIdToken());
		if (token == null) {
			throw new NotFoundException("Token not found");
		}
		token.setExpiration(new Date(System.currentTimeMillis() - 1));
		sessionFactory.getCurrentSession().merge(token);
		return token.getIdToken();
	}

	/**
	 * Receive token entity from DB if it exists
	 *
	 * @param id id of token to receive
	 * @return Token entity itself
	 * @throws NotFoundException in case if Token not exists
	 */

	@Override
	public Tokens getTokenFromId(Long id) throws NotFoundException {
		Tokens token = sessionFactory.getCurrentSession().get(Tokens.class, id);
		if (token == null) {
			throw new NotFoundException("id not found");
		}
		return token;
	}

	/**
	 * Receive token entity from DB if it exists
	 *
	 * @param email email of token owner whose token should be received
	 * @return Token entity itself
	 * @throws NotFoundException in case if Token not exists
	 */

	@Override
	public Tokens getTokenFromEmail(String email) throws NotFoundException {
		CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
		CriteriaQuery<Tokens> criteria = cb.createQuery(Tokens.class);
		Root<Tokens> root = criteria.from(Tokens.class);
		Predicate likeRestriction = cb.and(
				cb.like(root.get("tokenOwnerEmail"), email)
		);
		criteria.select(root).where(likeRestriction);
		TypedQuery<Tokens> typedQuery = sessionFactory.getCurrentSession().createQuery(criteria);
		Tokens token = typedQuery.getSingleResult();
		if (token == null) {
			throw new NotFoundException("email not found");
		} else {
			return token;
		}
	}
}
