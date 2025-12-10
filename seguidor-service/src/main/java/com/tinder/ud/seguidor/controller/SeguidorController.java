package com.tinder.ud.seguidor.controller;

import com.tinder.ud.seguidor.dto.SeguidorDTO;
import com.tinder.ud.seguidor.service.SeguidorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de seguidores.
 * Permite seguir/dejar de seguir usuarios y consultar relaciones.
 * 
 * @author Juan Estevan Ariza
 * @version 3.0
 * @since 2025-12-09
 */
@RestController
@RequestMapping("/seguidor")
@CrossOrigin(origins = "*")
public class SeguidorController {

    @Autowired
    private SeguidorService seguidorService;

    /**
     * Crea una relación de seguimiento entre dos usuarios.
     * 
     * @param idSeguidor ID del usuario que seguirá
     * @param idSeguido  ID del usuario a seguir
     * @return ResponseEntity con el SeguidorDTO creado
     * @throws RuntimeException si ya existe la relación o hay error
     */
    @PostMapping("/{idSeguidor}/seguir/{idSeguido}")
    public ResponseEntity<?> seguirUsuario(@PathVariable Long idSeguidor, @PathVariable Long idSeguido) {
        try {
            return ResponseEntity.ok(seguidorService.seguirUsuario(idSeguidor, idSeguido));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Elimina una relación de seguimiento entre dos usuarios.
     * 
     * @param idSeguidor ID del usuario que dejará de seguir
     * @param idSeguido  ID del usuario a dejar de seguir
     * @return ResponseEntity vacío con código 204
     * @throws RuntimeException si no existe la relación
     */
    @DeleteMapping("/{idSeguidor}/dejar-de-seguir/{idSeguido}")
    public ResponseEntity<?> dejarDeSeguir(@PathVariable Long idSeguidor, @PathVariable Long idSeguido) {
        try {
            seguidorService.dejarDeSeguir(idSeguidor, idSeguido);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Verifica si un usuario está siguiendo a otro.
     * 
     * @param idSeguidor ID del potencial seguidor
     * @param idSeguido  ID del potencial seguido
     * @return ResponseEntity con boolean indicando la relación
     */
    @GetMapping("/{idSeguidor}/sigue/{idSeguido}")
    public ResponseEntity<Boolean> estaSiguiendo(@PathVariable Long idSeguidor, @PathVariable Long idSeguido) {
        return ResponseEntity.ok(seguidorService.estaSiguiendo(idSeguidor, idSeguido));
    }

    /**
     * Obtiene la lista de usuarios que un usuario sigue.
     * 
     * @param idSeguidor ID del usuario
     * @return ResponseEntity con lista de SeguidorDTO
     */
    @GetMapping("/seguidos/{idSeguidor}")
    public ResponseEntity<List<SeguidorDTO>> obtenerSeguidos(@PathVariable Long idSeguidor) {
        return ResponseEntity.ok(seguidorService.obtenerSeguidos(idSeguidor));
    }

    /**
     * Obtiene la lista de seguidores de un usuario.
     * 
     * @param idSeguido ID del usuario
     * @return ResponseEntity con lista de SeguidorDTO
     */
    @GetMapping("/seguidores/{idSeguido}")
    public ResponseEntity<List<SeguidorDTO>> obtenerSeguidores(@PathVariable Long idSeguido) {
        return ResponseEntity.ok(seguidorService.obtenerSeguidores(idSeguido));
    }
}
