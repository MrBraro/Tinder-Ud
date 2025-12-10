package com.tinder.ud.swipe.controller;

import com.tinder.ud.swipe.dto.SwipeDTO;
import com.tinder.ud.swipe.service.SwipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para la gestión de swipes (likes/dislikes).
 * Gestiona las interacciones de usuarios con otros perfiles.
 * 
 * @author Juan Sebastián Bravo Rojas
 * @version 3.0
 * @since 2025-12-09
 */
@RestController
@RequestMapping("/swipe")
@CrossOrigin(origins = "*")
public class SwipeController {

    @Autowired
    private SwipeService swipeService;

    /**
     * Registra un swipe (like o dislike) de un usuario hacia otro.
     * Si es un like mutuo, se crea automáticamente un match.
     * 
     * @param dto Datos del swipe (idOrigen, idDestino, esLike)
     * @return ResponseEntity con el SwipeDTO incluyendo flag de match
     * @throws RuntimeException si los usuarios no existen o hay error
     */
    @PostMapping
    public ResponseEntity<?> registrarSwipe(@RequestBody SwipeDTO dto) {
        try {
            return ResponseEntity.ok(swipeService.registrarSwipe(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Verifica si existe un like de un usuario hacia otro.
     * Endpoint utilizado por match-service para verificar matches.
     * 
     * @param idOrigen  ID del usuario que dio like
     * @param idDestino ID del usuario que recibió el like
     * @return ResponseEntity con boolean indicando si existe el like
     */
    @GetMapping("/check-like/{idOrigen}/{idDestino}")
    public ResponseEntity<Boolean> existeLike(@PathVariable Long idOrigen, @PathVariable Long idDestino) {
        return ResponseEntity.ok(swipeService.existeLike(idOrigen, idDestino));
    }
}
