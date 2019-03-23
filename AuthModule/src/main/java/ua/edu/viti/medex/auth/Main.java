package ua.edu.viti.medex.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class Main {
	private static final Logger logger = LogManager.getLogger(Main.class);
	private static final SecurityRealm realm = new SecurityRealm();
	private static Configuration configuration = new Configuration()
			.addAnnotatedClass(AbstractUser.class)
			.configure();
	private static StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
			.applySettings(configuration.getProperties())
			.build();
	private static SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	private static Session session = sessionFactory.openSession();
	private static AbstractUser abstractUser = new AbstractUser();

	public static void main(String[] args) {
		logger.error(realm.doGetAuthenticationInfo(new UsernamePasswordToken("admin", "admin")).getCredentials());
		createUser();
	}

	private static void createUser() {
		abstractUser.setLogin("puki");
		abstractUser.setRole(Roles.DOCTOR);
		RandomNumberGenerator rng = new SecureRandomNumberGenerator();
		Object salt = rng.nextBytes();

		String hashedPasswordBase64 = new Sha256Hash("puki", salt, 1024).toBase64();

		abstractUser.setPassword(hashedPasswordBase64);
		session.beginTransaction();
		session.persist(abstractUser);
		session.getTransaction().commit();
	}

	private static void dropUser() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<AbstractUser> criteria = builder.createQuery(AbstractUser.class);

		Root<AbstractUser> myObjectRoot = criteria.from(AbstractUser.class);

		Predicate likeRestriction = builder.and(
				builder.like(myObjectRoot.get("login"), "user")
		);

		criteria.select(myObjectRoot).where(likeRestriction);

		TypedQuery<AbstractUser> typedQuery = session.createQuery(criteria);
		AbstractUser dropCred = typedQuery.getSingleResult();
		logger.error(dropCred);
	}

}
