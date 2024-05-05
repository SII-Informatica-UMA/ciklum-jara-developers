package es.uma.informatica.practica3.servicios;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uma.informatica.practica3.entidades.Entrenador;
import es.uma.informatica.practica3.entidades.Mensaje;
import es.uma.informatica.practica3.repositorios.EntrenadorRepository;
import es.uma.informatica.practica3.repositorios.MensajeRepository;
import es.uma.informatica.practica3.servicios.excepciones.EntidadNoEncontradaException;

@Service
@Transactional
public class EntrenadorServicio {
	
	private EntrenadorRepository entrenadorRepo;
    private MensajeRepository mensajeRepo;
	
	public EntrenadorServicio(EntrenadorRepository entrenadorRepo, 
			MensajeRepository mensajeRepo) {
		this.entrenadorRepo = entrenadorRepo;
		this.mensajeRepo = mensajeRepo;
	}
	
	public List<Entrenador> obtenerEntrenadores (Long idCentro) {
        return entrenadorRepo.findByIdCentro(idCentro);
    }

    public Optional<Entrenador> obtenerEntrenador (Long id) {
        Optional<Entrenador> entrenador = this.entrenadorRepo.findById(id);
        if (entrenador.isPresent()) {
            return entrenador;
        } else {
            throw new EntidadNoEncontradaException();
        }
    }

    public Entrenador aniadirEntrenador (Entrenador ent) {
        // Para buscar los mensajes asociados a él en la base de datos.
        refrescarMensajes(ent);
        return (Entrenador) entrenadorRepo.save(ent);
    }

    public void refrescarMensajes (Entrenador ent) {
        if (ent.getMensajes() != null) {
            var mensajesEnContexto = ent.getMensajes()
                                        .stream()
                                        .map(men -> refrescaMensaje(men).orElseThrow(() -> new EntidadNoEncontradaException()))
                                        .collect(Collectors.toList());
		    ent.setMensajes(mensajesEnContexto);
        }
    }

    private Optional<Mensaje> refrescaMensaje (Mensaje mensj) {
		if (mensj.getIdMensaje()!=null) {
			return mensajeRepo.findById(mensj.getIdMensaje());
		} else {
			return Optional.empty();
		}
	}

    public void eliminarEntrenador (Long id) {
        if (entrenadorRepo.existsById(id)) {
            entrenadorRepo.deleteById(id);
        } else {
            throw new EntidadNoEncontradaException();
        }
    }

    public Entrenador actualizarEntrenador (Entrenador newTrainer) {
        if (entrenadorRepo.existsById(newTrainer.getId())) {
            return entrenadorRepo.save(newTrainer);
        } else {
            throw new EntidadNoEncontradaException();
        }
    }

    public List<Mensaje> obtenerMensajes (Long idEntr) {
        return mensajeRepo.findByEntrenadorId(idEntr);
    }

    public Mensaje aniadirMensajeAsignado (Long idEntrenador, Mensaje mensaje) {
        if (this.entrenadorRepo.findById(idEntrenador).isEmpty())
          throw new EntidadNoEncontradaException();
        mensaje.setIdMensaje(null);
        mensaje.setEntrenador(this.entrenadorRepo.findById(idEntrenador).get());
        return this.mensajeRepo.save(mensaje);
    }

    public Optional<Mensaje> obtenerMensaje (Long id) {
        Optional<Mensaje> mens = mensajeRepo.findById(id);
        if (mens.isPresent()) {
            return mens;
        } else {
            throw new EntidadNoEncontradaException();
        }
    }
    
    public void eliminarMensaje (Long id) {
        if (mensajeRepo.existsById(id)) {
            mensajeRepo.deleteById(id);
        } else {
            throw new EntidadNoEncontradaException();
        }
    }

    // NO EXISTE UN PUT DE MENSAJE -> SE PUEDE COMPROBAR EN LA OPEN API DEL CV
}
