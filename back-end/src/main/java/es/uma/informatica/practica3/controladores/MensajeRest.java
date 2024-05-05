package es.uma.informatica.practica3.controladores;

import es.uma.informatica.practica3.dtos.MensajeDTO;
import es.uma.informatica.practica3.dtos.MensajeNuevoDTO;
import es.uma.informatica.practica3.entidades.Mensaje;
import es.uma.informatica.practica3.servicios.EntrenadorServicio;
import es.uma.informatica.practica3.servicios.excepciones.EntidadNoEncontradaException;

import java.net.URI;
import java.util.List;
import java.util.function.Function;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@CrossOrigin
@RequestMapping("/mensaje/entrenador")
public class MensajeRest {
    private EntrenadorServicio servicioEntrenadores;

    public MensajeRest (EntrenadorServicio servicioEntrenadores) {
        this.servicioEntrenadores = servicioEntrenadores;
    }

    // Para mirar los RequestParam y los RequestBody es MUY UTIL mirar la OpenAPI -> aparece tal cual

    @GetMapping
    @Operation(description = "Obtener los mensajes de un entrenador",
                responses = {@ApiResponse(responseCode = "200", description = "Devuelve la lista de mensajes de un entrenador"),
                            @ApiResponse(responseCode = "403", description = "Acceso no autorizado")})
    public List<MensajeDTO> obtenerMensajesEntrenador (@RequestParam(value = "entrenador", required = true) Long idTrainer) {
        return this.servicioEntrenadores.obtenerMensajes(idTrainer)
                                        .stream().map(MensajeDTO::fromEntity).toList();
    }

    @GetMapping({"/{idMensaje}"})
    @Operation(description = "Obtener un mensaje concreto por su identificador",
                responses = {@ApiResponse(responseCode = "200", description = "El mensaje existe"),
                            @ApiResponse(responseCode = "404", description = "El mensaje no existe")})
    public ResponseEntity<MensajeDTO> getMensajeEntrenador (@PathVariable Long idMensaje) {
        return ResponseEntity.of(this.servicioEntrenadores.obtenerMensaje(idMensaje)
                                    .map(MensajeDTO::fromEntity));
    }

    @PostMapping
    @Operation(description = "Insertar un nuevo mensaje a un entrenador",
                responses = {@ApiResponse(responseCode = "201", description = "Se crea el mensaje y lo devuelve"),
                            @ApiResponse(responseCode = "403", description = "Acceso no autorizado")})
    public ResponseEntity<MensajeDTO> crearMensaje(@RequestParam(value = "entrenador", required = true) Long idEntrenador, @RequestBody MensajeNuevoDTO mensajeNuevoDTO, UriComponentsBuilder uriBuilder) {
        System.out.println("Hola he entrado");
        Mensaje newMensaje = mensajeNuevoDTO.toEntity();
        URI uri = UriGenerator(UriComponentsBuilder.newInstance()
                                                    .build()).apply(newMensaje);
        return ResponseEntity.created(uri)
                            .body(MensajeDTO.fromEntity(this.servicioEntrenadores.aniadirMensajeAsignado(idEntrenador, newMensaje)));
    }

    private Function<Mensaje, URI> UriGenerator (UriComponents uriBuilder) {
        return m -> UriComponentsBuilder.newInstance().uriComponents(uriBuilder)
                            .path("/mensaje").path("/entrenador")
                            .path(String.format("/%d", m.getIdMensaje()))
                            .build()
                            .toUri();
    }

    @DeleteMapping({"/{idMensaje}"})
    @Operation(description = "Eliminar un determinado mensaje por identificador",
                responses = {@ApiResponse(responseCode = "200", description = "El mensaje se ha elminado"),
                            @ApiResponse(responseCode = "404", description = "El mensaje no existe")})
    public void eliminarUnMensaje (@PathVariable Long idMensaje) {
        this.servicioEntrenadores.eliminarMensaje(idMensaje);
    }


    /* Esta anotación se utiliza para indicar que el método siguiente manejará cualquier excepción del
    tipo EntrenadorNoExisteException que sea lanzada dentro del controlador o los métodos del controlador
    anidados. */
    @ExceptionHandler({EntidadNoEncontradaException.class})
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public void tratamientoNotFoundException () {}
}