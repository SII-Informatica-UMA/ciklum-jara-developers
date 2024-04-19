package es.uma.informatica.sii.spring.jpa.demo.entities;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
public abstract class Mensaje {

    @ManyToOne
    private Entrenador entrenador;

    private String asunto;

    @ElementCollection
    @CollectionTable(foreignKey = @ForeignKey(name = "mensaje_destinatario_fk"),
                    name = "destinatarios",
                    joinColumns = @JoinColumn(name = "mensaje_id", referencedColumnName = "idMensaje"))
    private List<Destinatario> destinatarios;

    @ElementCollection
    @CollectionTable(foreignKey = @ForeignKey(name = "mensaje_copia_fk"),
                    name = "copia",
                    joinColumns = @JoinColumn(name = "mensaje_id", referencedColumnName = "idMensaje"))
    private List<Destinatario> copia; 

    @ElementCollection
    @CollectionTable(foreignKey = @ForeignKey(name = "mensaje_copiaoculta_fk"),
                    name = "copia_oculta",
                    joinColumns = @JoinColumn(name = "mensaje_id", referencedColumnName = "idMensaje"))
    private List<Destinatario> copiaOculta;
    
    private Destinatario remitente;
    
    private String contenido;

    @Id
    @GeneratedValue
    private Long idMensaje;
  
    
    public Mensaje() {}
    
    public Mensaje(Entrenador e, Long id, String asunto, List<Destinatario> destinatarios, List<Destinatario> copia, List<Destinatario> copiaOculta, Destinatario remitente, String contenido) {
        this.entrenador = e;
        this.idMensaje = id;
        this.asunto = asunto;
        this.destinatarios = destinatarios;
        this.copia = copia;
        this.copiaOculta = copiaOculta;
        this.remitente = remitente;
        this.contenido = contenido;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public List<Destinatario> getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(List<Destinatario> destinatarios) {
        this.destinatarios = destinatarios;
    }

    public List<Destinatario> getCopia() {
        return copia;
    }

    public void setCopia(List<Destinatario> copia) {
        this.copia = copia;
    }

    public List<Destinatario> getCopiaOculta() {
        return copiaOculta;
    }

    public void setCopiaOculta(List<Destinatario> copiaOculta) {
        this.copiaOculta = copiaOculta;
    }

    public Destinatario getRemitente() {
        return remitente;
    }

    public void setRemitente(Destinatario remitente) {
        this.remitente = remitente;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Long getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(Long idMensaje) {
        this.idMensaje = idMensaje;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMensaje);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Mensaje other = (Mensaje) obj;
        if (idMensaje == null) {
            if (other.idMensaje != null)
                return false;
        } else if (!idMensaje.equals(other.idMensaje))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Mensaje(" + idMensaje + ", " + asunto + ", " + contenido + ", "
                            + destinatarios.toString() + ")";
    }
}