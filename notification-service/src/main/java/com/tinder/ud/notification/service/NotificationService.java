package com.tinder.ud.notification.service;

import com.tinder.ud.notification.dto.NotificationDTO;
import java.util.List;

public interface NotificationService {
    NotificationDTO logNotification(NotificationDTO dto);

    List<NotificationDTO> obtenerLogsPorDestinatario(String destinatario);
}
