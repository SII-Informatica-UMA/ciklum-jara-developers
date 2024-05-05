package es.uma.informatica.practica3.controladores;

import es.uma.informatica.practica3.dtos.EntrenadorDTO;
import es.uma.informatica.practica3.dtos.EntrenadorNuevoDTO;
import es.uma.informatica.practica3.entidades.Entrenador;
import es.uma.informatica.practica3.servicios.EntrenadorServicio;
import es.uma.informatica.practica3.servicios.excepciones.EntidadNoEncontradaException;

import java.net.URI;
import java.util.List;

import java.util.function.Function;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@CrossOrigin
@RequestMapping("/entrenador")
public class EntrenadorRest {
    private EntrenadorServicio servicioEntrenadores;

    public EntrenadorRest (EntrenadorServicio servicioEntrenadores) {
        this.servicioEntrenadores = servicioEntrenadores;
    }

    // Para mirar los RequestParam y los RequestBody es MUY UTIL mirar la OpenAPI -> aparece tal cual

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<EntrenadorDTO> obtenerEntrenadores (@RequestParam(value = "centro", required = true) Long centroId) {
        return this.servicioEntrenadores.obtenerEntrenadores(centroId).stream()
        .map(EntrenadorDTO::fromEntity)
        .toList();
    }

    @GetMapping({"/{idEntrenador}"})
    public ResponseEntity<EntrenadorDTO> getEntrenadorConcreto (@PathVariable Long idEntrenador) {
        return ResponseEntity.of(this.servicioEntrenadores.obtenerEntrenador(idEntrenador)
            .map(EntrenadorDTO::fromEntity));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<EntrenadorDTO> aniadirEntrenador (@RequestParam(value = "centro", required = true) Long centroId, @RequestBody EntrenadorNuevoDTO entrenador, UriComponentsBuilder uriBuilder) {
        Entrenador newTrainer = entrenador.toEntity();
        newTrainer.setId(null);
        newTrainer.setIdCentro(centroId);
        newTrainer = this.servicioEntrenadores.aniadirEntrenador(newTrainer);
        return ResponseEntity.created(UriGenerator(uriBuilder.build()).apply(newTrainer))
                                        .body(EntrenadorDTO.fromEntity(newTrainer));
    }

    private Function<Entrenador, URI> UriGenerator (UriComponents uriBuilder) {
        return trainer -> UriComponentsBuilder.newInstance().uriComponents(uriBuilder)
                            .path("/entrenador")
                            .path(String.format("/%d", trainer.getId()))
                            .build()
                            .toUri();
    }

    @PutMapping({"/{idEntrenador}"})
    public EntrenadorDTO actualizarEntrenador (@PathVariable Long idEntrenador, @RequestBody EntrenadorDTO entrenador) {
        if (servicioEntrenadores.obtenerEntrenador(idEntrenador).isPresent()) {
            entrenador.setId(idEntrenador);
            Entrenador newTrainer = this.servicioEntrenadores.actualizarEntrenador(entrenador.toEntity());
            return EntrenadorDTO.fromEntity(newTrainer);
        } else {
            // Es un poco redundate, pero no se entonces que devolver aqui...
            throw new EntidadNoEncontradaException();
        }
    }

    @DeleteMapping({"/{idEntrenador}"})
    public void eliminarEntrenador (@PathVariable Long idEntrenador) {
        if (servicioEntrenadores.obtenerEntrenador(idEntrenador).isPresent()) {
            this.servicioEntrenadores.eliminarEntrenador(idEntrenador);
        } else {
            throw new EntidadNoEncontradaException();
        }
    }


    /* Esta anotación se utiliza para indicar que el método siguiente manejará cualquier excepción del
    tipo EntrenadorNoExisteException que sea lanzada dentro del controlador o los métodos del controlador
    anidados. */
    @ExceptionHandler({EntidadNoEncontradaException.class})
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public void tratamientoNotFoundException () {}
}