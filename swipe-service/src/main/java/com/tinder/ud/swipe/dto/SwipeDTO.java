package com.tinder.ud.swipe.dto;

import java.time.LocalDateTime;

public class SwipeDTO {
    private Long id;
    private Long idOrigen;
    private Long idDestino;
    private Boolean esLike;
    private LocalDateTime fecha;
    private boolean match; // New field for frontend logic

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdOrigen() {
        return idOrigen;
    }

    public void setIdOrigen(Long idOrigen) {
        this.idOrigen = idOrigen;
    }

    public Long getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(Long idDestino) {
        this.idDestino = idDestino;
    }

    public Boolean getEsLike() {
        return esLike;
    }

    public void setEsLike(Boolean esLike) {
        this.esLike = esLike;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }
}
