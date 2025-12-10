package com.tinder.ud.media.controller;

import com.tinder.ud.media.dto.MediaDTO;
import com.tinder.ud.media.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de archivos multimedia.
 * Maneja la subida y eliminación de fotos de perfil.
 * 
 * @author Paula Martinez
 * @version 1.0
 * @since 2025-12-09
 */
@RestController
@RequestMapping("/media")
@CrossOrigin(origins = "*")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    /**
     * Agrega una nueva foto al perfil de un usuario.
     * 
     * @param dto Datos de la foto (URL, idUsuario)
     * @return ResponseEntity con el MediaDTO creado
     */
    @PostMapping
    public ResponseEntity<MediaDTO> agregarFoto(@RequestBody MediaDTO dto) {
        return ResponseEntity.ok(mediaService.agregarFoto(dto));
    }

    /**
     * Elimina una foto del sistema.
     * 
     * @param id ID de la foto a eliminar
     * @return ResponseEntity vacío con código 204 o 404 si no existe
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
     * @param idUsuario ID del usuario
     * @return ResponseEntity con lista de MediaDTO
     */
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<MediaDTO>> obtenerFotos(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(mediaService.obtenerFotosPorUsuario(idUsuario));
    }
}
