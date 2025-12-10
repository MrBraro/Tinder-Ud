package com.tinder.ud.match.dto;

import java.time.LocalDateTime;

/**
 * DTO que representa un match entre dos usuarios.
 * Incluye los identificadores involucrados y la fecha de creación. 
 * @author Juan Estevan Ariza Ortiz
 * @version 3.0
 * @since 2025-12-09
 */
public class MatchDTO {

    private Long id;
    private Long idUsuario1;
    private Long idUsuario2;
    private LocalDateTime fecha;

    /**
     * Obtiene el ID del match.
     *
     * @return identificador del match
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID del match.
     *
     * @param id identificador a asignar
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el ID del primer usuario.
     *
     * @return idUsuario1
     */
    public Long getIdUsuario1() {
        return idUsuario1;
    }

    /**
     * Establece el ID del primer usuario.
     *
     * @param idUsuario1 id del usuario
     */
    public void setIdUsuario1(Long idUsuario1) {
        this.idUsuario1 = idUsuario1;
    }

    /**
     * Obtiene el ID del segundo usuario.
     *
     * @return idUsuario2
     */
    public Long getIdUsuario2() {
        return idUsuario2;
    }

    /**
     * Establece el ID del segundo usuario.
     *
     * @param idUsuario2 id del usuario
     */
    public void setIdUsuario2(Long idUsuario2) {
        this.idUsuario2 = idUsuario2;
    }

    /**
     * Obtiene la fecha en que se generó el match.
     *
     * @return fecha de creación del match
     */
    public LocalDateTime getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha del match.
     *
     * @param fecha fecha a asignar
     */
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}

