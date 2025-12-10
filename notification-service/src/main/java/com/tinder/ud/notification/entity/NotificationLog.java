package com.tinder.ud.notification.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad JPA que representa un registro de notificación
 * almacenado en la base de datos. Contiene información del
 * destinatario, mensaje y fecha en que fue enviada.
 * 
 * @author Paula Martinez
 * @version 1.0
 * @since 2025-12-09
 */
@Entity
@Table(name = "notification_log")
public class NotificationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destinatario;

    private String mensaje;

    private LocalDateTime fecha;

    /**
     * Constructor vacío requerido por JPA.
     */
    public NotificationLog() {
    }

    /**
     * Constructor para crear una instancia completa.
     *
     * @param id identificador del log
     * @param destinatario correo o ID del destinatario
     * @param mensaje contenido de la notificación
     * @param fecha fecha de envío
     */
    public NotificationLog(Long id, String destinatario, String mensaje, LocalDateTime fecha) {
        this.id = id;
        this.destinatario = destinatario;
        this.mensaje = mensaje;
        this.fecha = fecha;
    }

    /** @return id del registro */
    public Long getId() {
        return id;
    }

    /** @param id establece el identificador */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return destinatario de la notificación */
    public String getDestinatario() {
        return destinatario;
    }

    /** @param destinatario define el destinatario */
    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    /** @return mensaje enviado */
    public String getMensaje() {
        return mensaje;
    }

    /** @param mensaje contenido del mensaje */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /** @return fecha de registro */
    public LocalDateTime getFecha() {
        return fecha;
    }

    /** @param fecha establece la fecha del registro */
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}

