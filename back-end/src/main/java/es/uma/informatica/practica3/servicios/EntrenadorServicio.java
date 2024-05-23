package es.uma.informatica.practica3.servicios;

import java.util.List;
import java.util.Optional;
import es.uma.informatica.practica3.security.SecurityConfguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import es.uma.informatica.practica3.dtos.GerenteDTO;
import es.uma.informatica.practica3.dtos.IdGerenteDTO;
import es.uma.informatica.practica3.entidades.Entrenador;
import es.uma.informatica.practica3.entidades.Mensaje;
import es.uma.informatica.practica3.repositorios.EntrenadorRepository;
import es.uma.informatica.practica3.repositorios.MensajeRepository;
import es.uma.informatica.practica3.servicios.excepciones.AccesoNoPermitidoException;
import es.uma.informatica.practica3.servicios.excepciones.EntidadExistenteException;
import es.uma.informatica.practica3.servicios.excepciones.EntidadNoEncontradaException;
import es.uma.informatica.practica3.servicios.excepciones.PeticionHttpFallidaException;
import java.util.NoSuchElementException;

@Service
@Transactional
public class EntrenadorServicio {
	
    @Autowired
    private RestTemplate restTemplate;

	private EntrenadorRepository entrenadorRepo;
    private MensajeRepository mensajeRepo;

    
    public EntrenadorServicio(EntrenadorRepository entrenadorRepo, 
			MensajeRepository mensajeRepo) {
		this.entrenadorRepo = entrenadorRepo;
		this.mensajeRepo = mensajeRepo;
	}

    public ResponseEntity<GerenteDTO> verificayencuentraGerente (Long idCentro) {
        try {
            // Construir la URL para obtener IdGerenteDTO
            String url1 = "http://localhost:8080/centro/" + idCentro + "/gerente";
            
            // Configurar los headers, incluyendo el token de autorización
            HttpEntity<String> entity = new HttpEntity<>(new HttpHeaders());

            // Hacer la primera solicitud GET
            ResponseEntity<IdGerenteDTO> response1 = restTemplate.exchange(url1, HttpMethod.GET, entity, IdGerenteDTO.class);

            // Construir la URL para la segunda solicitud
            IdGerenteDTO gDto = response1.getBody();

            // Construir la URL para la segunda solicitud
            String url2 = "http://localhost:8080/gerente/" + gDto.getIdGerente();

            // Hacer la segunda solicitud GET
            ResponseEntity<GerenteDTO> response2 = restTemplate.exchange(url2, HttpMethod.GET, entity, GerenteDTO.class);

            return response2;  
        } catch (HttpClientErrorException e) {
            // Manejo de errores 4xx
            throw new PeticionHttpFallidaException();
        }
    }

	
	public List<Entrenador> obtenerEntrenadores (Long idCentro) {

        ResponseEntity<GerenteDTO> response2 = verificayencuentraGerente(idCentro);

        if (response2.getBody().getIdUsuario().toString().equals(SecurityConfguration.getAuthenticatedUser().get().getUsername().toString())) {
            return entrenadorRepo.findByIdCentro(idCentro);
        } else {
            throw new AccesoNoPermitidoException();
        }

    }

    public Optional<Entrenador> obtenerEntrenador (Long id) {
        
        Optional<Entrenador> entrenador = this.entrenadorRepo.findById(id);
        if (entrenador.isPresent()) {

            ResponseEntity<GerenteDTO> response2 = verificayencuentraGerente(entrenador.get().getIdCentro());

            // Obtener el cuerpo de la respuesta de la segunda solicitud
            if (response2.getBody().getIdUsuario().toString().equals(SecurityConfguration.getAuthenticatedUser().get().getUsername().toString())) {
                return entrenador;
            } else {
                throw new AccesoNoPermitidoException();
            }
            
        } else {
            throw new EntidadNoEncontradaException();
        }
    }

