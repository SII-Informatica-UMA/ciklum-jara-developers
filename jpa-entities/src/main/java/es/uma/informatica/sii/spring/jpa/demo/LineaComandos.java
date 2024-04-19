package es.uma.informatica.sii.spring.jpa.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import es.uma.informatica.sii.spring.jpa.demo.repositories.EntrenadorRepository;

@Component
public class LineaComandos implements CommandLineRunner {
	private EntrenadorRepository repository;
	public LineaComandos(EntrenadorRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {

		System.out.println("GENERANDO LAS INSTRUCCIONES DDL...");

	}

}
