package com.tinder.ud.match.controller;

import com.tinder.ud.match.dto.MatchDTO;
import com.tinder.ud.match.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de matches.
 * Verifica y almacena los matches entre usuarios.
 * 
 * @author Juan Estevan Ariza
 * @version 3.0
 * @since 2025-12-09
 */
@RestController
@RequestMapping("/match")
@CrossOrigin(origins = "*")
public class MatchController {

    @Autowired
    private MatchService matchService;

    /**
     * Verifica si existe un match entre dos usuarios.
     * Consulta el swipe-service para confirmar likes mutuos.
     * 
     * @param idUsuario1 ID del primer usuario
     * @param idUsuario2 ID del segundo usuario
     * @return ResponseEntity con el MatchDTO si existe, o mensaje si no
     * @throws RuntimeException si hay error en la verificación
     */
    @PostMapping("/verificar/{idUsuario1}/{idUsuario2}")
    public ResponseEntity<?> verificarMatch(@PathVariable Long idUsuario1, @PathVariable Long idUsuario2) {
        try {
            MatchDTO match = matchService.verificarMatch(idUsuario1, idUsuario2);
            if (match != null) {
                return ResponseEntity.ok(match);
            } else {
                return ResponseEntity.ok("No hay match todavía");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Obtiene todos los matches de un usuario.
     * 
     * @param idUsuario ID del usuario
     * @return ResponseEntity con lista de MatchDTO
     */
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<MatchDTO>> obtenerMatches(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(matchService.obtenerMatches(idUsuario));
    }
}
