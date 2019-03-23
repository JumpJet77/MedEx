package ua.edu.viti.medex.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class SecurityRealm extends AuthorizingRealm {

	private static final Logger logger = LogManager.getLogger(SecurityRealm.class);

	//TODO: Replace correct registry
	private static ServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
			.configure()
			.build();
	private static SessionFactory sessionFactory = new MetadataSources(standardRegistry).addAnnotatedClass(AbstractUser.class)
			.buildMetadata()
			.buildSessionFactory();
	private static Session session = sessionFactory.openSession();


	public SecurityRealm() {
		setName("SecurityRealm");
		setCredentialsMatcher(new HashedCredentialsMatcher("SHA-256"));
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Long credentialId = (Long) principals.fromRealm(getName()).iterator().next();
		AbstractUser abstractUser = session.find(AbstractUser.class, 4L);
		if (abstractUser != null) {
			return new SimpleAuthorizationInfo();
		} else {
			return null;
		}
	}

	//TODO: replace straight HIBERNATE with correct DAO
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken authctoken = (UsernamePasswordToken) token;
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<AbstractUser> criteria = builder.createQuery(AbstractUser.class);

		Root<AbstractUser> myObjectRoot = criteria.from(AbstractUser.class);

		Predicate likeRestriction = builder.and(
				builder.like(myObjectRoot.get("login"), ((UsernamePasswordToken) token).getUsername())
		);

		criteria.select(myObjectRoot).where(likeRestriction);

		TypedQuery<AbstractUser> typedQuery = session.createQuery(criteria);
		AbstractUser abstractUser = typedQuery.getSingleResult();
		logger.error(abstractUser);
		if (abstractUser != null) {
			return new SimpleAuthenticationInfo(abstractUser.getId(), abstractUser.getPassword(), abstractUser.getLogin());
		} else {
			return null;
		}
	}
}
