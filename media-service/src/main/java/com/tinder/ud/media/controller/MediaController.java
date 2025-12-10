package com.tinder.ud.media.controller;

import com.tinder.ud.media.dto.MediaDTO;
import com.tinder.ud.media.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para manejar operaciones relacionadas
 * con archivos multimedia (fotos de usuario).
 * 
 * Métodos:
 * - agregarFoto(MediaDTO dto): agrega una foto para un usuario.
 * - eliminarFoto(Long id): elimina una foto según ID.
 * - obtenerFotos(Long idUsuario): obtiene todas las fotos de un usuario.
 * 
 * @author Paula Martinez
 * @version 3.0
 * @since 2025-12-09
 */
@RestController
@RequestMapping("/media")
@CrossOrigin(origins = "*")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    /**
     * Agrega una foto para un usuario.
     * 
     * @param dto objeto con datos de la foto y del usuario.
     * @return MediaDTO creado en la base de datos.
     */
    @PostMapping
    public ResponseEntity<MediaDTO> agregarFoto(@RequestBody MediaDTO dto) {
        return ResponseEntity.ok(mediaService.agregarFoto(dto));
    }

    /**
     * Elimina una foto por su ID.
     * 
     * @param id ID de la foto a eliminar.
     * @return estado 204 si se eliminó, 404 si no existe.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFoto(@PathVariable Long id) {
        try {
            mediaService.eliminarFoto(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtiene todas las fotos de un usuario.
     * 
     * @param idUsuario ID del usuario.
     * @return lista de MediaDTO pertenecientes al usuario.
     */
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<MediaDTO>> obtenerFotos(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(mediaService.obtenerFotosPorUsuario(idUsuario));
    }
}
