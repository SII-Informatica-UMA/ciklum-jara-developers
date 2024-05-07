package es.uma.informatica.practica3.dtos;

import es.uma.informatica.practica3.entidades.Destinatario.TipoDestinatario;
import es.uma.informatica.practica3.entidades.Destinatario;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DestinatarioDTO {
    private Long id;
    
    private TipoDestinatario tipo;
    
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }
    
    public void setTipo(TipoDestinatario tipo) {
        this.tipo = tipo;
    }

    public TipoDestinatario getTipo() {
        return this.tipo;
    }

    public DestinatarioDTO() {}
    
    public DestinatarioDTO(Long id, TipoDestinatario tipo) {
        this.id = id;
        this.tipo = tipo;
    }
    
    public static DestinatarioDTO fromEntity(Destinatario destinatario) {
        // No tiene mucho sentido ya que ambas clases tienen los mismos campos, pero asi lo indica la OpenAPI
        if (destinatario == null) {
            return null;
        } else {
            DestinatarioDTO aDevolver = new DestinatarioDTO();
            aDevolver.setId(destinatario.getId());
            aDevolver.setTipo(destinatario.getTipo());
            return aDevolver;
        }
    }
    
    // Para las copias de destinatarios, que son List<Destinatario>
    public static List<DestinatarioDTO> fromEntity(List<Destinatario> destinatariosL) {
        // Se podria haber hecho iterando sobre toda la lista del parametro, y conviertiendo con fromEntity a DTO
        // Quizas incluso hubiese funcionado unicamente realizando el return del stream().map().toList()
        if (destinatariosL == null) {
            return Collections.emptyList();
        } else {
            return destinatariosL.stream()
                    .map(DestinatarioDTO::fromEntity)
                    .collect(Collectors.toList());
        }
    }

    
    public Destinatario toEntity() {
        Destinatario aDevolver = new Destinatario();
        aDevolver.setId(this.id);
        aDevolver.setTipo(this.tipo);
        return aDevolver;
    }
    
    public static List<Destinatario> toEntity(List<DestinatarioDTO> destinatariosL) {
        if (destinatariosL != null) {
            return destinatariosL != null ? destinatariosL.stream().map(DestinatarioDTO::toEntity).toList() : Collections.emptyList();
        } else {
            return Collections.emptyList();
        }
        
    }
}