    public Entrenador aniadirEntrenador (Entrenador ent) {

        // Para buscar los mensajes asociados a él en la base de datos.
        if (!this.entrenadorRepo.findByIdUsuario(ent.getIdUsuario()).isPresent()) {
            try {
                String url1 = "http://localhost:8080/usuario/" + ent.getIdUsuario();
                
                // Crear la entidad HTTP con los headers vacios
                HttpEntity<String> entity = new HttpEntity<>(new HttpHeaders());

                // Hacer la primera solicitud GET -> NO DEVUELVE UN GERENTEDTO, PERO ERA NECESARIO DARLE UN TIPO A LA REQUESTENTITY SERIALIZABLE
                ResponseEntity<GerenteDTO> response = restTemplate.exchange(url1, HttpMethod.GET, entity, GerenteDTO.class);

                return (Entrenador) entrenadorRepo.save(ent);
            } catch (HttpClientErrorException e) {
                // Manejo de errores 4xx
                throw new PeticionHttpFallidaException();
            }

        } else {
            throw new EntidadExistenteException();
        }
    }

    public void eliminarEntrenador (Long id) {
        if (entrenadorRepo.existsById(id)) {
            Optional<Entrenador> entrenador = this.entrenadorRepo.findById(id);
            ResponseEntity<GerenteDTO> response2 = verificayencuentraGerente(entrenador.get().getIdCentro());

            // Obtener el cuerpo de la respuesta de la segunda solicitud
            if (response2.getBody().getIdUsuario().toString().equals(SecurityConfguration.getAuthenticatedUser().get().getUsername().toString())) {
                entrenadorRepo.deleteById(id);
            } else {
                throw new AccesoNoPermitidoException();
            }
        } else {
            throw new EntidadNoEncontradaException();
        }
    }

    public Entrenador actualizarEntrenador (Entrenador newTrainer) {
        // Como si he llegado aqui, se que el newTrainer.id existe y se ha comprobado el token,
        // no es necesario realizar nada auxiliar...
        return entrenadorRepo.save(newTrainer);
    }

    public List<Mensaje> obtenerMensajes (Long idEntr) {
        // Presuponemos que solo los entrenadores pueden acceder a sus mensajes...
        if (entrenadorRepo.existsById(idEntr)) {
            Optional<Entrenador> entrenador = this.entrenadorRepo.findById(idEntr);
            if (entrenador.get().getIdUsuario().toString().equals(SecurityConfguration.getAuthenticatedUser().get().getUsername().toString())) {
                List<Mensaje> messages = mensajeRepo.findByEntrenadorId(idEntr);
                return messages;
            } else {
                throw new AccesoNoPermitidoException();
            }
        } else {
            throw new EntidadNoEncontradaException();
        }


    }

    public Optional<Mensaje> obtenerMensaje (Long id) {
        // Presuponemos que solo los entrenadores pueden acceder a mensajes suyos concretos...
        Optional<Mensaje> mens = mensajeRepo.findById(id);
        if (mens.isPresent()) {
            Entrenador e = mens.get().getEntrenador();
            if (e.getIdUsuario().toString().equals(SecurityConfguration.getAuthenticatedUser().get().getUsername().toString())) {
                return mens;
            } else {
                throw new AccesoNoPermitidoException();
            }
        } else {
            throw new EntidadNoEncontradaException();
        }
    }

    public Mensaje aniadirMensajeAsignado (Long idEntrenador, Mensaje mensaje) {
        // Presuponemos que solo los entrenadores pueden declarar nuevos mensajes suyos...
        if (entrenadorRepo.existsById(idEntrenador)) {
            Optional<Entrenador> entrenador = this.entrenadorRepo.findById(idEntrenador);
            if (entrenador.get().getIdUsuario().toString().equals(SecurityConfguration.getAuthenticatedUser().get().getUsername().toString())) {
                mensaje.setIdMensaje(null);
                mensaje.setEntrenador(this.entrenadorRepo.findById(idEntrenador).get());
                return this.mensajeRepo.save(mensaje);
            } else {
                throw new AccesoNoPermitidoException();
            }
        } else {
            throw new EntidadNoEncontradaException();
        }
        
    }

    
    public void eliminarMensaje (Long id) {
        
        // Presuponemos que solo los entrenadores pueden eliminar sus mensajes...
        try {
            Entrenador e = mensajeRepo.findById(id).get().getEntrenador();
            if (e.getIdUsuario().toString().equals(SecurityConfguration.getAuthenticatedUser().get().getUsername().toString())) {
                mensajeRepo.deleteById(id);
            } else {
                throw new AccesoNoPermitidoException();
            }
        } catch (NoSuchElementException e) {
            throw new EntidadNoEncontradaException();
        }
    }

    // NO EXISTE UN PUT DE MENSAJE -> SE PUEDE COMPROBAR EN LA OPEN API DEL CV
}
