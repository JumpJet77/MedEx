package ua.edu.viti.medex.auth.config.secutiry;

import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.viti.medex.auth.dao.UsersDAOImpl;
import ua.edu.viti.medex.auth.entities.Users;

/**
 * @author Ihor Dovhoshliubnyi
 * User details service to load user from DB to perform login
 */
@Service
public class UsersDetailsService implements UserDetailsService {

	@Autowired
	UsersDAOImpl usersDAO;

	private Logger logger = LogManager.getLogger(UsersDetailsService.class);

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
			Users user = usersDAO.getUserByEmail(username);
			User.UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(username)
					.disabled(false)
					.password(user.getPassword())
					.authorities(user.getRoles());
			return builder.build();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
