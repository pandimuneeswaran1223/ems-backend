package com.project.ems_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EmsBackendApplication {

	public static void main(String[] args) {


//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		System.out.println(encoder.matches("Temp@1234",
//				"$2a$10$ifh6OeSXjq65aEz6XQTLRO19baywvoaDFsa.8V6PnTXz6WM3YI2/y"));



//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		System.out.println(encoder.encode("admin123"));
		SpringApplication.run(EmsBackendApplication.class, args);


	}

}
