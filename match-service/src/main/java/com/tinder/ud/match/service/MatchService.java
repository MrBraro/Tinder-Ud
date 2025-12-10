package com.tinder.ud.match.service;

import com.tinder.ud.match.dto.MatchDTO;
import java.util.List;

/**
 * Interfaz del servicio que define la lógica para la gestión de matches.
 * Incluye verificación, creación y consulta de matches entre usuarios.
 *
 * @version 3.0
 * @since 2025-12-09
 * @author Juan Estevan Ariza Ortiz
 */
public interface MatchService {

    /**
     * Verifica si dos usuarios tienen mutuo interés y genera un match si es necesario.
     *
     * @param idUsuario1 primer usuario
     * @param idUsuario2 segundo usuario
     * @return MatchDTO si se genera o existe match, null si no hay interés mutuo
     */
    MatchDTO verificarMatch(Long idUsuario1, Long idUsuario2);

    /**
     * Obtiene la lista de matches asociados a un usuario.
     *
     * @param idUsuario identificador del usuario
     * @return lista de MatchDTO
     */
    List<MatchDTO> obtenerMatches(Long idUsuario);
}

