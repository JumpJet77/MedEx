package ua.edu.viti.medex.main;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Main {

	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("pasha"));
	}

}
