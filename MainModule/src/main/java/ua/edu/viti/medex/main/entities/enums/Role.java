package ua.edu.viti.medex.main.entities.enums;

/**
 * @author Ihor Dovhoshliubnyi
 * Enum for roles of app
 * Has prefix due to Spring Security guidelines
 */

public enum Role {

	ROLE_USER, ROLE_DOCTOR, ROLE_NURSE, ROLE_ADMIN, ROLE_ANONYMOUS, ROLE_DEPARTMENT_HEAD

}
