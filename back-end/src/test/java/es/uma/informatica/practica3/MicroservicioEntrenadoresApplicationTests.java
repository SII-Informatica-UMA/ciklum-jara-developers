package es.uma.informatica.practica3;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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
import es.uma.informatica.practica3.dtos.MensajeDTO;
import es.uma.informatica.practica3.dtos.MensajeNuevoDTO;
import es.uma.informatica.practica3.entidades.Entrenador;
import es.uma.informatica.practica3.entidades.Mensaje;
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

	private RequestEntity<Void> get(String scheme, String host, int port, String path, String requestParam) {
		URI uri = uriWithParam(scheme, host,port, path, requestParam);
		System.out.println("La uri montada es: " + uri.toString());
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

	@Nested
	@DisplayName("cuando la base de datos tiene datos")
	public class BaseDatosConDatos {

		@BeforeEach
		public void insertarDatos() {

			var trainer = new Entrenador();
			trainer.setIdUsuario(10L);
			trainer.setDni("1111111K");
			trainer.setExperiencia("Experiencia elevada");
			trainer.setIdCentro(1L);
			entrenadorRepo.save(trainer);

			var mensaje1 = new Mensaje();
			mensaje1.setAsunto("Asunto 1");
			mensaje1.setEntrenador(trainer);
			var mensaje2 = new Mensaje();
			mensaje2.setAsunto("Asunto 2");
			mensaje2.setEntrenador(trainer);
			mensajeRepo.save(mensaje1);
			mensajeRepo.save(mensaje2);

			// PREGUNTARLE AL PROFESOR POR QUÉ NO GUARDA LOS MENSAJES EN LA BASE DE DATOS ???

		}

		@Test
		@DisplayName("da error cuando inserta un entrenador que ya existe")
		public void insertaEntrenadorExistente() {

			EntrenadorDTO trainer = new EntrenadorDTO();
			trainer.setIdUsuario(10L);

			var peticion = post("http", "localhost",port, "/entrenador", "centro=1" ,trainer);

			// Invocamos al servicio REST 
			var respuesta = restTemplate.exchange(peticion,Void.class);

			// Comprobamos el resultado
			assertThat(respuesta.getStatusCode().value()).isEqualTo(500);
		}

		@Test
		@DisplayName("es correcto cuando se obtiene la lista de entrenadores")
		public void obtenerTodosEntrenadores() {
			var peticion = get("http", "localhost",port, "/entrenador", "centro=1");

			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<List<EntrenadorDTO>>() {});
			
			assertEquals(1, respuesta.getBody().size());
		}

		@Test
		@DisplayName("es correcto cuando se obtiene un unico entrenador")
		public void obtenerEntrenador() {
			var peticion = get("http", "localhost",port, "/entrenador/1");

			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<EntrenadorDTO>() {});
			
			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
			assertThat(respuesta.getBody().getDni()).isEqualTo("1111111K");
		}

		@Test
		@DisplayName("modificar un entrenador correctamente")
		public void modificarEntrenador() {
			EntrenadorDTO trainer = new EntrenadorDTO();
			trainer.setDni("00000000K");
			var peticion = put("http", "localhost",port, "/entrenador/1", trainer);

			var respuesta = restTemplate.exchange(peticion, Void.class);

			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
			assertThat(entrenadorRepo.findById(1L).get().getDni()).isEqualTo("00000000K");
		}

		@Test
		@DisplayName("da error al modificar un entrenador que no existe")
		public void modificarEntrenadorInexistente() {
			EntrenadorDTO trainer = new EntrenadorDTO();
			trainer.setDni("00000000K");
			var peticion = put("http", "localhost",port, "/entrenador/2", trainer);

			var respuesta = restTemplate.exchange(peticion, Void.class);

			assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
		}

		@Test
		@DisplayName("eliminar un entrenador correctamente")
		public void eliminarEntrenador() {

			Entrenador newTrainer = new Entrenador();
			newTrainer.setIdUsuario(20L);
			newTrainer.setDni("1111111K");
			newTrainer.setExperiencia("Experiencia elevada");
			newTrainer.setIdCentro(1L);
			entrenadorRepo.save(newTrainer);

			var peticion = delete("http", "localhost",port, "/entrenador/2");

			var respuesta = restTemplate.exchange(peticion, Void.class);

			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
			assertThat(entrenadorRepo.count()).isEqualTo(1);
		}

		@Test
		@DisplayName("es correcto cuando se obtiene la lista de mensajes")
		public void obtenerTodosMensajes() {
			var peticion = get("http", "localhost",port, "/mensaje/entrenador", "entrenador=1");

			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<List<MensajeDTO>>() {});
			
			assertEquals(2, respuesta.getBody().size());
		}

		@Test
		@DisplayName("es correcto cuando se obtiene un mensaje concreto")
		public void obtenerUnMensaje() {
			var peticion = get("http", "localhost",port, "/mensaje/entrenador/1");

			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<MensajeDTO>() {});
			
			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
			assertThat(respuesta.getBody().getAsunto()).isEqualTo("Asunto 1");
		}

		@Test
		@DisplayName("inserta correctamente un mensaje")
		public void insertaMensaje() {

			MensajeNuevoDTO newMessage = new MensajeNuevoDTO();
			newMessage.setAsunto("Asunto 3");
			newMessage.setContenido("Contenido del mensaje 3");

			var peticion = post("http", "localhost",port, "/mensaje/entrenador", "entrenador=1",newMessage);

			// Invocamos al servicio REST
			var respuesta = restTemplate.exchange(peticion,Void.class);

			// Comprobamos el resultado
			assertThat(respuesta.getStatusCode().value()).isEqualTo(201);

			List<Mensaje> allMensajes = mensajeRepo.findAll();
			assertThat(allMensajes).hasSize(3);
		}

		@Test
		@DisplayName("elimina un mensaje correctamente")
		public void eliminarMensaje() {

			var peticion = delete("http", "localhost",port, "/mensaje/entrenador/1");

			var respuesta = restTemplate.exchange(peticion, Void.class);

			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
			assertThat(mensajeRepo.count()).isEqualTo(1);
		}

		@Test
		@DisplayName("devuelve error al eliminar un mensaje que no existe")
		public void eliminarMensajeInexistente () {
			var peticion = delete("http", "localhost",port, "/mensaje/entrenador/3");

			var respuesta = restTemplate.exchange(peticion, Void.class);

			assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
		}

		@Test
		@DisplayName("da error cuando se trata de insertar un nuevo mensaje asociado a un entrenador inexistente")
		public void insertaIncorrectamenteMensaje() {

			MensajeNuevoDTO newMessage = new MensajeNuevoDTO();
			newMessage.setAsunto("Asunto 3");
			newMessage.setContenido("Contenido del mensaje 3");

			var peticion = post("http", "localhost",port, "/mensaje/entrenador", "entrenador=2",newMessage);

			// Invocamos al servicio REST
			var respuesta = restTemplate.exchange(peticion,Void.class);

			// Comprobamos el resultado
			assertThat(respuesta.getStatusCode().value()).isEqualTo(404);

			List<Mensaje> allMensajes = mensajeRepo.findAll();
			assertThat(allMensajes).hasSize(2);
		}

		@Test
		@DisplayName("devuelve error al obtener un mensaje que no existe")
		public void obtenerMensajeInexistente () {
			var peticion = get("http", "localhost",port, "/mensaje/entrenador/3");

			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<MensajeDTO>() {});
			
			assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
		}
	}


	
}
