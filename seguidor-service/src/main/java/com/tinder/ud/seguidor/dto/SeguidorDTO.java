package com.tinder.ud.seguidor.dto;

import java.time.LocalDateTime;

/**
 *
 * Representación de los seguidores, estableciendo sus posibles estados en la 
 * base de datos
 * 
 * @author Juan Estevan Ariza Ortiz
 * @version 3.0
 * @since 2025-12-09
 */
public class SeguidorDTO {
    private Long id;
    private Long idSeguidor;
    private Long idSeguido;
    private LocalDateTime fecha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdSeguidor() {
        return idSeguidor;
    }

    public void setIdSeguidor(Long idSeguidor) {
        this.idSeguidor = idSeguidor;
    }

    public Long getIdSeguido() {
        return idSeguido;
    }

    public void setIdSeguido(Long idSeguido) {
        this.idSeguido = idSeguido;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
