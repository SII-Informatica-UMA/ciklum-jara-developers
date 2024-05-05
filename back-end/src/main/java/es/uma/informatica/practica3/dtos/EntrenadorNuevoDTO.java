package es.uma.informatica.practica3.dtos;

import java.util.Date;

import es.uma.informatica.practica3.entidades.Entrenador;

public class EntrenadorNuevoDTO {
    private Long idUsuario;
  
    private String telefono;
    
    private String direccion;
    
    private String dni;
    
    private Date fechaNacimiento;
    
    private Date fechaAlta;
    
    private Date fechaBaja;
    
    private String especialidad;
    
    private String titulacion;
    
    private String experiencia;
    
    private String observaciones;
    
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public void setDni(String dni) {
        this.dni = dni;
    }
    
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    
    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
    
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    
    public void setTitulacion(String titulacion) {
        this.titulacion = titulacion;
    }
    
    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public EntrenadorNuevoDTO() {}
    
    public EntrenadorNuevoDTO(Long idUsuario, String telefono, String direccion,
                                String dni, Date fechaNacimiento, Date fechaAlta, Date fechaBaja,
                                String especialidad, String titulacion, String experiencia,
                                String observaciones) {
        this.idUsuario = idUsuario;
        this.telefono = telefono;
        this.direccion = direccion;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.especialidad = especialidad;
        this.titulacion = titulacion;
        this.experiencia = experiencia;
        this.observaciones = observaciones;
    }
    
    public Long getIdUsuario() {
        return this.idUsuario;
    }
    
    public String getTelefono() {
        return this.telefono;
    }
    
    public String getDireccion() {
        return this.direccion;
    }
    
    public String getDni() {
        return this.dni;
    }
    
    public Date getFechaNacimiento() {
        return this.fechaNacimiento;
    }
    
    public Date getFechaAlta() {
        return this.fechaAlta;
    }
    
    public Date getFechaBaja() {
        return this.fechaBaja;
    }
    
    public String getEspecialidad() {
        return this.especialidad;
    }
    
    public String getTitulacion() {
        return this.titulacion;
    }
    
    public String getExperiencia() {
        return this.experiencia;
    }
    
    public String getObservaciones() {
        return this.observaciones;
    }
    
    public static EntrenadorNuevoDTO fromEntity(Entrenador entrenador) {
        EntrenadorNuevoDTO aDevolver = new EntrenadorNuevoDTO();
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
        aDevolver.setIdUsuario(this.idUsuario);
        aDevolver.setTelefono(this.telefono);
        aDevolver.setDireccion(this.direccion);
        aDevolver.setDni(this.dni);
        aDevolver.setFechaNacimiento(this.fechaNacimiento);
        aDevolver.setFechaAlta(this.fechaAlta);
        aDevolver.setFechaBaja(this.fechaBaja);
        aDevolver.setEspecialidad(this.especialidad);
        aDevolver.setTitulacion(this.titulacion);
        aDevolver.setExperiencia(this.experiencia);
        aDevolver.setObservaciones(this.observaciones);
        return aDevolver;
    }
}
