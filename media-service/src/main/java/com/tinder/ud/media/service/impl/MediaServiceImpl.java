package com.tinder.ud.media.service.impl;

import com.tinder.ud.media.dto.MediaDTO;
import com.tinder.ud.media.entity.Media;
import com.tinder.ud.media.repository.MediaRepository;
import com.tinder.ud.media.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MediaServiceImpl implements MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    @Override
    public MediaDTO agregarFoto(MediaDTO dto) {
        Media media = new Media();
        media.setIdUsuario(dto.getIdUsuario());
        media.setUrl(dto.getUrl());
        media.setEsPrincipal(dto.getEsPrincipal());

        Media saved = mediaRepository.save(media);
        return mapToDTO(saved);
    }

    @Override
    public void eliminarFoto(Long id) {
        if (!mediaRepository.existsById(id)) {
            throw new RuntimeException("Foto no encontrada");
        }
        mediaRepository.deleteById(id);
    }

    @Override
    public List<MediaDTO> obtenerFotosPorUsuario(Long idUsuario) {
        return mediaRepository.findByIdUsuario(idUsuario).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private MediaDTO mapToDTO(Media entity) {
        MediaDTO dto = new MediaDTO();
        dto.setId(entity.getId());
        dto.setIdUsuario(entity.getIdUsuario());
        dto.setUrl(entity.getUrl());
        dto.setEsPrincipal(entity.getEsPrincipal());
        return dto;
    }
}
