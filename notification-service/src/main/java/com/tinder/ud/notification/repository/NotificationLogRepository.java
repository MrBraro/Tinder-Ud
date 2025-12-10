package com.tinder.ud.notification.repository;

import com.tinder.ud.notification.entity.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para gestionar operaciones CRUD
 * sobre la entidad NotificationLog.
 * 
 * Proporciona métodos para consultar notificaciones
 * por destinatario.
 * 
 * @author Paula Martinez
 * @version 3.0
 * @since 2025-12-09
 */
@Repository
public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {

    /**
     * Busca todos los registros asociados a un destinatario.
     *
     * @param destinatario correo o ID del destinatario
     * @return lista de registros de notificaciones
     */
    List<NotificationLog> findByDestinatario(String destinatario);
}
