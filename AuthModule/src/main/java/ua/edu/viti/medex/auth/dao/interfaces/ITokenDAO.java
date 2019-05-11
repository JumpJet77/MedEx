package ua.edu.viti.medex.auth.dao.interfaces;

import javassist.NotFoundException;
import ua.edu.viti.medex.auth.entities.Tokens;

/**
 * @author Ihor Dovhoshliubnyi
 * interface for Token persisting and management
 */

public interface ITokenDAO {
	Long addTokenData(String tokenToAdd);

	Tokens refreshToken(Tokens oldToken) throws NotFoundException;

	Long invalidateToken(Tokens tokenToInvalidate) throws NotFoundException;

	Tokens getTokenFromId(Long id) throws NotFoundException;

	Tokens getTokenFromEmail(String email) throws NotFoundException;
}
