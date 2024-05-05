package es.uma.informatica.practica3.dtos;

import java.util.Date;

import es.uma.informatica.practica3.entidades.Entrenador;

public class EntrenadorDTO extends EntrenadorNuevoDTO {
    private Long id;

    public EntrenadorDTO() {}
    
    public Long getId() {
        return this.id;
    }
  
    public void setId(Long id) {
        this.id = id;
    }
    
    public EntrenadorDTO(Long id, Long idUsuario, String telefono, String direccion, String dni,
                        Date fechaNacimiento, Date fechaAlta, Date fechaBaja, String especialidad, String titulacion,
                        String experiencia, String observaciones) {
        // Para llamar al constructor de EntrenadorNuevoDTO
        super(idUsuario, telefono, direccion, dni, fechaNacimiento, fechaAlta, fechaBaja, especialidad, titulacion, experiencia, observaciones);
        this.id = id;
    }

    public static EntrenadorDTO fromEntity(Entrenador entrenador) {
        EntrenadorDTO aDevolver = new EntrenadorDTO();
        aDevolver.setId(entrenador.getId());
        aDevolver.setIdUsuario(entrenador.getIdUsuario());
        aDevolver.setTelefono(entrenador.getTelefono());
        aDevolver.setDireccion(entrenador.getDireccion());
        aDevolver.setDni(entrenador.getDni());
        aDevolver.setFechaNacimiento(entrenador.getFechaNacimiento());
        aDevolver.setFechaAlta(entrenador.getFechaAlta());
        aDevolver.setFechaBaja(entrenador.getFechaBaja());
        aDevolver.setEspecialidad(entrenador.getEspecialidad());
        aDevolver.setObservaciones(entrenador.getObservaciones());
        aDevolver.setTitulacion(entrenador.getTitulacion());
        aDevolver.setExperiencia(entrenador.getExperiencia());
        return aDevolver;
    }
    
    public Entrenador toEntity() {
        Entrenador aDevolver = new Entrenador();
        aDevolver.setId(this.id);
        aDevolver.setIdUsuario(getIdUsuario());
        aDevolver.setTelefono(getTelefono());
        aDevolver.setDireccion(getDireccion());
        aDevolver.setDni(getDni());
        aDevolver.setFechaNacimiento(getFechaNacimiento());
        aDevolver.setFechaAlta(getFechaAlta());
        aDevolver.setFechaBaja(getFechaBaja());
        aDevolver.setEspecialidad(getEspecialidad());
        aDevolver.setTitulacion(getTitulacion());
        aDevolver.setExperiencia(getExperiencia());
        aDevolver.setObservaciones(getObservaciones());
        return aDevolver;
    }
}
