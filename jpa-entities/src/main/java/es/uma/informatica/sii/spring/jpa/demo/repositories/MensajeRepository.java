package es.uma.informatica.sii.spring.jpa.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.uma.informatica.sii.spring.jpa.demo.entities.Entrenador;
import es.uma.informatica.sii.spring.jpa.demo.entities.Mensaje;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
	// Este sería el simil del metodo getter() de los mensajes de un entrenador concreto
	List<Mensaje> findByEntrenador(Entrenador e);

	// Metodos auxiliares
	List<Mensaje> findByAsunto(String asunto);
	List<Mensaje> findByContenido(String contenido);	

	// Por si interesa encontrar un mensaje concreto por su id
	@Query("select m from Mensaje m where m.idMensaje = :id")
	List<Mensaje> miConsultaCompleja(@Param("id") Long id);
}
