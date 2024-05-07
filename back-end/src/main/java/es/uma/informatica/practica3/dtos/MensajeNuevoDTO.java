package es.uma.informatica.practica3.dtos;

import es.uma.informatica.practica3.entidades.Mensaje;

import java.util.List;

public class MensajeNuevoDTO {
    
    private List<DestinatarioDTO> destinatarios;
    
    private List<DestinatarioDTO> copia;
    
    private List<DestinatarioDTO> copiaOculta;
    
    private String asunto;

    private DestinatarioDTO remitente;
    
    private String contenido;
    
    public List<DestinatarioDTO> getDestinatarios() {
        return destinatarios;
    }

    public MensajeNuevoDTO(List<DestinatarioDTO> destinatarios, List<DestinatarioDTO> copia,
            List<DestinatarioDTO> copiaOculta, String asunto, DestinatarioDTO remitente, String contenido) {
        this.destinatarios = destinatarios;
        this.copia = copia;
        this.copiaOculta = copiaOculta;
        this.asunto = asunto;
        this.remitente = remitente;
        this.contenido = contenido;
    }

    public MensajeNuevoDTO () {}

    public void setDestinatarios(List<DestinatarioDTO> destinatarios) {
        this.destinatarios = destinatarios;
    }

    public List<DestinatarioDTO> getCopia() {
        return copia;
    }

    public void setCopia(List<DestinatarioDTO> copia) {
        this.copia = copia;
    }
    
    public List<DestinatarioDTO> getCopiaOculta() {
        return copiaOculta;
    }

    public void setCopiaOculta(List<DestinatarioDTO> copiaOculta) {
        this.copiaOculta = copiaOculta;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public DestinatarioDTO getRemitente() {
        return remitente;
    }

    public void setRemitente(DestinatarioDTO remitente) {
        this.remitente = remitente;
    }
    
    public String getContenido() {
        return contenido;
    }
    
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Mensaje toEntity() {
        Mensaje aDevolver = new Mensaje();
        aDevolver.setAsunto(this.asunto);
        aDevolver.setDestinatarios(DestinatarioDTO.toEntity(this.destinatarios));
        aDevolver.setCopia(DestinatarioDTO.toEntity(this.copia));
        aDevolver.setCopiaOculta(DestinatarioDTO.toEntity(this.copiaOculta));
        aDevolver.setRemitente(this.remitente != null ? this.remitente.toEntity() : null);
        aDevolver.setContenido(this.contenido);
        return aDevolver;
    }
}
