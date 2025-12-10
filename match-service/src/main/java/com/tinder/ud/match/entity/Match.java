package com.tinder.ud.match.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad JPA que representa un match entre dos usuarios.
 * Se persiste cuando ambos han mostrado interés mutuo.
 *
 * <p>Corresponde a la tabla "matches".</p>
 *
 * @author Juan Estevan Ariza Ortiz
 * @version 3.0
 * @since 2025-12-09
 */
@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_usuario1", nullable = false)
    private Long idUsuario1;

    @Column(name = "id_usuario2", nullable = false)
    private Long idUsuario2;

    private LocalDateTime fecha;

    /**
     * Constructor vacío requerido por JPA.
     */
    public Match() {
    }

    /**
     * Constructor completo.
     *
     * @param id identificador del match
     * @param idUsuario1 primer usuario
     * @param idUsuario2 segundo usuario
     * @param fecha fecha del match
     */
    public Match(Long id, Long idUsuario1, Long idUsuario2, LocalDateTime fecha) {
        this.id = id;
        this.idUsuario1 = idUsuario1;
        this.idUsuario2 = idUsuario2;
        this.fecha = fecha;
    }

    // Getters y setters estándar...
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
