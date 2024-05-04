package es.uma.informatica.practica3;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import es.uma.informatica.practica3.dtos.IngredienteDTO;
import es.uma.informatica.practica3.dtos.ProductoDTO;
import es.uma.informatica.practica3.entidades.Ingrediente;
import es.uma.informatica.practica3.entidades.Producto;
import es.uma.informatica.practica3.repositorios.IngredienteRepository;
import es.uma.informatica.practica3.repositorios.ProductoRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DisplayName("En el servicio de productos")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class MicroservicioEntrenadoresApplicationTests {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Value(value="${local.server.port}")
	private int port;
	
	@Autowired
	private IngredienteRepository ingredienteRepo;
	
	@Autowired
	private ProductoRepository productoRepo;
	
	
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
	
	private RequestEntity<Void> get(String scheme, String host, int port, String path) {
		URI uri = uri(scheme, host,port, path);
		var peticion = RequestEntity.get(uri)
			.accept(MediaType.APPLICATION_JSON)
			.build();
		return peticion;
	}
	
	private RequestEntity<Void> delete(String scheme, String host, int port, String path) {
		URI uri = uri(scheme, host,port, path);
		var peticion = RequestEntity.delete(uri)
			.build();
		return peticion;
	}
	
	private <T> RequestEntity<T> post(String scheme, String host, int port, String path, T object) {
		URI uri = uri(scheme, host,port, path);
		var peticion = RequestEntity.post(uri)
			.contentType(MediaType.APPLICATION_JSON)
			.body(object);
		return peticion;
	}
	
	private <T> RequestEntity<T> put(String scheme, String host, int port, String path, T object) {
		URI uri = uri(scheme, host,port, path);
		var peticion = RequestEntity.put(uri)
			.contentType(MediaType.APPLICATION_JSON)
			.body(object);
		return peticion;
	}
	
	private void compruebaCampos(Ingrediente expected, Ingrediente actual) {
		assertThat(actual.getNombre()).isEqualTo(expected.getNombre());
	}
	
	private void compruebaCampos(Producto expected, Producto actual) {	
		assertThat(actual.getNombre()).isEqualTo(expected.getNombre());
		assertThat(actual.getDescripcion()).isEqualTo(expected.getDescripcion());
		assertThat(actual.getIngredientes()).isEqualTo(expected.getIngredientes());
	}

	@Test
	@DisplayName("Test global Main")
	public void testGlobal() {
		MicroservicioEntrenadoresApplication.main(new String[]{});
	}
	
	@Nested
	@DisplayName("cuando la base de datos está vacía")
	public class BaseDatosVacia {

		@Test
		@DisplayName("devuelve error al acceder a un ingrediente concreto")
		public void errorConIngredienteConcreto() {
			var peticion = get("http", "localhost",port, "/ingredientes/1");
			
			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<IngredienteDTO>() {});
			
			assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
		}
		
		@Test
		@DisplayName("devuelve una lista vacía de productos")
		public void devuelveListaVaciaProductos() {
			var peticion = get("http", "localhost",port, "/productos");
			
			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<List<ProductoDTO>>() {});
			
			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
			assertThat(respuesta.getBody()).isEmpty();
		}
		
		@Test
		@DisplayName("inserta correctamente un ingrediente")
		public void insertaIngrediente() {
			
			// Preparamos el ingrediente a insertar
			var ingrediente = IngredienteDTO.builder()
									.nombre("Chorizo")
									.build();
			// Preparamos la petición con el ingrediente dentro
			var peticion = post("http", "localhost",port, "/ingredientes", ingrediente);
			
			// Invocamos al servicio REST 
			var respuesta = restTemplate.exchange(peticion,Void.class);
			
			// Comprobamos el resultado
			assertThat(respuesta.getStatusCode().value()).isEqualTo(201);
			assertThat(respuesta.getHeaders().get("Location").get(0))
				.startsWith("http://localhost:"+port+"/ingredientes");
		
			List<Ingrediente> ingredientesBD = ingredienteRepo.findAll();
			assertThat(ingredientesBD).hasSize(1);
			assertThat(respuesta.getHeaders().get("Location").get(0))
				.endsWith("/"+ingredientesBD.get(0).getId());
			compruebaCampos(ingrediente.ingrediente(), ingredientesBD.get(0));
		}
	}


	@Nested
	@DisplayName("cuando la base de datos no está vacía")
	public class BaseDatosNoVacia {

		@BeforeEach
		public void setUp () {
			/* Las pruebas de los beforeEach se hacen mediante la base de datos directamente con el
			repository*/
			Ingrediente ingrediente1 = new Ingrediente();
            ingrediente1.setNombre("Ingrediente 1");

            Ingrediente ingrediente2 = new Ingrediente();
            ingrediente2.setNombre("Ingrediente 2");

            Ingrediente ingrediente3 = new Ingrediente();
            ingrediente3.setNombre("Ingrediente 3");

            // Crear productos
            Producto producto1 = new Producto();
            producto1.setNombre("Producto 1");
            producto1.setDescripcion("Descripción del producto 1");
			Set<Ingrediente> ingredientesProducto1 = new HashSet<>();
            ingredientesProducto1.add(ingrediente1);
            ingredientesProducto1.add(ingrediente2);
            ingredientesProducto1.add(ingrediente3);
            producto1.setIngredientes(ingredientesProducto1);

            Producto producto2 = new Producto();
            producto2.setNombre("Producto 2");
            producto2.setDescripcion("Descripción del producto 2");
			Set<Ingrediente> ingredientesProducto2 = new HashSet<>();
            ingredientesProducto2.add(ingrediente1);
            ingredientesProducto2.add(ingrediente2);
            ingredientesProducto2.add(ingrediente3);
            producto2.setIngredientes(ingredientesProducto2);

            Producto producto3 = new Producto();
            producto3.setNombre("Producto 3");
            producto3.setDescripcion("Descripción del producto 3");
			Set<Ingrediente> ingredientesProducto3 = new HashSet<>();
            ingredientesProducto3.add(ingrediente1);
            ingredientesProducto3.add(ingrediente2);
            ingredientesProducto3.add(ingrediente3);
            producto3.setIngredientes(ingredientesProducto3);

			ingredienteRepo.save(ingrediente1);
			ingredienteRepo.save(ingrediente2);
			ingredienteRepo.save(ingrediente3);
			productoRepo.save(producto1);
			productoRepo.save(producto2);
			productoRepo.save(producto3);
		}

		@Test
		@DisplayName("devuelve una lista no vacía de productos")
		public void devuelveListaVaciaProductos() {
			var peticion = get("http", "localhost",port, "/productos");
			
			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<List<ProductoDTO>>() {});
			
			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
			assertThat(respuesta.getBody()).isNotEmpty();
		}

		@Test
		@DisplayName("añadir un producto a la lista")
		public void aniadirProductoLista() {
			var pr = ProductoDTO.builder()
					.nombre("Producto de ejemplo")
					.descripcion("Descripción del producto de ejemplo")
					.ingredientes(new HashSet<>())
					.build();

			var peticion = post("http", "localhost",port, "/productos", pr);
			
			var respuesta = restTemplate.exchange(peticion, Void.class);

			assertThat(respuesta.getStatusCode().value()).isEqualTo(201);
		}

		@Test
		@DisplayName("obtener producto concreto")
		public void productoConcreto() {
			var peticion = get("http", "localhost",port, "/productos/1");
			
			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<ProductoDTO>() {});
			
			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
		}

		@Test
		@DisplayName("devuelve correcto al acceder a un ingrediente concreto existente")
		public void correctoConIngredienteConcreto() {
			var peticion = get("http", "localhost",port, "/ingredientes/1");
			
			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<IngredienteDTO>() {});
			
			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
		}

		@Test
		@DisplayName("devuelve error al acceder a un ingrediente no existente")
		public void errorConIngredienteConcreto() {
			var peticion = get("http", "localhost",port, "/ingredientes/4");
			
			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<IngredienteDTO>() {});
			
			assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
		}

		@Test
		@DisplayName("el numero de ingredientes es correcto")
		public void correctoNumeroDeIngredientes() {
			var peticion = get("http", "localhost",port, "/ingredientes");

			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<List<IngredienteDTO>>() {});

			assertEquals(respuesta.getBody().size(),3);
		}

		@Test
		@DisplayName("actualiza ingrediente")
		public void actualizarIngrediente() {

			var ing = IngredienteDTO.builder()
				.nombre("Ingrediente nuevo")
				.build();

			var peticion = put("http", "localhost",port, "/ingredientes/2", ing);

			var respuesta = restTemplate.exchange(peticion, Void.class);

			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
		}

		@Test
		@DisplayName("no eliminar ingrediente")
		public void eliminarIngrediente() {
			var peticion = delete("http", "localhost",port, "/ingredientes/1");
			
			var respuesta = restTemplate.exchange(peticion, Void.class);
			
			List<Ingrediente> lI = ingredienteRepo.findAll();

			assertThat(respuesta.getStatusCode().value()).isEqualTo(500);
			assertThat(lI.size()).isEqualTo(3);
			
		}
	}

	@Nested
	@DisplayName("cuando la base de datos solo tiene ingredientes")
	public class BaseDatos1Ingrediente {

		@BeforeEach
		public void setUp () {
			
			Ingrediente ingrediente1 = new Ingrediente();
            ingrediente1.setNombre("Ingrediente 1");

            Ingrediente ingrediente2 = new Ingrediente();
            ingrediente2.setNombre("Ingrediente 2");

            Ingrediente ingrediente3 = new Ingrediente();
            ingrediente3.setNombre("Ingrediente 3");
			
			ingredienteRepo.save(ingrediente1);
			ingredienteRepo.save(ingrediente2);
			ingredienteRepo.save(ingrediente3);
		}

		@Test
		@DisplayName("eliminar ingrediente")
		public void eliminarIngrediente() {
			var peticion = delete("http", "localhost",port, "/ingredientes/1");
			
			var respuesta = restTemplate.exchange(peticion, Void.class);
			
			List<Ingrediente> lI = ingredienteRepo.findAll();

			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
			assertThat(lI.size()).isEqualTo(2);
			
		}

		@Test
		@DisplayName("añadir existente")
		public void aniadirExistente() {

			var ing = IngredienteDTO.builder()
				.nombre("Ingrediente 1")
				.build();

			var peticion = post("http", "localhost",port, "/ingredientes", ing);
			
			var respuesta = restTemplate.exchange(peticion, Void.class);

			assertThat(respuesta.getStatusCode().value()).isEqualTo(409);
		}
	}

	@Nested
	@DisplayName("cuando la base de datos tiene un unico producto")
	public class BaseDatos1Producto {

		@BeforeEach
		public void setUp () {
			
			Producto producto1 = new Producto();
            producto1.setNombre("Producto 1");
            producto1.setDescripcion("Descripción del producto 1");
			productoRepo.save(producto1);
		}

		@Test
		@DisplayName("actualizar producto")
		public void actualizarExistente() {

			var pr = ProductoDTO.builder()
				.nombre("Producto nuevo")
				.build();

			var peticion = put("http", "localhost",port, "/productos/1", pr);

			var respuesta = restTemplate.exchange(peticion, Void.class);

			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
		}

		@Test
		@DisplayName("no actualizar producto")
		public void actualizarNoExistente() {

			var pr = ProductoDTO.builder()
				.nombre("Producto nuevo")
				.build();

			var peticion = put("http", "localhost",port, "/productos/2", pr);

			var respuesta = restTemplate.exchange(peticion, Void.class);

			assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
		}

		@Test
		@DisplayName("eliminar producto")
		public void eliminarIngrediente() {
			var peticion = delete("http", "localhost",port, "/productos/1");
			
			var respuesta = restTemplate.exchange(peticion, Void.class);
			
			List<Producto> lI = productoRepo.findAll();

			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
			assertThat(lI.size()).isEqualTo(0);
			
		}

		@Test
		@DisplayName("añadir un producto existente")
		public void aniadirProductoLista() {
			var pr = ProductoDTO.builder()
					.nombre("Producto 1")
					.build();

			var peticion = post("http", "localhost",port, "/productos", pr);
			
			var respuesta = restTemplate.exchange(peticion, Void.class);

			assertThat(respuesta.getStatusCode().value()).isEqualTo(409);
		}
	}

	@Nested
	@DisplayName("cuando la base de datos tiene un unico producto y un unico ingrediente")
	public class BaseDatos1Producto1Ingrediente {

		@BeforeEach
		public void setUp () {

			Ingrediente ingrediente1 = new Ingrediente();
            ingrediente1.setNombre("Ingrediente 1");

            // Crear productos
            Producto producto1 = new Producto();
            producto1.setNombre("Producto 1");
            producto1.setDescripcion("Descripción del producto 1");
			Set<Ingrediente> ingredientesProducto1 = new HashSet<>();
            ingredientesProducto1.add(ingrediente1);
            producto1.setIngredientes(ingredientesProducto1);

			ingredienteRepo.save(ingrediente1);
			productoRepo.save(producto1);
		}

		@Test
		@DisplayName("añadir nuevo producto con ingrediente existente")
		public void nuevoProducto() {
			IngredienteDTO ingrediente1 = new IngredienteDTO();
            ingrediente1.setNombre("Ingrediente 1");
			Set<IngredienteDTO> newIngredientes = new HashSet<>();
			newIngredientes.add(ingrediente1);

			var pr = ProductoDTO.builder()
					.nombre("Producto nuevo")
					.descripcion("Descripción del producto nuevo")
					.ingredientes(newIngredientes)
					.build();

			var peticion = post("http", "localhost",port, "/productos", pr);
			
			var respuesta = restTemplate.exchange(peticion, Void.class);

			assertThat(respuesta.getStatusCode().value()).isEqualTo(201);
		}
	}
}
