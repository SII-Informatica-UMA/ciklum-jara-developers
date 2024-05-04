package es.uma.informatica.practica3.entidades;

import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;


@Embeddable
public class Destinatario {

  public enum TipoDestinatario {
    CENTRO, CLIENTE, ENTRENADOR
  }

  private Long id;

  @Enumerated(EnumType.STRING)
  private TipoDestinatario tipo;

  public Long getId() {
    return id;
  }

  public TipoDestinatario getTipo() {
    return tipo;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setTipo(TipoDestinatario tipo) {
    this.tipo = tipo;
  }

  public Destinatario() {}
  
  public Destinatario(TipoDestinatario tipo, Long idDestinatario) {
    this.tipo = tipo;
    this.id = idDestinatario;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Destinatario other = (Destinatario) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Destinatario(" + id + ", " + tipo + ")";
  }  
}
