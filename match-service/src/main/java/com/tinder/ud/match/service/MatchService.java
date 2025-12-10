package com.tinder.ud.match.service;

import com.tinder.ud.match.dto.MatchDTO;
import java.util.List;

public interface MatchService {
    MatchDTO verificarMatch(Long idUsuario1, Long idUsuario2);

    List<MatchDTO> obtenerMatches(Long idUsuario);
}
