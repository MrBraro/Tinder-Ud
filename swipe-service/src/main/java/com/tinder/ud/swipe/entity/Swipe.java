package com.tinder.ud.swipe.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad JPA que representa un swipe (like o dislike) entre usuarios.
 * Registra la interacción de un usuario hacia otro perfil.
 * 
 * @author Juan Estevan Ariza Ortiz
 * @version 3.0
 * @since 2025-12-09
 */
@Entity
@Table(name = "swipe")
public class Swipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_origen", nullable = false)
    private Long idOrigen; // Quien da el like/dislike

    @Column(name = "id_destino", nullable = false)
    private Long idDestino; // Quien recibe

    @Column(name = "es_like", nullable = false)
    private Boolean esLike; // true = LIKE, false = DISLIKE

    private LocalDateTime fecha;

    public Swipe() {
    }

    public Swipe(Long id, Long idOrigen, Long idDestino, Boolean esLike, LocalDateTime fecha) {
        this.id = id;
        this.idOrigen = idOrigen;
        this.idDestino = idDestino;
        this.esLike = esLike;
        this.fecha = fecha;
    }

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
}
