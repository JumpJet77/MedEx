package ua.edu.viti.medex.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class Main {

	private static final Logger LOGGER = LogManager.getLogger(Main.class.getName());

	public static void main(String[] args) {
		LOGGER.info("Hello world!");

		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);

		Subject currentUser = SecurityUtils.getSubject();

		Session session = currentUser.getSession();
		session.setAttribute("someKey", "aValue");
		String value = (String) session.getAttribute("someKey");
		if (value.equals("aValue")) {
			LOGGER.info("Retrieved the correct value! [" + value + "]");
		}

		// let's login the current user so we can check against roles and permissions:
		if (!currentUser.isAuthenticated()) {
			UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
			token.setRememberMe(true);
			try {
				currentUser.login(token);
			} catch (UnknownAccountException uae) {
				LOGGER.info("There is no user with username of " + token.getPrincipal());
			} catch (IncorrectCredentialsException ice) {
				LOGGER.info("Password for account " + token.getPrincipal() + " was incorrect!");
			} catch (LockedAccountException lae) {
				LOGGER.info("The account for username " + token.getPrincipal() + " is locked.  " +
						"Please contact your administrator to unlock it.");
			}
			// ... catch more exceptions here (maybe custom ones specific to your application?
			catch (AuthenticationException ae) {
				//unexpected condition?  error?
			}
		}

		//say who they are:
		//print their identifying principal (in this case, a username):
		LOGGER.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");

		//test a role:
		if (currentUser.hasRole("schwartz")) {
			LOGGER.info("May the Schwartz be with you!");
		} else {
			LOGGER.info("Hello, mere mortal.");
		}

		//test a typed permission (not instance-level)
		if (currentUser.isPermitted("lightsaber:weild")) {
			LOGGER.info("You may use a lightsaber ring.  Use it wisely.");
		} else {
			LOGGER.info("Sorry, lightsaber rings are for schwartz masters only.");
		}

		//a (very powerful) Instance Level permission:
		if (currentUser.isPermitted("winnebago:drive:eagle5")) {
			LOGGER.info("You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  " +
					"Here are the keys - have fun!");
		} else {
			LOGGER.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
		}

		//all done - log out!
		currentUser.logout();

		System.exit(0);
	}

}
