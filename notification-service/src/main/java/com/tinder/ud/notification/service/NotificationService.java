package com.tinder.ud.notification.service;

import com.tinder.ud.notification.dto.NotificationDTO;
import java.util.List;

/**
 * Servicio encargado de gestionar la lógica de notificaciones.
 * Define operaciones para registrar y consultar logs.
 * 
 * @author Paula Martinez
 * @version 1.0
 * @since 2025-12-09
 */
public interface NotificationService {

    /**
     * Registra una nueva notificación en el sistema.
     *
     * @param dto datos de la notificación
     * @return notificación almacenada
     */
    NotificationDTO logNotification(NotificationDTO dto);

    /**
     * Obtiene el historial de notificaciones enviadas a un destinatario.
     *
     * @param destinatario identificador del destinatario
     * @return lista de notificaciones registradas
     */
    List<NotificationDTO> obtenerLogsPorDestinatario(String destinatario);
}
