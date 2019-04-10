package ua.edu.viti.medex.auth.dao;

import java.util.List;

public interface IUsersDAO {

	List<Users> findAll();

	Users findById(Long id);

	Long signUp(Users user);

	Users findByEmail();

	void update(Users userToUpdate);

	void delete(Long id);
}
