package com.tinder.ud.notification.controller;

import com.tinder.ud.notification.dto.NotificationDTO;
import com.tinder.ud.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de notificaciones.
 * Registra y consulta logs de notificaciones enviadas.
 * 
 * @author Paula Martinez
 * @version 3.0
 * @since 2025-12-09
 */
@RestController
@RequestMapping("/notification")
@CrossOrigin(origins = "*")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    /**
     * Registra una notificación enviada en el sistema.
     * 
     * @param dto Datos de la notificación (tipo, destinatario, mensaje)
     * @return ResponseEntity con el NotificationDTO creado
     */
    @PostMapping("/log")
    public ResponseEntity<NotificationDTO> logNotification(@RequestBody NotificationDTO dto) {
        return ResponseEntity.ok(notificationService.logNotification(dto));
    }

    /**
     * Obtiene el historial de notificaciones de un destinatario.
     * 
     * @param destinatario Email o ID del destinatario
     * @return ResponseEntity con lista de NotificationDTO
     */
    @GetMapping("/logs/{destinatario}")
    public ResponseEntity<List<NotificationDTO>> obtenerLogs(@PathVariable String destinatario) {
        return ResponseEntity.ok(notificationService.obtenerLogsPorDestinatario(destinatario));
    }
}
