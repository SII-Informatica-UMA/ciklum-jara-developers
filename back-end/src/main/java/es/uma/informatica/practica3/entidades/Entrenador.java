package es.uma.informatica.practica3.entidades;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
public class Entrenador {

    private Long idUsuario;

    private String telefono;

    private String direccion;

    private String dni;

    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @Temporal(TemporalType.DATE)
    private Date fechaAlta;

    @Temporal(TemporalType.DATE)
    private Date fechaBaja;

    private String especialidad;
    
    private String titulacion;
    
    private String experiencia;
    
    private String observaciones;

    @OneToMany(mappedBy = "entrenador")
    private List<Mensaje> mensajes;

    @Id
    @GeneratedValue
    private Long id;
    
    private Long idCentro;



    public Entrenador() {}
    
    public Entrenador(Long idUsuario, String telefono, String direccion, String dni, Date fechaNacimiento,
            Date fechaAlta, Date fechaBaja, String especialidad, String titulacion, String experiencia,
            String observaciones, Long id, Long idCentro) {
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
        this.id = id;
        this.idCentro = idCentro;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getDni() {
        return dni;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public String getTitulacion() {
        return titulacion;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public Long getId() {
        return id;
    }

    public Long getIdCentro() {
        return idCentro;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

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

    public void setId(Long id) {
        this.id = id;
    }

    public void setIdCentro(Long idCentro) {
        this.idCentro = idCentro;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Entrenador entrenador = (Entrenador) obj;
        return id != null && id.equals(entrenador.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return "Entrenador " + id + "\n\t-> Titulacion: " + titulacion +
                                    "\n\t-> Experiencia: " + experiencia +
                                    "\n\t-> Telefono: " + telefono;
    }
}
