package es.uma.informatica.practica3.dtos;

import es.uma.informatica.practica3.entidades.Mensaje;

import java.util.List;

public class MensajeDTO extends MensajeNuevoDTO {

    private Long idMensaje;

    public void setIdMensaje(Long idMensaje) {
        this.idMensaje = idMensaje;
    }

    public Long getIdMensaje() {
        return idMensaje;
    }

    public MensajeDTO() {
        // Constructor defecto
    }

    public MensajeDTO(List<DestinatarioDTO> destinatarios, List<DestinatarioDTO> copia,
            List<DestinatarioDTO> copiaOculta, String asunto, DestinatarioDTO remitente, String contenido,
            Long idMensaje) {
        super(destinatarios, copia, copiaOculta, asunto, remitente, contenido);
        this.idMensaje = idMensaje;
    }

    public static MensajeDTO fromEntity (Mensaje mensaje) {
        MensajeDTO aDevolver = new MensajeDTO();
        aDevolver.setAsunto(mensaje.getAsunto());
        aDevolver.setDestinatarios(DestinatarioDTO.fromEntity(mensaje.getDestinatarios()));
        aDevolver.setCopia(DestinatarioDTO.fromEntity(mensaje.getCopia()));
        aDevolver.setCopiaOculta(DestinatarioDTO.fromEntity(mensaje.getCopiaOculta()));
        aDevolver.setRemitente(DestinatarioDTO.fromEntity(mensaje.getRemitente()));
        aDevolver.setContenido(mensaje.getContenido());
        aDevolver.setIdMensaje(mensaje.getIdMensaje());
        return aDevolver;
    }
      
    public Mensaje toEntity() {
        Mensaje aDevolver = new Mensaje();
        aDevolver.setIdMensaje(this.idMensaje);
        aDevolver.setAsunto(getAsunto());
        aDevolver.setDestinatarios(DestinatarioDTO.toEntity(getDestinatarios()));
        aDevolver.setCopia(DestinatarioDTO.toEntity(getCopia()));
        aDevolver.setCopiaOculta(DestinatarioDTO.toEntity(getCopiaOculta()));
        aDevolver.setRemitente(getRemitente().toEntity());
        aDevolver.setContenido(getContenido());
        return aDevolver;
    }
}
