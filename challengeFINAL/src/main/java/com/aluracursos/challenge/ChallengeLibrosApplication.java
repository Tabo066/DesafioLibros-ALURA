package com.aluracursos.challenge;

import com.aluracursos.challenge.principal.Principal;
import com.aluracursos.challenge.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeLibrosApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepo;
	public static void main(String[] args) {
		SpringApplication.run(ChallengeLibrosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(libroRepo);
		principal.muestraElMenu();

	}
}
