package com.tinder.ud.seguidor.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;


/**
* Entidad que representa la relación de seguimiento entre dos usuarios.
* Contiene información del seguidor, seguido y fecha de registro.
* 
* @author Juan Estevan Ariza Ortiz
* @version 1.0
* @since 2025-12-09
*/
@Entity
@Table(name = "seguidor")
public class Seguidor {


/** ID autogenerado de la relación */
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


/** ID del usuario seguidor */
@Column(name = "id_seguidor", nullable = false)
private Long idSeguidor;


/** ID del usuario seguido */
@Column(name = "id_seguido", nullable = false)
private Long idSeguido;


/** Fecha en la que se creó la relación */
private LocalDateTime fecha;


/** Constructor vacío requerido por JPA */
public Seguidor() {}


/**
* Constructor completo.
*
* @param id identificador
* @param idSeguidor usuario seguidor
* @param idSeguido usuario seguido
* @param fecha fecha de creación
*/
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
