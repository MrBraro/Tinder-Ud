package com.tinder.ud.seguidor.service;

import com.tinder.ud.seguidor.dto.SeguidorDTO;
import java.util.List;

public interface SeguidorService {
    SeguidorDTO seguirUsuario(Long idSeguidor, Long idSeguido);

    void dejarDeSeguir(Long idSeguidor, Long idSeguido);

    boolean estaSiguiendo(Long idSeguidor, Long idSeguido);

    List<SeguidorDTO> obtenerSeguidos(Long idSeguidor);

    List<SeguidorDTO> obtenerSeguidores(Long idSeguido);
}
