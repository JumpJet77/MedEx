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
import ua.edu.viti.medex.auth.dao.RolesDAOImpl;
import ua.edu.viti.medex.auth.entities.Roles;

@Service
public class RolesDetailsService implements UserDetailsService {

	@Autowired
	RolesDAOImpl rolesDAO;

	private Logger logger = LogManager.getLogger(RolesDetailsService.class);


	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			Roles roles = rolesDAO.getRoleByEmail(username);
			User.UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(username)
					.disabled(false)
					.password(roles.getUser().getPassword())
					.authorities(roles.getRole().toString());
			return builder.build();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
