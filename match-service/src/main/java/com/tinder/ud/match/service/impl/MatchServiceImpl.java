package com.tinder.ud.match.service.impl;

import com.tinder.ud.match.dto.MatchDTO;
import com.tinder.ud.match.entity.Match;
import com.tinder.ud.match.repository.MatchRepository;
import com.tinder.ud.match.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de matches.
 * Aplica la lógica para verificar interés mutuo entre usuarios
 * consultando el swipe-service y gestionando persistencia en la base de datos.
 *
 * @author Juan Estevan Ariza Ortiz
 * @version 3.0
 * @since 2025-12-09
 */
@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String SWIPE_SERVICE_URL = "http://localhost:8084/swipe/check-like/";

        /**
     * Verifica si dos usuarios tienen interés mutuo.
     * Genera y almacena un match si ambos han dado like.
     *
     * @param idUsuario1 primer usuario
     * @param idUsuario2 segundo usuario
     * @return MatchDTO si existe match, null si no
     */
    @Override
    public MatchDTO verificarMatch(Long idUsuario1, Long idUsuario2) {

        Long u1 = Math.min(idUsuario1, idUsuario2);
        Long u2 = Math.max(idUsuario1, idUsuario2);

        if (matchRepository.existsByIdUsuario1AndIdUsuario2(u1, u2)) {
            throw new RuntimeException("El match ya existe");
        }

        // Check Like 1 -> 2
        Boolean like1 = restTemplate.getForObject(SWIPE_SERVICE_URL + u1 + "/" + u2, Boolean.class);
        // Check Like 2 -> 1
        Boolean like2 = restTemplate.getForObject(SWIPE_SERVICE_URL + u2 + "/" + u1, Boolean.class);

        if (Boolean.TRUE.equals(like1) && Boolean.TRUE.equals(like2)) {
            // Es match!
            Match match = new Match();
            match.setIdUsuario1(u1);
            match.setIdUsuario2(u2);
            match.setFecha(LocalDateTime.now());

            Match saved = matchRepository.save(match);

            // Notificacion
            try {
            } catch (Exception e) {
                System.err.println("Error enviando notificacion: " + e.getMessage());
            }

            return mapToDTO(saved);
        } else {
            return null; // No match yet
        }
    }

     /**
     * Obtiene todos los matches asociados a un usuario.
     *
     * @param idUsuario identificador del usuario
     * @return lista de MatchDTO
     */
    @Override
    public List<MatchDTO> obtenerMatches(Long idUsuario) {
        return matchRepository.findByIdUsuario1OrIdUsuario2(idUsuario, idUsuario).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

     /**
     * Convierte una entidad Match en un MatchDTO.
     *
     * @param entity entidad Match
     * @return DTO resultante
     */
    private MatchDTO mapToDTO(Match entity) {
        MatchDTO dto = new MatchDTO();
        dto.setId(entity.getId());
        dto.setIdUsuario1(entity.getIdUsuario1());
        dto.setIdUsuario2(entity.getIdUsuario2());
        dto.setFecha(entity.getFecha());
        return dto;
    }
}
