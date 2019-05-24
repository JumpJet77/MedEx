package ua.edu.viti.medex.auth.config.secutiry;

import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.viti.medex.main.dao.PersonDAOImpl;
import ua.edu.viti.medex.main.entities.humans.Person;

/**
 * @author Ihor Dovhoshliubnyi
 * User details service to load user from DB to perform login
 */
@Service
public class PersonDetailsService implements UserDetailsService {

	@Autowired
	PersonDAOImpl personDAO;


	@Autowired
	@Qualifier("mainSessionFactory")
	SessionFactory mainSessionFactory;

	private Logger logger = LogManager.getLogger(PersonDetailsService.class);

	/**
	 * method for loading user from db by username
	 *
	 * @param username email of persisted user
	 * @return Spring Securities User details
	 * @throws UsernameNotFoundException if persisted user not found
	 */
	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			Person person = personDAO.getPersonByEmail(username);
			User.UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(username)
					.disabled(false)
					.password(person.getPassword())
					.authorities(person.getRoles());
			return builder.build();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
