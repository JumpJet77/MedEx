package ua.edu.viti.medex.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
	private static final Logger logger = LogManager.getLogger(Main.class);
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("credentials");
	private static EntityManager em = emf.createEntityManager();
	private static SecurityRealm realm = new SecurityRealm();
	private static Credentials credentials = new Credentials();

	public static void main(String[] args) {
		beginTransaction();
		logger.error(realm.doGetAuthenticationInfo(new UsernamePasswordToken("admin", "admin")));
		//createUser();
		//Credentials newCred = em.find(Credentials.class, 2L);
		//logger.info(newCred);
		closeTransaction();
	}

	private static void beginTransaction() {
		em.getTransaction().begin();
	}

	private static void closeTransaction() {
		em.getTransaction().commit();
		em.clear();
		em.flush();
		em.close();
	}

	private static void createUser() {
		credentials.setUsername("admin");
		credentials.setRole(Roles.ADMIN);

		RandomNumberGenerator rng = new SecureRandomNumberGenerator();
		Object salt = rng.nextBytes();

		String hashedPasswordBase64 = new Sha256Hash("admin", salt, 1024).toBase64();

		credentials.setPassword(hashedPasswordBase64);

		em.persist(credentials);
	}

}
