package es.uma.informatica.practica3.repositorios;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.uma.informatica.practica3.entidades.*;

public interface EntrenadorRepository extends JpaRepository<Entrenador, Long> {
	// Es necesario para encontrar los entrenadores por su id
	Optional<Entrenador> findById(Long id);
	Optional<Entrenador> findByIdUsuario(Long id);

	// Metodos auxiliares
	List<Entrenador> findByTitulacion(String title);
	List<Entrenador> findByFechaBaja(Date fechabaja);
	List<Entrenador> findByEspecialidadOrderByFechaAlta(String esp);
	List<Entrenador> findByIdCentro(Long paramLong);	

	@Query("select e from Entrenador e where e.id = :id")
	List<Entrenador> miConsultaCompleja(@Param("id") Long id);

	@Query("SELECT e FROM Entrenador e WHERE YEAR(e.fechaNacimiento) = :anioNacimiento")
    List<Entrenador> findByAnioNacimiento(@Param("anioNacimiento") int anioNacimiento);

	// No hemos definido un metodo para obtener los mensajes de un entrenador ya que
	// hemos optado por la opción de tener un getter() de dicha estructura de datos en
	// la clase Entrenador.
}
