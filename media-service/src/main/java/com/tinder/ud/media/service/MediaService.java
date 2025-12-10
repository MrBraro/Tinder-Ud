package com.tinder.ud.media.service;

import com.tinder.ud.media.dto.MediaDTO;
import java.util.List;

/**
 * Interfaz de servicio para gestionar archivos multimedia.
 * 
 * Métodos:
 * - agregarFoto(MediaDTO dto): crea un registro de foto.
 * - eliminarFoto(Long id): elimina una foto.
 * - obtenerFotosPorUsuario(Long idUsuario): obtiene fotos de un usuario.
 * 
 * @version 3.0
 * @since 2025-12-10
 */
public interface MediaService {

    /**
     * Registra una nueva foto en el sistema.
     * 
     * @param dto DTO con datos de la foto.
     * @return MediaDTO guardado.
     */
    MediaDTO agregarFoto(MediaDTO dto);

    /**
     * Elimina una foto por su ID.
     * 
     * @param id ID de la foto.
     */
    void eliminarFoto(Long id);

    /**
     * Obtiene todas las fotos de un usuario.
     * 
     * @param idUsuario ID del usuario.
     * @return lista de MediaDTO.
     */
    List<MediaDTO> obtenerFotosPorUsuario(Long idUsuario);
}
