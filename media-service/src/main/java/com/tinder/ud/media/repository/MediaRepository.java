package com.tinder.ud.media.repository;

import com.tinder.ud.media.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para acceso a la tabla media.
 * 
 * Métodos:
 * - findByIdUsuario(Long idUsuario): retorna todas las fotos de un usuario.
 * 
 * @author Paula Martinez
 * @version 3.0
 * @since 2025-12-10
 */
@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

    /**
     * Busca todas las fotos pertenecientes a un usuario.
     * 
     * @param idUsuario ID del usuario.
     * @return lista de Media.
     */
    List<Media> findByIdUsuario(Long idUsuario);
}
