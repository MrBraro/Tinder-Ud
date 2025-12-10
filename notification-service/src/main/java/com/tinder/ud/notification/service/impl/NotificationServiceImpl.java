package com.tinder.ud.notification.service.impl;

import com.tinder.ud.notification.dto.NotificationDTO;
import com.tinder.ud.notification.entity.NotificationLog;
import com.tinder.ud.notification.repository.NotificationLogRepository;
import com.tinder.ud.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de notificaciones.
 * Gestiona la conversión entre entidades y DTOs,
 * así como la persistencia de los registros.
 * 
 * @author Paula Martinez
 * @version 1.0
 * @since 2025-12-09
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationLogRepository repository;

    /**
     * Registra una nueva notificación en la base de datos.
     *
     * @param dto datos de la notificación
     * @return notificación creada
     */
    @Override
    public NotificationDTO logNotification(NotificationDTO dto) {
        NotificationLog entity = new NotificationLog();
        entity.setDestinatario(dto.getDestinatario());
        entity.setMensaje(dto.getMensaje());
        entity.setFecha(LocalDateTime.now());

        return mapToDTO(repository.save(entity));
    }

    /**
     * Obtiene todas las notificaciones asociadas a un destinatario.
     *
     * @param destinatario correo o ID del destinatario
     * @return lista de notificaciones
     */
    @Override
    public List<NotificationDTO> obtenerLogsPorDestinatario(String destinatario) {
        return repository.findByDestinatario(destinatario).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una entidad NotificationLog a su correspondiente DTO.
     *
     * @param entity entidad de base de datos
     * @return objeto DTO equivalente
     */
    private NotificationDTO mapToDTO(NotificationLog entity) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(entity.getId());
        dto.setDestinatario(entity.getDestinatario());
        dto.setMensaje(entity.getMensaje());
        dto.setFecha(entity.getFecha());
        return dto;
    }
}
