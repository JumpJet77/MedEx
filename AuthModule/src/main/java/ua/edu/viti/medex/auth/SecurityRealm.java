package ua.edu.viti.medex.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.Sha256CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SecurityRealm extends AuthorizingRealm {

	private static final Logger logger = LogManager.getLogger(SecurityRealm.class);
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("credentials");
	private static EntityManager em = emf.createEntityManager();


	public SecurityRealm() {
		setName("SecurityRealm");
		setCredentialsMatcher(new Sha256CredentialsMatcher());
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Long credentialId = (Long) principals.fromRealm(getName()).iterator().next();
		Credentials credentials = em.find(Credentials.class, 4L);
		if (credentials != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			return info;
		} else {
			return null;
		}
	}

	//TODO: replace straight HIBERNATE with correct DAO

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken authctoken = (UsernamePasswordToken) token;
		Credentials credentials = em.find(Credentials.class, 4L);
		if (credentials != null) {
			return new SimpleAuthenticationInfo(credentials.getId(), credentials.getPassword(), credentials.getUsername());
		} else {
			return null;
		}
	}
}
