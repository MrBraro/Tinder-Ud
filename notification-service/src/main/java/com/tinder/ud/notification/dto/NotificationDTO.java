package com.tinder.ud.notification.dto;

import java.time.LocalDateTime;

/**
 * Objeto de transferencia de datos para representar una notificación
 * registrada dentro del sistema.
 * Contiene información básica como destinatario, mensaje y fecha.
 * 
 * @author 
 * @version 3.0
 * @since 2025-12-09
 */
public class NotificationDTO {

    private Long id;
    private String destinatario;
    private String mensaje;
    private LocalDateTime fecha;

    /**
     * Obtiene el identificador del registro de notificación.
     *
     * @return id de la notificación
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador del registro.
     *
     * @param id valor del identificador
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el destinatario de la notificación.
     *
     * @return destinatario
     */
    public String getDestinatario() {
        return destinatario;
    }

    /**
     * Establece el destinatario de la notificación.
     *
     * @param destinatario correo o ID del destinatario
     */
    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    /**
     * Obtiene el contenido del mensaje enviado.
     *
     * @return mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Establece el mensaje enviado.
     *
     * @param mensaje contenido del mensaje
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Obtiene la fecha de creación de la notificación.
     *
     * @return fecha de registro
     */
    public LocalDateTime getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha del registro de notificación.
     *
     * @param fecha fecha de creación
     */
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}

