package com.tinder.ud.media.dto;

/**
 * Objeto de transferencia de datos para información multimedia.
 * Representa una foto asociada a un usuario.
 * 
 * Atributos:
 * - id: identificador de la foto.
 * - idUsuario: ID del usuario dueño.
 * - url: ubicación de la foto.
 * - esPrincipal: si es la foto principal.
 * 
 * Getters y setters incluidos.
 * 
 * @author Paula Martinez
 * @version 3.0
 * @since 2025-12-10
 */
public class MediaDTO {

    private Long id;
    private Long idUsuario;
    private String url;
    private Boolean esPrincipal;

    public Long getId() {
        return id;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getEsPrincipal() {
        return esPrincipal;
    }

    public void setEsPrincipal(Boolean esPrincipal) {
        this.esPrincipal = esPrincipal;
    }
}
