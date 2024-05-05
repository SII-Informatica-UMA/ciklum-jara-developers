package es.uma.informatica.practica3;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;

import es.uma.informatica.practica3.dtos.EntrenadorDTO;
import es.uma.informatica.practica3.entidades.Entrenador;
import es.uma.informatica.practica3.repositorios.EntrenadorRepository;
import es.uma.informatica.practica3.repositorios.MensajeRepository;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DisplayName("En el servicio de entrenadores y sus mensajes,")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class MicroservicioEntrenadoresApplicationTests {
	
	@Autowired
	private TestRestTemplate restTemplate;

	// Para poder extraer el puerto de nuestra maquina donde Spring ha lanzado las pruebas...
	@Value(value="${local.server.port}")
	private int port;

	@Autowired
	private EntrenadorRepository entrenadorRepo;

	@Autowired
	private MensajeRepository mensajeRepo;

	// Extraido de la solucion del profesor del CV sobre el taller de pruebas de jUnit
	private URI uri(String scheme, String host, int port, String ...paths) {
		UriBuilderFactory ubf = new DefaultUriBuilderFactory();
		UriBuilder ub = ubf.builder()
				.scheme(scheme)
				.host(host).port(port);
		for (String path: paths) {
			ub = ub.path(path);
		}
		return ub.build();
	}

	// Para aquellas consultas que requieran de un @RequestParam
	private URI uriWithParam(String scheme, String host, int port, String paths, String rP) {
		UriBuilderFactory ubf = new DefaultUriBuilderFactory();
		UriBuilder ub = ubf.builder()
				.scheme(scheme)
				.host(host).port(port).path(paths);

		String[] partes = rP.split("=");
		ub.queryParam(partes[0], partes[1]);
		System.out.println("La peticion post es: " + ub.toUriString());
		return ub.build();
	}

	// Extraido de la solucion del profesor del CV sobre el taller de pruebas de jUnit
	private RequestEntity<Void> get(String scheme, String host, int port, String path) {
		URI uri = uri(scheme, host,port, path);
		var peticion = RequestEntity.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.build();
		return peticion;
	}

	// Extraido de la solucion del profesor del CV sobre el taller de pruebas de jUnit
	private RequestEntity<Void> delete(String scheme, String host, int port, String path) {
		URI uri = uri(scheme, host,port, path);
		var peticion = RequestEntity.delete(uri)
				.build();
		return peticion;
	}

	// Extraido de la solucion del profesor del CV sobre el taller de pruebas de jUnit
	private <T> RequestEntity<T> post(String scheme, String host, int port, String path, T object) {
		URI uri = uri(scheme, host,port, path);
		var peticion = RequestEntity.post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.body(object);
		return peticion;
	}

	private <T> RequestEntity<T> post(String scheme, String host, int port, String path, String requestParam, T object) {
		URI uri = uriWithParam(scheme, host,port, path, requestParam);
		var peticion = RequestEntity.post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.body(object);
		return peticion;
	}

	// Extraido de la solucion del profesor del CV sobre el taller de pruebas de jUnit
	private <T> RequestEntity<T> put(String scheme, String host, int port, String path, T object) {
		URI uri = uri(scheme, host,port, path);
		var peticion = RequestEntity.put(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.body(object);
		return peticion;
	}

	@Nested
	@DisplayName("cuando la base de datos está vacía")
	public class BaseDatosVacia {

		@Test
		@DisplayName("error al obtener un entrenador concreto")
		public void errorAlObtenerEntrenadorConcreto() {
			var peticion = get("http", "localhost",port, "/entrenador/1");

			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<EntrenadorDTO>() {});

			assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
		}
		
		@Test
		@DisplayName("devuelve error al modificar un entrenador que no existe")
		public void modificarEntrenadorInexistente () {
			EntrenadorDTO trainer = new EntrenadorDTO();
			trainer.setIdUsuario(123L);
			var peticion = put("http", "localhost",port, "/entrenador/1", trainer);

			var respuesta = restTemplate.exchange(peticion, Void.class);

			assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
		}

		@Test
		@DisplayName("devuelve error al eliminar un entrenador que no existe")
		public void eliminarEntrenadorInexistente () {
			var peticion = delete("http", "localhost",port, "/entrenador/1");

			var respuesta = restTemplate.exchange(peticion, Void.class);

			assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
		}

		@Test
		@DisplayName("inserta correctamente un entrenador")
		public void insertaEntrenador() {

			EntrenadorDTO trainer = new EntrenadorDTO();
			trainer.setIdUsuario(123L);
			trainer.setEspecialidad("Especialidad de prueba");
			
			var peticion = post("http", "localhost",port, "/entrenador", "centro=1" ,trainer);

			var respuesta = restTemplate.exchange(peticion,Void.class);

			assertThat(respuesta.getStatusCode().value()).isEqualTo(201);
			assertThat(respuesta.getHeaders().get("Location").get(0))
			.startsWith("http://localhost:"+port+"/entrenador");

			List<Entrenador> allTrainers = entrenadorRepo.findAll();
			assertThat(allTrainers).hasSize(1);
			assertThat(respuesta.getHeaders().get("Location").get(0))
			.endsWith("/"+allTrainers.get(0).getId());
		}
	}
}
