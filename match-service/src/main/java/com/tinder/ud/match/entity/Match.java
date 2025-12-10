package com.tinder.ud.match.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad JPA que representa un match entre dos usuarios.
 * Se crea cuando ambos usuarios se han dado like mutuamente.
 * 
 * @author Juan Estevan Ariza
 * @version 1.0
 * @since 2025-12-09
 */
@Entity
@Table(name = "matches") // 'match' is a reserved keyword in MySQL sometimes
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_usuario1", nullable = false)
    private Long idUsuario1;

    @Column(name = "id_usuario2", nullable = false)
    private Long idUsuario2;

    private LocalDateTime fecha;

    public Match() {
    }

    public Match(Long id, Long idUsuario1, Long idUsuario2, LocalDateTime fecha) {
        this.id = id;
        this.idUsuario1 = idUsuario1;
        this.idUsuario2 = idUsuario2;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuario1() {
        return idUsuario1;
    }

    public void setIdUsuario1(Long idUsuario1) {
        this.idUsuario1 = idUsuario1;
    }

    public Long getIdUsuario2() {
        return idUsuario2;
    }

    public void setIdUsuario2(Long idUsuario2) {
        this.idUsuario2 = idUsuario2;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
