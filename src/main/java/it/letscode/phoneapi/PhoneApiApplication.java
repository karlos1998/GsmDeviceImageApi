package it.letscode.phoneapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PhoneApiApplication {

	public static void main(String[] args) {
		System.out.println("PhoneApiApplication Started.");
		SpringApplication.run(PhoneApiApplication.class, args);
	}

}
