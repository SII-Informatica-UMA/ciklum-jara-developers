package es.uma.informatica.practica3.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.uma.informatica.practica3.entidades.*;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
	// Este sería el simil del metodo getter() de los mensajes de un entrenador concreto
	// Revisar si el nombre esta bien puesto... para luego usar en el EntrenadorServicio.java
	List<Mensaje> findByEntrenadorId(Long paramLong);

	// Metodos auxiliares
	List<Mensaje> findByAsunto(String asunto);
	List<Mensaje> findByContenido(String contenido);	

	// Por si interesa encontrar un mensaje concreto por su id
	@Query("select m from Mensaje m where m.idMensaje = :id")
	List<Mensaje> miConsultaCompleja(@Param("id") Long id);
}
