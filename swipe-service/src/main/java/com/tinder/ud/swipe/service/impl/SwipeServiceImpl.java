package com.tinder.ud.swipe.service.impl;

import com.tinder.ud.swipe.dto.SwipeDTO;
import com.tinder.ud.swipe.entity.Swipe;
import com.tinder.ud.swipe.repository.SwipeRepository;
import com.tinder.ud.swipe.service.SwipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class SwipeServiceImpl implements SwipeService {

    @Autowired
    private SwipeRepository swipeRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String USUARIO_SERVICE_URL = "http://localhost:8082/usuario/existe/";

    // URL for match service if we wanted to trigger match check immediately,
    // but the requirement says match logic is in match-service.
    // Usually swipe triggers match check. We will assume the controller might call
    // match-service later or match-service polls.
    // However, typical flow: Swipe -> Save -> Call Match Service to check "Is
    // Match?".
    // For now, adhering to strict validatio first.

    @Override
    public SwipeDTO registrarSwipe(SwipeDTO dto) {
        if (dto.getIdOrigen().equals(dto.getIdDestino())) {
            throw new RuntimeException("No puedes darte swipe a ti mismo");
        }

        verificarUsuarioExiste(dto.getIdOrigen());
        verificarUsuarioExiste(dto.getIdDestino());

        // Check if swipe already exists to update it or throw error?
        // Tinder usually allows updating? Let's assume unique interaction per pair for
        // simplicity, or just overwrite.
        // We will implement overwrite logic if exists.

        Swipe swipe = swipeRepository.findByIdOrigenAndIdDestino(dto.getIdOrigen(), dto.getIdDestino())
                .orElse(new Swipe());

        swipe.setIdOrigen(dto.getIdOrigen());
        swipe.setIdDestino(dto.getIdDestino());
        swipe.setEsLike(dto.getEsLike());
        swipe.setFecha(LocalDateTime.now());

        Swipe saved = swipeRepository.save(swipe);
        SwipeDTO resultDTO = mapToDTO(saved);

        // Check for Match (Reciprocal Like)
        if (Boolean.TRUE.equals(dto.getEsLike())) {
            boolean isMatch = existeLike(dto.getIdDestino(), dto.getIdOrigen());
            resultDTO.setMatch(isMatch);

            if (isMatch) {
                // Call Match Service to persist the match
                try {
                    String matchUrl = "http://localhost:8085/match/verificar/" + dto.getIdOrigen() + "/"
                            + dto.getIdDestino();
                    restTemplate.postForEntity(matchUrl, null, Object.class);
                    System.out.println("Match verified and created via MatchService for " + dto.getIdOrigen() + " and "
                            + dto.getIdDestino());
                } catch (Exception e) {
                    System.err.println("Error calling MatchService: " + e.getMessage());
                    // Don't fail the swipe just because match service failed, but log it.
                }
            }
        }

        return resultDTO;
    }

    @Override
    public boolean existeLike(Long idOrigen, Long idDestino) {
        return swipeRepository.existsByIdOrigenAndIdDestinoAndEsLike(idOrigen, idDestino, true);
    }

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
