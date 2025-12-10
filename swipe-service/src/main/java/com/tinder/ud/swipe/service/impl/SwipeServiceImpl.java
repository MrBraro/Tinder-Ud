package com.tinder.ud.swipe.service.impl;

import com.tinder.ud.swipe.dto.SwipeDTO;
import com.tinder.ud.swipe.entity.Swipe;
import com.tinder.ud.swipe.repository.SwipeRepository;
import com.tinder.ud.swipe.service.SwipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

/**
 * Implementación del servicio encargado de registrar interacciones tipo swipe
 * (like o dislike) entre usuarios dentro de la plataforma.
 * 
 * Valida existencia de usuarios, evita que un usuario se de swipe a sí mismo,
 * actualiza o crea nuevos registros y detecta posibles matches.
 * 
 * También se comunica con el microservicio Match para registrar coincidencias.
 * 
 * @author Juan Estavan Ariza Ortiz
 * @version 1.0
 * @since 2025-12-10
 */
@Service
public class SwipeServiceImpl implements SwipeService {

    @Autowired
    private SwipeRepository swipeRepository;

    @Autowired
    private RestTemplate restTemplate;

    /** URL del microservicio usuario para validar existencia */
    private final String USUARIO_SERVICE_URL = "http://localhost:8082/usuario/existe/";

    /**
     * Registra un swipe (like o dislike) entre usuarios.
     * 
     * Si ya existe un swipe entre ambos usuarios, se sobrescribe.
     * Si es un like, verifica si existe un like recíproco para generar match.
     * 
     * @param dto objeto SwipeDTO con los datos del swipe
     * @return SwipeDTO con la información persistida y si hubo match
     * 
     * @throws RuntimeException si:
     *      - un usuario intenta hacerse swipe a sí mismo,
     *      - el origen o destino no existen,
     *      - ocurre error al comunicarse con el servicio usuario
     */
    @Override
    public SwipeDTO registrarSwipe(SwipeDTO dto) {
        if (dto.getIdOrigen().equals(dto.getIdDestino())) {
            throw new RuntimeException("No puedes darte swipe a ti mismo");
        }

        verificarUsuarioExiste(dto.getIdOrigen());
        verificarUsuarioExiste(dto.getIdDestino());

        // Buscar si existe un swipe previo para sobrescribirlo
        Swipe swipe = swipeRepository.findByIdOrigenAndIdDestino(dto.getIdOrigen(), dto.getIdDestino())
                .orElse(new Swipe());

        swipe.setIdOrigen(dto.getIdOrigen());
        swipe.setIdDestino(dto.getIdDestino());
        swipe.setEsLike(dto.getEsLike());
        swipe.setFecha(LocalDateTime.now());

        Swipe saved = swipeRepository.save(swipe);
        SwipeDTO resultDTO = mapToDTO(saved);

        // Lógica de match solo si es like
        if (Boolean.TRUE.equals(dto.getEsLike())) {
            boolean isMatch = existeLike(dto.getIdDestino(), dto.getIdOrigen());
            resultDTO.setMatch(isMatch);

            if (isMatch) {
                // Si hay match, llamar al match-service para registrar
                try {
                    String matchUrl = "http://localhost:8085/match/verificar/" 
                            + dto.getIdOrigen() + "/" + dto.getIdDestino();

                    restTemplate.postForEntity(matchUrl, null, Object.class);
                    System.out.println("Match generado entre " 
                            + dto.getIdOrigen() + " y " + dto.getIdDestino());
                } catch (Exception e) {
                    // No interrumpir el proceso por fallo externo
                    System.err.println("Error llamando a MatchService: " + e.getMessage());
                }
            }
        }

        return resultDTO;
    }

    /**
     * Verifica si un usuario ya dio like a otro usuario.
     * 
     * @param idOrigen usuario que envió el like
     * @param idDestino usuario que lo recibió
     * @return true si existe un like registrado, false si no
     */
    @Override
    public boolean existeLike(Long idOrigen, Long idDestino) {
        return swipeRepository.existsByIdOrigenAndIdDestinoAndEsLike(idOrigen, idDestino, true);
    }

    /**
     * Verifica la existencia de un usuario consultando el microservicio usuario.
     * 
     * @param usuarioId ID del usuario a verificar
     * @throws RuntimeException si el usuario no existe o si el servicio usuario falla
     */
    private void verificarUsuarioExiste(Long usuarioId) {
        try {
            Boolean existe = restTemplate.getForObject(USUARIO_SERVICE_URL + usuarioId, Boolean.class);

            if (Boolean.FALSE.equals(existe)) {
                throw new RuntimeException("Usuario con ID " + usuarioId + " no existe");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error verificando usuario: " + e.getMessage());
        }
    }

    /**
     * Convierte una entidad Swipe a un DTO SwipeDTO.
     * 
     * @param entity entidad Swipe
     * @return DTO con información del swipe
     */
    private SwipeDTO mapToDTO(Swipe entity) {
        SwipeDTO dto = new SwipeDTO();
        dto.setId(entity.getId());
        dto.setIdOrigen(entity.getIdOrigen());
        dto.setIdDestino(entity.getIdDestino());
        dto.setEsLike(entity.getEsLike());
        dto.setFecha(entity.getFecha());
        return dto;
    }
}

