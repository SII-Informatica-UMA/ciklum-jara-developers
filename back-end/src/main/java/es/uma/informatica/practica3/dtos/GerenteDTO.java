package es.uma.informatica.practica3.dtos;

public class GerenteDTO {

    private Long id;

    private Long idUsuario;

    private String empresa;

    public GerenteDTO(Long id, Long idUsuario, String empresa) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.empresa = empresa;
    }

    public GerenteDTO() {}
    
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
}
