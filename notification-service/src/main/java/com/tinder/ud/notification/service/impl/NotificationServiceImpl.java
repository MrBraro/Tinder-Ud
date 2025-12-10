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

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationLogRepository repository;

    @Override
    public NotificationDTO logNotification(NotificationDTO dto) {
        NotificationLog entity = new NotificationLog();
        entity.setDestinatario(dto.getDestinatario());
        entity.setMensaje(dto.getMensaje());
        entity.setFecha(LocalDateTime.now());

        return mapToDTO(repository.save(entity));
    }

    @Override
    public List<NotificationDTO> obtenerLogsPorDestinatario(String destinatario) {
        return repository.findByDestinatario(destinatario).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private NotificationDTO mapToDTO(NotificationLog entity) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(entity.getId());
        dto.setDestinatario(entity.getDestinatario());
        dto.setMensaje(entity.getMensaje());
        dto.setFecha(entity.getFecha());
        return dto;
    }
}
