package oi.github.brunotorres_be.libraryapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //habilitar a auditoria
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}