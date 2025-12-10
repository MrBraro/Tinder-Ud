package com.tinder.ud.media.service;

import com.tinder.ud.media.dto.MediaDTO;
import java.util.List;

public interface MediaService {
    MediaDTO agregarFoto(MediaDTO dto);

    void eliminarFoto(Long id);

    List<MediaDTO> obtenerFotosPorUsuario(Long idUsuario);
}
