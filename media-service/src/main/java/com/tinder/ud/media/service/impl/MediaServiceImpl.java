package com.tinder.ud.media.service.impl;

import com.tinder.ud.media.dto.MediaDTO;
import com.tinder.ud.media.entity.Media;
import com.tinder.ud.media.repository.MediaRepository;
import com.tinder.ud.media.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación de MediaService.
 * Maneja lógica de negocio para registros multimedia.
 * 
 * Métodos:
 * - agregarFoto(MediaDTO dto)
 * - eliminarFoto(Long id)
 * - obtenerFotosPorUsuario(Long idUsuario)
 * - mapToDTO(Media entity): mapea entidad a DTO.
 * 
 * @author Paula Martinez
 * @version 3.0
 * @since 2025-12-10
 */
@Service
public class MediaServiceImpl implements MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    /**
     * Crea un nuevo registro de foto.
     * 
     * @param dto datos de la foto.
     * @return foto registrada.
     */
    @Override
    public MediaDTO agregarFoto(MediaDTO dto) {
        Media media = new Media();
        media.setIdUsuario(dto.getIdUsuario());
        media.setUrl(dto.getUrl());
        media.setEsPrincipal(dto.getEsPrincipal());

        Media saved = mediaRepository.save(media);
        return mapToDTO(saved);
    }

    /**
     * Elimina una foto existente.
     * 
     * @param id ID de la foto.
     */
    @Override
    public void eliminarFoto(Long id) {
        if (!mediaRepository.existsById(id)) {
            throw new RuntimeException("Foto no encontrada");
        }
        mediaRepository.deleteById(id);
    }

    /**
     * Devuelve todas las fotos de un usuario.
     * 
     * @param idUsuario ID del usuario.
     * @return lista de fotos en DTO.
     */
    @Override
    public List<MediaDTO> obtenerFotosPorUsuario(Long idUsuario) {
        return mediaRepository.findByIdUsuario(idUsuario).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una entidad Media en MediaDTO.
     * 
     * @param entity entidad Media.
     * @return DTO resultante.
     */
    private MediaDTO mapToDTO(Media entity) {
        MediaDTO dto = new MediaDTO();
        dto.setId(entity.getId());
        dto.setIdUsuario(entity.getIdUsuario());
        dto.setUrl(entity.getUrl());
        dto.setEsPrincipal(entity.getEsPrincipal());
        return dto;
    }
}
