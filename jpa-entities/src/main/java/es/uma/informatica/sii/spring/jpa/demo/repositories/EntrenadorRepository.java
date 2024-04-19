package es.uma.informatica.sii.spring.jpa.demo.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.uma.informatica.sii.spring.jpa.demo.entities.Entrenador;

public interface EntrenadorRepository extends JpaRepository<Entrenador, Long> {
	List<Entrenador> findByTitulacion(String title);
	List<Entrenador> findByFechaBaja(Date fechabaja);
	List<Entrenador> findByEspecialidadOrderByFechaAlta(String esp);
	List<Entrenador> findByIdCentro(Long paramLong);	

	@Query("select e from Entrenador e where e.id = :id")
	List<Entrenador> miConsultaCompleja(@Param("id") Long id);

	@Query("SELECT e FROM Entrenador e WHERE YEAR(e.fechaNacimiento) = :anioNacimiento")
    List<Entrenador> findByAnioNacimiento(@Param("anioNacimiento") int anioNacimiento);
}
