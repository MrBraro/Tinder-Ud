package com.tinder.ud.seguidor.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seguidor")
public class Seguidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_seguidor", nullable = false)
    private Long idSeguidor; // El usuario que sigue

    @Column(name = "id_seguido", nullable = false)
    private Long idSeguido; // El usuario que es seguido

    private LocalDateTime fecha;

    public Seguidor() {
    }

    public Seguidor(Long id, Long idSeguidor, Long idSeguido, LocalDateTime fecha) {
        this.id = id;
        this.idSeguidor = idSeguidor;
        this.idSeguido = idSeguido;
        this.fecha = fecha;
    }

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
