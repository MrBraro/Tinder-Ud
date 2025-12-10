package com.tinder.ud.seguidor.service;

import com.tinder.ud.seguidor.dto.SeguidorDTO;
import java.util.List;

/**
 * Interfaz que define las operaciones del servicio de seguidores.
 * Gestiona las relaciones de seguimiento entre usuarios.
 *
 * @author Juan Estevan Ariza Ortiz
 * @version 1.0
 * @since 2025-12-09
 */
public interface SeguidorService {

    SeguidorDTO seguirUsuario(Long idSeguidor, Long idSeguido);

    void dejarDeSeguir(Long idSeguidor, Long idSeguido);

    boolean estaSiguiendo(Long idSeguidor, Long idSeguido);

    List<SeguidorDTO> obtenerSeguidos(Long idSeguidor);

    List<SeguidorDTO> obtenerSeguidores(Long idSeguido);
}

